package com.eastnine.gaja.util

open class ViewModelEvent<out T>(val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}
