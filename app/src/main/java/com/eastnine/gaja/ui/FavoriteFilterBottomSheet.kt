package com.eastnine.gaja.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.eastnine.domain.util.TypeDef
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_CREATE_ASC
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_CREATE_DESC
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_RATE_ASC
import com.eastnine.domain.util.TypeDef.FILTER_TYPE_RATE_DESC
import com.eastnine.gaja.R
import com.eastnine.gaja.data.FavoriteFilterData
import com.eastnine.gaja.databinding.FragmentFavoriteFilterBottomSheetBinding
import com.eastnine.gaja.event.FavoriteFilterEvent
import com.eastnine.gaja.listener.OnFilterBottomListener
import com.eastnine.gaja.util.base.BaseBottomSheetDialogFragment
import com.eastnine.gaja.util.eventObserve
import com.eastnine.gaja.viewmodel.FavoriteFilterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFilterBottomSheet :
    BaseBottomSheetDialogFragment<FragmentFavoriteFilterBottomSheetBinding>(R.layout.fragment_favorite_filter_bottom_sheet) {

    private val viewModel by viewModels<FavoriteFilterViewModel>()

    private var onBottomSheetListener: OnFilterBottomListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initData = arguments?.getParcelable<FavoriteFilterData>(BUNDLE_BOTTOM_SHEET_DATA)

        setupBinding()
        setRecyclerView()
        setupObserver()
        setupData(initData?.type ?: FILTER_TYPE_RATE_ASC)
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
    }

    private fun setRecyclerView() {
        val diffUtil: DiffUtil.ItemCallback<FavoriteFilterData> = object : DiffUtil.ItemCallback<FavoriteFilterData>() {
            override fun areItemsTheSame(oldItem: FavoriteFilterData, newItem: FavoriteFilterData): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: FavoriteFilterData, newItem: FavoriteFilterData): Boolean = false
        }

        binding.recyclerFavoriteTypeList.adapter = FavoriteFilterAdapter(
            context = requireContext(),
            viewModel = viewModel,
            diffUtil = diffUtil
        )
    }

    private fun setupObserver() {
        viewModel.event.eventObserve(viewLifecycleOwner) { event ->
            when (event) {
                is FavoriteFilterEvent.OnClickedItem -> {
                    onBottomSheetListener?.onBottomSelected(event.data)
                    dismiss()
                }
                is FavoriteFilterEvent.OnClickedClose -> {
                    dismiss()
                }
            }
        }

        viewModel.filterList.observe(viewLifecycleOwner) {
            replaceAllData(it)
        }
    }

    private fun setupData(@TypeDef.FilterType initData: Int) {
        viewModel.updateFilterList(
            listOf(
                FavoriteFilterData(
                    FILTER_TYPE_RATE_ASC,
                    FILTER_TYPE_RATE_ASC == initData,
                    getString(R.string.favorite_filter_rate_asc)
                ),
                FavoriteFilterData(
                    FILTER_TYPE_RATE_DESC,
                    FILTER_TYPE_RATE_DESC == initData,
                    getString(R.string.favorite_filter_rate_desc)
                ),
                FavoriteFilterData(
                    FILTER_TYPE_CREATE_ASC,
                    FILTER_TYPE_CREATE_ASC == initData,
                    getString(R.string.favorite_filter_create_asc)
                ),
                FavoriteFilterData(
                    FILTER_TYPE_CREATE_DESC,
                    FILTER_TYPE_CREATE_DESC == initData,
                    getString(R.string.favorite_filter_create_desc)
                )
            )
        )
    }

    fun setBottomListener(listener: OnFilterBottomListener) {
        onBottomSheetListener = listener
    }

    private fun replaceAllData(list: List<FavoriteFilterData>) {
        binding.recyclerFavoriteTypeList.adapter.let { adapter ->
            if (adapter is FavoriteFilterAdapter) {
                adapter.replaceAllData(list)
            }
        }
    }

    companion object {
        private const val BUNDLE_BOTTOM_SHEET_DATA = "bottomSheetData"

        fun instance(data: FavoriteFilterData) = FavoriteFilterBottomSheet()
            .apply {
                arguments = bundleOf(BUNDLE_BOTTOM_SHEET_DATA to data)
            }
    }
}
