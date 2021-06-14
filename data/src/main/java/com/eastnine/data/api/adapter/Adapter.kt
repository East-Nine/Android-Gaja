package com.eastnine.data.api.adapter

import com.eastnine.data.api.response.Product
import com.eastnine.data.db.entity.PlaceData
import com.eastnine.domain.vo.ProductVo

object Adapter {
    fun productToProductVo(item: Product): ProductVo =
        ProductVo(
            id = item.id,
            name = item.name,
            rate = item.rate,
            thumbnail = item.thumbnail,
            imagePath = item.description.imagePath,
            price = item.description.price,
            subject = item.description.subject,
            like = false,
            create = 0
        )

    fun placeDataToProductVo(item: PlaceData): ProductVo =
        ProductVo(
            id = item.id,
            name = item.name,
            rate = item.rate,
            thumbnail = item.thumbnail,
            imagePath = item.imagePath,
            price = item.price,
            subject = item.subject,
            like = item.like,
            create = 0
        )

    fun productVoToPlaceData(item: ProductVo): PlaceData =
        PlaceData(
            id = item.id,
            name = item.name,
            rate = item.rate,
            thumbnail = item.thumbnail,
            imagePath = item.imagePath,
            price = item.price,
            subject = item.subject,
            like = item.like,
            create = 0
        )
}