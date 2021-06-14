package com.eastnine.gaja.data

import android.os.Parcelable
import com.eastnine.domain.util.TypeDef
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteFilterData(
    @TypeDef.FilterType val type: Int,
    val isChecked: Boolean,
    val title: String
): Parcelable
