package com.usda_nutrient.data

data class ResponseHandler<T> (
    val status : Boolean,
    val message: String,
    val data: T
    )