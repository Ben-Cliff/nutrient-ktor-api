package com.usda_nutrient.plugins

import com.usda_nutrient.routes.itemsRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        itemsRoutes()
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
