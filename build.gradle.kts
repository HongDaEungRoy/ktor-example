
val kotlin_version: String by project
val logback_version: String by project
val ojdbc11Version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
     maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation(platform("io.ktor:ktor-bom:2.3.6"))
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-webjars-jvm")
    implementation("io.github.smiley4:ktor-swagger-ui:2.9.0")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.insert-koin:koin-ktor:3.5.0")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-jackson")

    implementation("org.jetbrains.exposed:exposed-core:0.52.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.52.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.52.0")
    implementation("com.oracle.database.jdbc:ojdbc11:$ojdbc11Version")

////    kotlin 공식 mongo driver
//    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.2")
//    implementation("org.mongodb:bson-kotlinx:5.1.2")
//    implementation("org.mongodb:bson-kotlinx:5.1.2")

    implementation("org.jetbrains.kotlinx:dataframe:0.13.1")
    implementation("org.redundent:kotlin-xml-builder:1.8.0")
    implementation("org.apache.poi:poi:5.3.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")
    //implementation("io.ktor:ktor-server-multipart")

    implementation("org.litote.kmongo:kmongo:5.1.0")
    implementation("org.litote.kmongo:kmongo-coroutine:5.1.0")

//    implementation("io.ktor:ktor-server-swagger: 2.3.12")
//    implementation("io.ktor:ktor-server-openapi: 2.3.12")


    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
