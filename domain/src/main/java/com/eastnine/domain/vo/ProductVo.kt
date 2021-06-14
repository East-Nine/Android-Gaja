package com.eastnine.domain.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductVo(
    val id: Int = 0,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val imagePath: String,
    val price: Int,
    val subject: String,
    val like: Boolean,
    val create: Long
): Parcelable