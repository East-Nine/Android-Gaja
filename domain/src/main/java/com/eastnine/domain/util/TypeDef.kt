package com.eastnine.domain.util

import androidx.annotation.IntDef

object TypeDef {

    @IntDef(
        FILTER_TYPE_RATE_ASC, FILTER_TYPE_RATE_DESC,
        FILTER_TYPE_CREATE_ASC, FILTER_TYPE_CREATE_DESC
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class FilterType

    const val FILTER_TYPE_RATE_ASC = 0
    const val FILTER_TYPE_RATE_DESC = 1
    const val FILTER_TYPE_CREATE_ASC = 2
    const val FILTER_TYPE_CREATE_DESC = 3
}