package com.eastnine.gaja.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnlineViewModel @Inject constructor(
    application: Application
): BaseViewModel(application) {
    private val _pagingData = MutableLiveData<PagingData<ProductVo>>()
    val pagingData: LiveData<PagingData<ProductVo>> = _pagingData

    fun updatePagingData(data: PagingData<ProductVo>) {
        _pagingData.value = data
    }

    fun replaceData(data: ProductVo) {
        pagingData.value?.let {
            updatePagingData(
                it.map { placeData ->
                    if (placeData.id == data.id) {
                        data
                    } else {
                        placeData
                    }
                }
            )
        }
    }
}