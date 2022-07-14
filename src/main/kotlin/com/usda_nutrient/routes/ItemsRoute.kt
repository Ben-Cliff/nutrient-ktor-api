package com.usda_nutrient.routes

import com.usda_nutrient.data.getItemForId
import com.usda_nutrient.data.requests.ItemsRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.itemsRoutes() {
    // TODO: 14.07.22 create better response handling with null values 
    route("/get-item") {
        get {
            val itemId = call.receive<ItemsRequest>().id
            val item = getItemForId(itemId)
            // if item is not null, ie item does not exsist with this ID
            item?.let {
                call.respond(
                    HttpStatusCode.OK,
                    it
                )
            } // Else item is null return  null
                ?: call.respond(
                HttpStatusCode.OK,
                it
            )
        }
    }
}



