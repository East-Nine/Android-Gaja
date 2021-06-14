package com.eastnine.gaja.event

import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.util.base.BaseEvent

sealed class PlaceDetailEvent: BaseEvent() {
    object OnClickedClose: PlaceDetailEvent()
    data class OnClickedFavorite(val data: ProductVo): PlaceDetailEvent()
}