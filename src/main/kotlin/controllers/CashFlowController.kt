package org.delcom.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.delcom.data.*
import org.delcom.helpers.loadInitialData
import org.delcom.services.ICashFlowService

fun Route.cashFlowController(service: ICashFlowService) {

    route("/cash-flows") {

        post("/setup") {
            setupData(call, service)
        }

        get {
            val query = CashFlowQuery(
                type = call.request.queryParameters["type"],
                source = call.request.queryParameters["source"],
                labels = call.request.queryParameters["labels"],
                gteAmount = call.request.queryParameters["gteAmount"]?.toDoubleOrNull(),
                lteAmount = call.request.queryParameters["lteAmount"]?.toDoubleOrNull(),
                search = call.request.queryParameters["search"],
                startDate = call.request.queryParameters["startDate"],
                endDate = call.request.queryParameters["endDate"]
            )

            val result = service.getAllCashFlows(query)
            call.respond(DataResponse("success", "OK", result))
        }

        get("/{id}") {
            val id = call.parameters["id"]!!
            val result = service.getCashFlowById(id)
            call.respond(DataResponse("success", "OK", result))
        }

        post {
            val request = call.receive<CashFlowRequest>()
            val created = service.createCashFlow(request)
            call.respond(DataResponse("success", "Created", created))
        }

        put("/{id}") {
            val id = call.parameters["id"]!!
            val request = call.receive<CashFlowRequest>()
            val updated = service.updateCashFlow(id, request)
            call.respond(DataResponse("success", "Updated", updated))
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!
            service.removeCashFlow(id)
            call.respond(DataResponse("success", "Deleted", null))
        }

        get("/types") {
            call.respond(DataResponse("success", "OK", service.getCashFlowTypes()))
        }

        get("/sources") {
            call.respond(DataResponse("success", "OK", service.getCashFlowSources()))
        }

        get("/labels") {
            call.respond(DataResponse("success", "OK", service.getCashFlowLabels()))
        }
    }
}

suspend fun setupData(call: ApplicationCall, service: ICashFlowService) {

    val existing = service.getAllCashFlows(CashFlowQuery())
    existing.forEach { service.removeCashFlow(it.id) }

    val init = loadInitialData()
    init.forEach {
        service.createRawCashFlow(
            it.id,
            it.type,
            it.source,
            it.label,
            it.amount,
            it.description,
            it.createdAt,
            it.updatedAt
        )
    }

    call.respond(DataResponse("success", "Berhasil memuat data awal", null))
}
