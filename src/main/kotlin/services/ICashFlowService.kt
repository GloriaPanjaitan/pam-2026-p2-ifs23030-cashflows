package org.delcom.services

import org.delcom.entities.CashFlow
import org.delcom.data.CashFlowQuery
import org.delcom.data.CashFlowRequest

interface ICashFlowService {

    fun getAllCashFlows(query: CashFlowQuery): List<CashFlow>

    fun getCashFlowById(id: String): CashFlow?

    fun createCashFlow(request: CashFlowRequest): CashFlow

    fun createRawCashFlow(
        id: String,
        type: String,
        source: String,
        label: String,
        amount: Double,
        description: String,
        createdAt: String,
        updatedAt: String
    ): CashFlow

    fun updateCashFlow(id: String, request: CashFlowRequest): CashFlow?

    fun removeCashFlow(id: String): Boolean

    fun getCashFlowTypes(): List<String>

    fun getCashFlowSources(): List<String>

    fun getCashFlowLabels(): List<String>
}
