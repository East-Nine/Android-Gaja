package com.eastnine.gaja.ui

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.BR
import com.eastnine.gaja.R
import com.eastnine.gaja.databinding.ItemPlaceBinding
import com.eastnine.gaja.util.base.BasePagerAdapter
import com.eastnine.gaja.util.base.BaseViewModel

class PlacePagerAdapter(
    private val context: Context,
    private val viewModel: BaseViewModel,
    private val page: String,
    diffUtil: DiffUtil.ItemCallback<ProductVo>,
) : BasePagerAdapter<ItemPlaceBinding, ProductVo>(context, diffUtil) {

    override fun getLayoutId(): Int = R.layout.item_place

    override fun onViewHolder(binding: ItemPlaceBinding, position: Int, data: ProductVo) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.data, data)
        binding.setVariable(BR.page, page)

        binding.imagePlace.post {
            val width = binding.imagePlace.width
            val height = (width.toFloat() * 9f / 16f).toInt()

            binding.imagePlace.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.width = width
                this.height = height
            }

            Glide
                .with(context)
                .load(data.thumbnail)
                .placeholder(R.drawable.img_placeholder)
                .centerCrop()
                .into(binding.imagePlace)
        }
        binding.textTitle.text = data.name
        binding.textRate.text = data.rate.toString()
    }
}