package com.eastnine.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.eastnine.data.api.adapter.Adapter
import com.eastnine.data.db.dao.TravelDao
import com.eastnine.domain.util.EnumType
import com.eastnine.domain.util.Value.CALL_ITEM_SIZE
import com.eastnine.domain.vo.ProductVo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TravelDBSource(private val travelDao: TravelDao,private val type: EnumType.CallType): RxPagingSource<Int, ProductVo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ProductVo>> {
        val page = params.key ?: 1
        val db = when (type) {
            EnumType.CallType.DatabaseRateASC -> {
                travelDao.getSortRateASC(CALL_ITEM_SIZE, page)
            }
            EnumType.CallType.DatabaseRateDESC -> {
                travelDao.getSortRateDESC(CALL_ITEM_SIZE, page)
            }
            EnumType.CallType.DatabaseTimeASC -> {
                travelDao.getSortTimeASC(CALL_ITEM_SIZE, page)
            }
            EnumType.CallType.DatabaseTimeDESC -> {
                travelDao.getSortTimeDESC(CALL_ITEM_SIZE, page)
            }
            else -> {
                travelDao.getData()
            }
        }
        return db
            .subscribeOn(Schedulers.io())
            .map { localList ->
                if (localList.isEmpty()) {
                    LoadResult.Error(Throwable("End"))
                } else {
                    val list = localList.map { Adapter.placeDataToProductVo(it) }
                    LoadResult.Page(
                        data = list,
                        prevKey = null,
                        nextKey = page + 1
                    )
                }
            }
            .onErrorReturn { LoadResult.Error(it) }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductVo>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
}