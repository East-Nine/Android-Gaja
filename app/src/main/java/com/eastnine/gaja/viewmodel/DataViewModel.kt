package com.eastnine.gaja.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.eastnine.data.api.adapter.Adapter
import com.eastnine.data.db.dao.TravelDao
import com.eastnine.domain.usecase.GCUseCase
import com.eastnine.domain.util.EnumType
import com.eastnine.domain.vo.ProductVo
import com.eastnine.gaja.event.PlaceListEvent
import com.eastnine.gaja.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    application: Application,
    private val gcUseCase: GCUseCase,
    private val travelDao: TravelDao
): BaseViewModel(application) {

    fun toggleFavorite(placeData: ProductVo) {
        val data = Adapter.productVoToPlaceData(placeData)
        viewModelScope.launch(Dispatchers.IO) {
            if (placeData.like) {
                travelDao
                    .insert(data)
            } else {
                travelDao
                    .delete(data)
            }
                .subscribe()
                .also(::addDisposable)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchOnlineData() {
        gcUseCase.getTravel(EnumType.CallType.Online).cachedIn(viewModelScope)
            .subscribe(
                { placeDataList ->
                    callEvent(PlaceListEvent.GetDataList(placeDataList))
                },
                {
                    callEvent(PlaceListEvent.Error(it.message ?: ""))
                }
            )
            .also(::addDisposable)
    }

    fun fetchFavoriteData() {
        getFavoriteDataRateASC()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFavoriteDataRateASC() {
        gcUseCase.getTravel(EnumType.CallType.DatabaseRateASC).cachedIn(viewModelScope)
            .subscribe(
                { placeDataList ->
                    callEvent(PlaceListEvent.GetDataList(placeDataList))
                },
                {
                    callEvent(PlaceListEvent.Error(it.message ?: ""))
                }
            )
            .also(::addDisposable)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFavoriteDataRateDESC() {
        gcUseCase.getTravel(EnumType.CallType.DatabaseRateDESC).cachedIn(viewModelScope)
            .subscribe(
                { placeDataList ->
                    callEvent(PlaceListEvent.GetDataList(placeDataList))
                },
                {
                    callEvent(PlaceListEvent.Error(it.message ?: ""))
                }
            )
            .also(::addDisposable)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFavoriteDataTimeASC() {
        gcUseCase.getTravel(EnumType.CallType.DatabaseTimeASC).cachedIn(viewModelScope)
            .subscribe(
                { placeDataList ->
                    callEvent(PlaceListEvent.GetDataList(placeDataList))
                },
                {
                    callEvent(PlaceListEvent.Error(it.message ?: ""))
                }
            )
            .also(::addDisposable)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFavoriteDataTimeDESC() {
        gcUseCase.getTravel(EnumType.CallType.DatabaseTimeDESC).cachedIn(viewModelScope)
            .subscribe(
                { placeDataList ->
                    callEvent(PlaceListEvent.GetDataList(placeDataList))
                },
                {
                    callEvent(PlaceListEvent.Error(it.message ?: ""))
                }
            )
            .also(::addDisposable)
    }

    fun onClickedItem(view: View, data: ProductVo) {
        callEvent(PlaceListEvent.OnClickedItem(view, data))
    }
    fun onClickedFavorite(data: ProductVo) {
        callEvent(PlaceListEvent.OnClickedFavorite(data))
    }
}