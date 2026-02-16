package org.delcom.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.delcom.controllers.cashFlowController
import org.delcom.repositories.CashFlowRepository
import org.delcom.services.CashFlowService

fun Application.configureRouting() {

    val repository = CashFlowRepository()
    val service = CashFlowService(repository)

    routing {
        cashFlowController(service)
    }
}
