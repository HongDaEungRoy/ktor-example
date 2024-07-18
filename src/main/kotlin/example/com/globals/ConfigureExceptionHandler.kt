package example.com.globals

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureExceptionHandler() {
    install(StatusPages) {
        exception<CommonException> { call, cause ->
            call.respond(HttpStatusCode.fromValue(cause.code.toInt()), mapOf(
                "code" to cause.code,
                "message" to cause.message
            ))
        }

        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf(
                "code" to "500",
                "message" to (cause.message ?: "An unknown error occurred")
            ))
        }
    }
}