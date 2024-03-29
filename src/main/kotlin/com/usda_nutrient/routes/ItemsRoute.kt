package com.usda_nutrient.routes

import com.usda_nutrient.data.ResponseHandler
import com.usda_nutrient.data.createIdOrUpdateIdForId
import com.usda_nutrient.data.deleteItemForId
import com.usda_nutrient.data.getItemForId
import com.usda_nutrient.data.model.Item
import com.usda_nutrient.data.requests.DeleteItemRequest
import com.usda_nutrient.data.requests.ItemRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// TODO: 14.07.22 create better response handling with null values
fun Route.itemsRoutes() {
    // route for com.usda_nutrient.data.getItemForId
    route("/get-item") {
        get {
            val itemId = call.receive<ItemRequest>().id
            val item = getItemForId(itemId)
            // if item is not null, ie item does exsist with this ID
        /* Function Learn!
        _let is often used for executing a code block only with non-null values.
        To perform actions on a non-null object, use the safe call operator ?.
        on it and call let with the actions in its lambda.
        */
          // TODO: get-item is able to query the database to know if the ID exsists however does not return any values save for 500 Internal Server Error ;(
            item?.let {
                call.respond(
                    HttpStatusCode.OK,
                    ResponseHandler(true, "Food item successfully retrieved", it)
                )
            } // Else item is null return  null
                ?: call.respond(
                    HttpStatusCode.OK,
                    ResponseHandler(true, "There is no Food item with this id", it)
            )
        }
    }

    // route for com.usda_nutrient.data.createIdOrUpdateIdForId
    route("/create-update-item"){
        post{
            // if an incorrect or missing parameter is sent by the client, return bad request as httpstatus
            val request = try {
                call.receive<Item>()
                // TODO: 15.07.22 change throwable exception to  ContentTransformationException
            }catch(e:Exception){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            //no problem with parameters sent by client, send result
            if(createIdOrUpdateIdForId(request)){
                call.respond(
                    HttpStatusCode.OK,
                    ResponseHandler(true, "Food item successfully created/ updated", Unit)
                )
            }
            //If there is a conflict (server state not ready and prevents post)
            else{
                call.respond(HttpStatusCode.Conflict)
            }

        }
    }

    // TODO: can take in an empty id string
    route("/delete-item") {
        post {
            val request = try {
                call.receive<DeleteItemRequest>()
            } catch (e:Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            if (deleteItemForId(request.id)) {
                call.respond(
                    HttpStatusCode.OK,
                    ResponseHandler(true, "Food item successfully deleted", Unit)
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    ResponseHandler(true, "Food item not found", Unit)
                )
            }
        }
    }
}



