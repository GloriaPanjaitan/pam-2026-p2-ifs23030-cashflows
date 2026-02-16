package org.delcom

import io.ktor.server.application.Application
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.delcom.plugins.configureSerialization
import org.delcom.routing.configureRouting
import java.net.ServerSocket

fun main() {
    val port = findAvailablePort(8080, 8090) // cek dari 8080 sampai 8090
    embeddedServer(Netty, host = "0.0.0.0", port = port) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}

// Fungsi untuk cari port yang belum dipakai
fun findAvailablePort(start: Int, end: Int): Int {
    for (p in start..end) {
        try {
            ServerSocket(p).use { socket ->
                return p // port available
            }
        } catch (_: Exception) {
            // port sedang dipakai, lanjut ke port berikutnya
        }
    }
    throw RuntimeException("Tidak ada port kosong antara $start dan $end")
}
