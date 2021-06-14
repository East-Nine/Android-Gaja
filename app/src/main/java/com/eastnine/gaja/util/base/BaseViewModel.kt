package com.eastnine.gaja.util.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eastnine.gaja.util.ViewModelEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private val _event = MutableLiveData<ViewModelEvent<BaseEvent>>()
    val event: LiveData<ViewModelEvent<BaseEvent>> = _event
    
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
    
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun callEvent(event: BaseEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _event.value = ViewModelEvent(event)
        }
    }
}