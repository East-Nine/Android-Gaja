package com.eastnine.domain.repository

import androidx.paging.PagingData
import com.eastnine.domain.util.EnumType
import com.eastnine.domain.vo.ProductVo
import io.reactivex.rxjava3.core.Observable

interface GCRepository {
    fun getTravel(type: EnumType.CallType): Observable<PagingData<ProductVo>>
}