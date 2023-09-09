package com.gumu.tracker_data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("product_name")
    val productName: String?,
    @SerializedName("image_front_thumb_url")
    val imageFrontThumbUrl: String?,
    val nutriments: NutrimentsDto
)
