package com.eastnine.gaja.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.event.PlaceDetailEvent
import com.eastnine.gaja.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    application: Application
): BaseViewModel(application) {

    private val _productVo = MutableLiveData<ProductVo>()
    val productVo: LiveData<ProductVo> = _productVo

    fun updateProductVo(data: ProductVo) {
        _productVo.value = data
    }

    fun onClickedClose() {
        callEvent(PlaceDetailEvent.OnClickedClose)
    }
    fun onClickedFavorite(data: ProductVo) {
        callEvent(PlaceDetailEvent.OnClickedFavorite(data))
    }
}