package com.eastnine.gaja.ui

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.eastnine.gaja.BR
import com.eastnine.gaja.R
import com.eastnine.gaja.data.FavoriteFilterData
import com.eastnine.gaja.databinding.ItemFavoriteFilterBinding
import com.eastnine.gaja.util.base.BaseAdapter
import com.eastnine.gaja.viewmodel.FavoriteFilterViewModel

class FavoriteFilterAdapter(
    context: Context,
    private val viewModel: FavoriteFilterViewModel,
    diffUtil: DiffUtil.ItemCallback<FavoriteFilterData>,
) : BaseAdapter<ItemFavoriteFilterBinding, FavoriteFilterData>(context, diffUtil) {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    override fun getLayoutId(): Int = R.layout.item_favorite_filter

    override fun onViewHolder(binding: ItemFavoriteFilterBinding, position: Int, data: FavoriteFilterData) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.data, data)
    }
}