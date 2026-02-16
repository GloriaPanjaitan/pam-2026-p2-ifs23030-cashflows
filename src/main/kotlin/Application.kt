package org.delcom

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.delcom.routing.configureRouting
import org.delcom.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, host = "0.0.0.0", port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization() // fungsi konfigurasi JSON/ContentNegotiation
    configureRouting()       // routing utama
}
