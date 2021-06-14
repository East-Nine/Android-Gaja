package com.eastnine.data.api.service

import com.eastnine.data.api.response.TravelResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface GCService {
    //장소검색
    @GET("{$PAGE}.json")
    fun getTravel(
        @Path(PAGE) page: Int
    ): Single<TravelResponse>

    companion object {
        private const val PAGE = "page"
    }
}