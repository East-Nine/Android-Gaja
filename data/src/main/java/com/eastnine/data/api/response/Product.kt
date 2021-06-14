package com.eastnine.data.api.response

data class Product(
    val description: Description,
    val id: Int,
    val name: String,
    val rate: Double,
    val thumbnail: String
)