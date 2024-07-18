package example.com

import example.com.domain.pig.route.configurePigRoute
import example.com.domain.user.route.configureUserRoute
import example.com.globals.configureDatabases
import example.com.globals.configureExceptionHandler
import example.com.globals.configureKoin
import example.com.globals.configureSwagger
import example.com.util.DataFrameUtils
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.kotlinx.dataframe.api.count
import java.io.File

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSwagger()
    routing { apiRoute() }
    configureKoin()
    configureExceptionHandler()
    install(ContentNegotiation) { jackson() }
    configureDatabases()
}

fun Routing.apiRoute() {
    route("/api/v1") {
        configureUserRoute()
        configurePigRoute()
        configureExcelRoute()
    }
}

fun Route.configureExcelRoute() {
    post("/excel", {
        description = "upload excel"
        request {
            multipartBody {
                part<PartData.FileItem>("file") {
                    require(true)
                }
            }
        }
    }) {
        var fileBytes: ByteArray? = null
        var fileName: String? = null

        call.receiveMultipart().forEachPart { part ->
            if (part is PartData.FileItem) {
                fileName = part.originalFileName ?: "unknown"
                fileBytes = part.streamProvider().readBytes()
                part.dispose()
            }
        }

        if (fileBytes != null && fileName != null) {
            val tempFile = File.createTempFile("temp", ".xlsx")
            tempFile.writeBytes(fileBytes!!)

            try {
                val df = DataFrameUtils.excelToDataFrame(tempFile.absolutePath)
                val jsonResult = DataFrameUtils.dataFrameToJson(df, prettyPrint = true)
                call.respondText(jsonResult, ContentType.Application.Json)
            } finally {
                tempFile.delete()
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "No file received or invalid file")
        }
    }

}
