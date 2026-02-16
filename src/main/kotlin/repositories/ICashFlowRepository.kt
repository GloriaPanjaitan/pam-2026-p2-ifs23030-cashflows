package org.delcom.repositories

import org.delcom.entities.CashFlow

interface ICashFlowRepository {
    fun getAll(): List<CashFlow>
    fun getById(id: String): CashFlow?
    fun insert(data: CashFlow): CashFlow
    fun update(data: CashFlow): Boolean
    fun delete(id: String): Boolean
}
