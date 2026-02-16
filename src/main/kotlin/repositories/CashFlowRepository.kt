package org.delcom.repositories

import org.delcom.entities.CashFlow

class CashFlowRepository : ICashFlowRepository {

    private val cashFlows = mutableListOf<CashFlow>()

    override fun getAll(): List<CashFlow> = cashFlows

    override fun getById(id: String): CashFlow? =
        cashFlows.find { it.id == id }

    override fun insert(data: CashFlow): CashFlow {
        cashFlows.add(data)
        return data
    }

    override fun update(data: CashFlow): Boolean {
        val index = cashFlows.indexOfFirst { it.id == data.id }
        if (index == -1) return false
        cashFlows[index] = data
        return true
    }

    override fun delete(id: String): Boolean =
        cashFlows.removeIf { it.id == id }
}
