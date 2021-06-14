package com.eastnine.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.eastnine.data.api.adapter.Adapter
import com.eastnine.data.api.service.GCService
import com.eastnine.data.db.dao.TravelDao
import com.eastnine.domain.vo.ProductVo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TravelOnlineSource(
    private val travelDao: TravelDao,
    private val gcService: GCService
): RxPagingSource<Int, ProductVo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ProductVo>> {
        val page = params.key ?: 1

        return travelDao
            .getData()
            .subscribeOn(Schedulers.io())
            .zipWith(gcService.getTravel(page)) { placeDataList, response ->
                val data = response.data
                if (data == null) {
                    LoadResult.Error(Throwable("End"))
                } else {
                    LoadResult.Page(
                        data = data.product.map {
                            val productVo = Adapter.productToProductVo(it)

                            val placeVoLocalList = placeDataList.map { Adapter.placeDataToProductVo(it) }

                            if (placeVoLocalList.contains(productVo)) {
                                val index = placeVoLocalList.indexOf(productVo)
                                val localPlaceData = placeVoLocalList[index]
                                productVo.copy(like = localPlaceData.like, create = localPlaceData.create)
                            } else {
                                productVo
                            }
                        },
                        prevKey = null,
                        nextKey = page + 1
                    )
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductVo>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
}