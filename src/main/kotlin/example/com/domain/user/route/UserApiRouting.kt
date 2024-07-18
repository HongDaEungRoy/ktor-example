package example.com.domain.user.route

import example.com.domain.user.route.request.UserCreateRequest
import example.com.domain.user.service.UserService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.configureUserRoute() {
    userGetRoutes()
    userPostRoutes()
}

private fun Route.userGetRoutes() {
    val userService by inject<UserService>()

    get("/user/{userId}", {
        request { pathParameter<Long>("userId"){ required = true } }
    }) {
        // call.parameters or queryParameter
        val userId = call.parameters["userId"]?.toLongOrNull()
            ?: return@get call.respond(HttpStatusCode.BadRequest)
        val user = userService.getById(userId)
        call.respond(HttpStatusCode.OK, user)
    }
}

private fun Route.userPostRoutes() {
    val userService by inject<UserService>()

    post("/user", {
        description = "Create a new user"
        request { body<UserCreateRequest>() }
    }) {
        val requestDto = call.receive<UserCreateRequest>()
        userService.create(requestDto.name, requestDto.age)
        call.respond(HttpStatusCode.OK)
    }
}