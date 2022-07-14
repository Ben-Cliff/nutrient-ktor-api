package com.usda_nutrient.data

import com.usda_nutrient.data.model.Item
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("NutritionalDatabase")

private val items = database.getCollection<Item>()