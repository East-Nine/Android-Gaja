package com.eastnine.gaja.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.R
import com.eastnine.gaja.databinding.ActivityPlaceDetailBinding
import com.eastnine.gaja.event.PlaceDetailEvent
import com.eastnine.gaja.util.base.BaseActivity
import com.eastnine.gaja.util.eventObserve
import com.eastnine.gaja.viewmodel.DataViewModel
import com.eastnine.gaja.viewmodel.PlaceDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailActivity: BaseActivity<ActivityPlaceDetailBinding>(R.layout.activity_place_detail) {

    private val viewModel by viewModels<PlaceDetailViewModel>()
    private val dataViewModel by viewModels<DataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.getParcelableExtra<ProductVo>(BUNDLE_KEY_PRODUCT_VO)

        setupBinding()
        setupData(data)
        setupObserve()
    }

    override fun finish() {
        viewModel.productVo.value?.let { data ->
            setResult(RESULT_OK, Intent().apply { putExtra(BUNDLE_KEY_PRODUCT_VO, data) })
        }
        super.finish()
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.dataViewModel = dataViewModel
    }

    private fun setupData(data: ProductVo?) {
        data?.let {
            viewModel.updateProductVo(it)

            binding.imageThumbnail.post {
                val width = binding.imageThumbnail.width
                val height = (width.toFloat() * 9f / 16f).toInt()

                binding.imageThumbnail.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    this.width = width
                    this.height = height
                }

                Glide
                    .with(this@PlaceDetailActivity)
                    .load(it.imagePath)
                    .placeholder(R.drawable.img_placeholder)
                    .centerCrop()
                    .into(binding.imageThumbnail)
            }
        }
    }

    private fun setupObserve() {
        viewModel.event.eventObserve(this@PlaceDetailActivity) { event ->
            when (event) {
                is PlaceDetailEvent.OnClickedClose -> {
                    finish()
                }
                is PlaceDetailEvent.OnClickedFavorite -> {
                    val data = event.data.copy(like = !event.data.like)
                    dataViewModel.toggleFavorite(data)
                    viewModel.updateProductVo(data)
                }
            }
        }
    }

    companion object {
        const val BUNDLE_KEY_PRODUCT_VO = "productVo"

        fun createBundle(productVo: ProductVo): Bundle =
            bundleOf(
                BUNDLE_KEY_PRODUCT_VO to productVo
            )
    }
}