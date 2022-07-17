package com.usda_nutrient.data

import com.usda_nutrient.data.model.Item
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.eq

private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("NutritionalDatabase")

private val items = database.getCollection<Item>()

suspend fun getItemForId(id: String): Item? {
    // find the items with this id among all the items
    return items.findOneById(id)
}

// Function that adds new item to database or, if item already exsists, update it.
// Takes in an item and returns a boolean
suspend fun createIdOrUpdateIdForId(item: Item): Boolean {
    // variable itemExists checks if item exists
    val itemExists = items.findOneById(item.id) != null
    // if such a user exists, update the user with the new item information
    return if (itemExists) {
        items.updateOneById(item.id, item).wasAcknowledged()
    }
    // if user is new, create an id and save it to the database.
    else {
        item.id = ObjectId().toString()
        items.insertOne(item).wasAcknowledged() //wasAcknowledged returns true if query is successful
    }
}

suspend fun deleteItemForId(itemId: String): Boolean {
    val item = items.findOne(Item::id eq itemId)
    item?.let { item ->
        return items.deleteOneById(item.id).wasAcknowledged()
    } ?: return false }


