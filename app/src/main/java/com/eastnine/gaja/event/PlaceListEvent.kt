package com.eastnine.gaja.event

import android.view.View
import androidx.paging.PagingData
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.util.base.BaseEvent

sealed class PlaceListEvent: BaseEvent() {
    data class GetDataList(val list: PagingData<ProductVo>): BaseEvent()
    data class OnClickedItem(val view: View, val data: ProductVo): BaseEvent()
    data class OnClickedFavorite(val data: ProductVo): BaseEvent()
    data class Error(val message: String): BaseEvent()
}