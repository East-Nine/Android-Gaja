package com.eastnine.domain.usecase

import androidx.paging.PagingData
import com.eastnine.domain.repository.GCRepository
import com.eastnine.domain.util.EnumType
import com.eastnine.domain.vo.ProductVo
import io.reactivex.rxjava3.core.Observable

class GCUseCase(private val repository: GCRepository) {
    fun getTravel(type: EnumType.CallType): Observable<PagingData<ProductVo>> =
        repository.getTravel(type)
}