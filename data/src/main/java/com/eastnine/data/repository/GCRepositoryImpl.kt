package com.eastnine.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.eastnine.data.api.service.GCService
import com.eastnine.data.db.dao.TravelDao
import com.eastnine.data.paging.TravelDBSource
import com.eastnine.data.paging.TravelOnlineSource
import com.eastnine.domain.util.Value.CALL_ITEM_SIZE
import com.eastnine.domain.repository.GCRepository
import com.eastnine.domain.util.EnumType.CallType
import com.eastnine.domain.vo.ProductVo
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GCRepositoryImpl(private val travelDao: TravelDao, private val service: GCService): GCRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTravel(type: CallType): Observable<PagingData<ProductVo>> =
        when (type) {
            CallType.Online -> {
                Pager(PagingConfig(CALL_ITEM_SIZE)) {
                    TravelOnlineSource(travelDao, service)
                }.observable
            }
            CallType.DatabaseRateASC -> {
                Pager(PagingConfig(CALL_ITEM_SIZE)) {
                    TravelDBSource(travelDao, type)
                }.observable
            }
            CallType.DatabaseRateDESC -> {
                Pager(PagingConfig(CALL_ITEM_SIZE)) {
                    TravelDBSource(travelDao, type)
                }.observable
            }
            CallType.DatabaseTimeASC -> {
                Pager(PagingConfig(CALL_ITEM_SIZE)) {
                    TravelDBSource(travelDao, type)
                }.observable
            }
            CallType.DatabaseTimeDESC -> {
                Pager(PagingConfig(CALL_ITEM_SIZE)) {
                    TravelDBSource(travelDao, type)
                }.observable
            }
        }
}