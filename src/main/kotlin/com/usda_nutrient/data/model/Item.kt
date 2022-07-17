package com.usda_nutrient.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

// TODO: Allowing nullable inputs as this was somehow interfering with the delete route (error MissingKotlinParameterException https://github.com/FasterXML/jackson-module-kotlin/issues/87)
//  To investigate how this hampers API performance and scalability.
data class Item (
    val FoodGroup: String?,
    val Description: String?,
    val Energy_kcal: Int?,
    val Protein_g: Int?,
    val Fat_g: Int?,
    @BsonId
    var id: String = ObjectId().toString()
)