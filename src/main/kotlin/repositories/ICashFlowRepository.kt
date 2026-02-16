package repositories

import entities.CashFlow

interface ICashFlowRepository {
    fun getAll(): List<CashFlow>
    fun getById(id: String): CashFlow?
    fun insert(data: CashFlow): CashFlow
    fun update(data: CashFlow): Boolean
    fun delete(id: String): Boolean
}
