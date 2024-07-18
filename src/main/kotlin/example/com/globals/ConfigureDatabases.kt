package example.com.globals

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import example.com.domain.user.entity.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun configureDatabases() {
    Database.connect(
        url = "",
        driver = "oracle.jdbc.OracleDriver",
        user = "",
        password = "",
        databaseConfig = DatabaseConfig {
            defaultIsolationLevel = Connection.TRANSACTION_READ_COMMITTED
        },
    )

    transaction { SchemaUtils.create(Users) }
}