package com.eastnine.gaja.ui

import android.app.Activity
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
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_CREATE_ASC
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_CREATE_DESC
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_RATE_ASC
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_RATE_DESC
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.R
import com.eastnine.gaja.data.FavoriteFilterData
import com.eastnine.gaja.databinding.FragmentFavoriteBinding
import com.eastnine.gaja.event.FavoriteFilterEvent
import com.eastnine.gaja.event.PlaceListEvent
import com.eastnine.gaja.listener.OnFilterBottomListener
import com.eastnine.gaja.util.base.BaseFragment
import com.eastnine.gaja.util.eventObserve
import com.eastnine.gaja.viewmodel.FavoriteViewModel
import com.eastnine.gaja.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    private val dataViewModel by activityViewModels<DataViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupObserve()
        setupRecyclerView()
        setupData()
    }

    private fun setupBinding() {
        binding.viewModel = dataViewModel
        binding.favoriteViewModel = favoriteViewModel
    }

    private fun setupObserve() {
        dataViewModel.event.eventObserve(viewLifecycleOwner) { event ->
            when (event) {
                is PlaceListEvent.GetDataList -> {
                    favoriteViewModel.updatePagingData(event.list)
                }
                is PlaceListEvent.OnClickedItem -> {
                    val optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(requireActivity(), event.view, getString(R.string.shared_image))

                    requireActivity().activityResultRegistry
                        .register(
                            this.javaClass.simpleName,
                            ActivityResultContracts.StartActivityForResult()
                        ) { result ->
                            if (result.resultCode == Activity.RESULT_OK) {
                                result
                                    .data
                                    ?.getParcelableExtra<ProductVo>(PlaceDetailActivity.BUNDLE_KEY_PRODUCT_VO)
                                    ?.let { data ->
                                        if (!data.like) {
                                            favoriteViewModel.deleteData(data)
                                        }
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
                    val data = event.data.copy(like = !event.data.like)
                    dataViewModel.toggleFavorite(data)
                    favoriteViewModel.deleteData(data)
                }
                is PlaceListEvent.Error -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        favoriteViewModel.event.eventObserve(viewLifecycleOwner) { event ->
            when (event) {
                is FavoriteFilterEvent.OnClickedFilter -> {
                    FavoriteFilterBottomSheet
                        .instance(event.data)
                        .apply {
                            setBottomListener(
                                object: OnFilterBottomListener {
                                    override fun onBottomSelected(data: FavoriteFilterData) {
                                        favoriteViewModel.recyclerviewState = binding.recyclerFavoriteList.layoutManager?.onSaveInstanceState()
                                        favoriteViewModel.updateFilter(data)

                                        when (data.type) {
                                            FILTER_TYPE_RATE_ASC -> {
                                                dataViewModel.getFavoriteDataRateASC()
                                            }
                                            FILTER_TYPE_RATE_DESC -> {
                                                dataViewModel.getFavoriteDataRateDESC()
                                            }
                                            FILTER_TYPE_CREATE_ASC -> {
                                                dataViewModel.getFavoriteDataTimeASC()
                                            }
                                            FILTER_TYPE_CREATE_DESC -> {
                                                dataViewModel.getFavoriteDataTimeDESC()
                                            }
                                        }
                                    }
                                }
                            )
                        }
                        .show(parentFragmentManager, this.javaClass.simpleName)
                }
            }
        }

        favoriteViewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            val adapter = binding.recyclerFavoriteList.adapter
            if (adapter is PlacePagerAdapter) {
                lifecycleScope.launchWhenCreated {
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val diffUtil: DiffUtil.ItemCallback<ProductVo> = object : DiffUtil.ItemCallback<ProductVo>() {
            override fun areItemsTheSame(oldItem: ProductVo, newItem: ProductVo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ProductVo, newItem: ProductVo): Boolean =
                oldItem.id == newItem.id && oldItem.like == newItem.like && oldItem.create == newItem.create
        }

        binding.recyclerFavoriteList.adapter = PlacePagerAdapter(
            context = requireContext(),
            viewModel = dataViewModel,
            page = this.javaClass.simpleName,
            diffUtil = diffUtil
        )
    }

    private fun setupData() {
        dataViewModel.fetchFavoriteData()
    }
}