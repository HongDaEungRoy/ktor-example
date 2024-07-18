package example.com.globals


import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*
import io.ktor.server.webjars.*

fun Application.configureSwagger() {
    install(Webjars) { path= "/webjars" }
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "Example API"
            version = "latest"
            description = "Example API for testing and demonstration purposes."
        }

        server {
            url = "http://localhost:8080"
            description = "Development Server"
        }
    }
//    routing {
//        swaggerUI(path = "swagger")
//    }

}

