package com.usda_nutrient.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Item (
    val FoodGroup: String,
    val Description: String,
    val Energy_kcal: Int,
    val Protein_g: Int,
    val Fat_g: Int,
    @BsonId
    val ID: String = ObjectId().toString()
)