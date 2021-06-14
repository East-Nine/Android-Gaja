package com.eastnine.gaja.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.eastnine.gaja.ui.FavoriteFragment
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter(
    value = ["bind:page", "bind:favoriteTime"],
    requireAll = true
)
fun setFavoriteTime(view: TextView, page: String, timeInMillis: Long) {
    if (page == FavoriteFragment::class.java.simpleName && timeInMillis > 0) {
        val date = Date()
        date.time = timeInMillis
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val text = simpleDateFormat.format(date)
        view.text = text
    } else {
        view.visibility = View.GONE
    }
}