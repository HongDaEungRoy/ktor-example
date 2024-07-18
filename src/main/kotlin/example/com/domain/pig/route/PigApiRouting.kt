package example.com.domain.pig.route

import example.com.domain.pig.route.request.PigCreateRequest
import example.com.domain.pig.service.PigService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject

fun Route.configurePigRoute() {
    pigGetRoutes()
    pigPostRoutes()
}

private fun Route.pigGetRoutes() {
    val pigService by inject<PigService>()

    get("/pig/{pigId}", {
        request { pathParameter<ObjectId>("pigId"){ required = true } }
    }) {
        call.parameters["pigId"]?.let {
            val pig = pigService.getById(ObjectId(it))
            call.respond(HttpStatusCode.OK, pig)
        } ?: return@get call.respond(HttpStatusCode.BadRequest)
    }
}

private fun Route.pigPostRoutes() {
    val pigService by inject<PigService>()

    post("/pig", {
        description = "create a new pig"
        request { body<PigCreateRequest>() }
    }) {
        val requestDto = call.receive<PigCreateRequest>()
        pigService.create(requestDto.ownerId, requestDto.pigNm, requestDto.weight)
        call.respond(HttpStatusCode.OK)
    }
}