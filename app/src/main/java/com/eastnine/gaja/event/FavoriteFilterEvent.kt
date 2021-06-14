package com.eastnine.gaja.event

import com.eastnine.gaja.data.FavoriteFilterData
import com.eastnine.gaja.util.base.BaseEvent

sealed class FavoriteFilterEvent: BaseEvent() {
    data class OnClickedItem(val data: FavoriteFilterData): FavoriteFilterEvent()
    data class OnClickedFilter(val data: FavoriteFilterData): FavoriteFilterEvent()
    object OnClickedClose: FavoriteFilterEvent()
}