package com.eastnine.gaja.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.R
import com.eastnine.gaja.databinding.FragmentOnlineBinding
import com.eastnine.gaja.event.PlaceListEvent
import com.eastnine.gaja.util.base.BaseFragment
import com.eastnine.gaja.util.eventObserve
import com.eastnine.gaja.viewmodel.DataViewModel
import com.eastnine.gaja.viewmodel.OnlineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnlineFragment: BaseFragment<FragmentOnlineBinding>(R.layout.fragment_online) {

    private val dataViewModel by activityViewModels<DataViewModel>()
    private val onlineViewModel by viewModels<OnlineViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupObserve()
        setupRecyclerView()
        setupData()
    }

    private fun setupBinding() {
        binding.viewModel = dataViewModel
    }

    private fun setupObserve() {
        dataViewModel.event.eventObserve(viewLifecycleOwner) { event ->
            when (event) {
                is PlaceListEvent.GetDataList -> {
                    onlineViewModel.updatePagingData(event.list)
                }
                is PlaceListEvent.OnClickedItem -> {
                    val optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(requireActivity(), event.view, getString(R.string.shared_image))

                    requireActivity().activityResultRegistry
                        .register(
                            this.javaClass.simpleName,
                            ActivityResultContracts.StartActivityForResult()
                        ) { result ->
                            if (result.resultCode == RESULT_OK) {
                                result
                                    .data
                                    ?.getParcelableExtra<ProductVo>(PlaceDetailActivity.BUNDLE_KEY_PRODUCT_VO)
                                    ?.let { data ->
                                        onlineViewModel.replaceData(data)
                                }
                            }
                        }
                        .launch(
                            Intent(requireContext(), PlaceDetailActivity::class.java)
                                .apply {
                                    putExtras(PlaceDetailActivity.createBundle(event.data))
                                },
                            optionsCompat
                        )
                }
                is PlaceListEvent.OnClickedFavorite -> {
                    val data = event.data.copy(like = !event.data.like, create = System.currentTimeMillis())
                    dataViewModel.toggleFavorite(data)
                    onlineViewModel.replaceData(data)
                }
                is PlaceListEvent.Error -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        onlineViewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            val adapter = binding.recyclerPlaceList.adapter
            if (adapter is PlacePagerAdapter) {
                lifecycleScope.launchWhenCreated {
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val diffUtil = object : DiffUtil.ItemCallback<ProductVo>() {
            override fun areItemsTheSame(oldItem: ProductVo, newItem: ProductVo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ProductVo, newItem: ProductVo): Boolean =
                oldItem.id == newItem.id && oldItem.like == newItem.like
        }

        binding.recyclerPlaceList.adapter = PlacePagerAdapter(
            context = requireContext(),
            viewModel = dataViewModel,
            page = this.javaClass.simpleName,
            diffUtil = diffUtil
        )
    }

    private fun setupData() {
        dataViewModel.fetchOnlineData()
    }
}