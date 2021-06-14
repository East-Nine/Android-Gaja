package com.eastnine.gaja.viewmodel

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.eastnine.domain.util.TypeDef
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.R
import com.eastnine.gaja.data.FavoriteFilterData
import com.eastnine.gaja.event.FavoriteFilterEvent
import com.eastnine.gaja.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    application: Application
): BaseViewModel(application) {

    private val _pagingData = MutableLiveData<PagingData<ProductVo>>()
    val pagingData: LiveData<PagingData<ProductVo>> = _pagingData

    var recyclerviewState: Parcelable? = null

    private val _filter = MutableLiveData(
        FavoriteFilterData(
            TypeDef.FILTER_TYPE_RATE_ASC,
            true,
            getApplication<Application>().getString(R.string.favorite_filter_rate_asc)
        )
    )
    val filter: LiveData<FavoriteFilterData> = _filter

    fun updateFilter(filter: FavoriteFilterData) {
        _filter.value = filter
    }

    fun updatePagingData(list: PagingData<ProductVo>) {
        _pagingData.value = list
    }

    fun deleteData(data: ProductVo) {
        pagingData.value?.let {
            updatePagingData(
                it.filter { productVo -> productVo.id != data.id }
            )
        }
    }

    fun onClickedFilter(data: FavoriteFilterData) {
        callEvent(FavoriteFilterEvent.OnClickedFilter(data))
    }
}