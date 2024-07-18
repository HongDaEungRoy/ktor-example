package example.com.globals

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.reactivestreams.client.MongoClient
import example.com.domain.pig.dao.PigRepository
import example.com.domain.pig.service.PigService
import example.com.domain.user.dao.UserRepository
import example.com.domain.user.service.UserService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.litote.kmongo.KMongo

fun Application.configureKoin() {
    install(Koin) {
        modules(appModule)
    }
}

val appModule = module {
    single {
        val connectionString =
            ""

        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()

        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()

        KMongo.createClient(settings) //by KMongo
        //coroutineMongoClient.create(settings) by 공식 드라이버
    }
    single { get<MongoClient>().getDatabase("testmongo") }
    single { PigRepository(database = get()) }
    single { PigService(pigRepository = get()) }

    single { UserRepository() }
    single { UserService(userRepository = get()) }
}
