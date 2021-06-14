package com.eastnine.gaja.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eastnine.gaja.data.FavoriteFilterData
import com.eastnine.gaja.event.FavoriteFilterEvent
import com.eastnine.gaja.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteFilterViewModel @Inject constructor(
    application: Application
): BaseViewModel(application) {

    private val _filterList = MutableLiveData<List<FavoriteFilterData>>(listOf())
    val filterList: LiveData<List<FavoriteFilterData>> = _filterList

    fun updateFilterList(list: List<FavoriteFilterData>) {
        _filterList.value = list
    }

    fun onClickedItem(data: FavoriteFilterData) {
        callEvent(FavoriteFilterEvent.OnClickedItem(data))
    }

    fun onClickedClose() {
        callEvent(FavoriteFilterEvent.OnClickedClose)
    }
}