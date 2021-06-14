package com.eastnine.gaja.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<ViewModelEvent<T>>.eventObserve(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<ViewModelEvent<T>> {
    val wrappedObserver = Observer<ViewModelEvent<T>> { viewModelEvent ->
        viewModelEvent.getContentIfNotHandled()?.let {
            onChanged.invoke(it)
        }
    }

    observe(owner, wrappedObserver)

    return wrappedObserver
}