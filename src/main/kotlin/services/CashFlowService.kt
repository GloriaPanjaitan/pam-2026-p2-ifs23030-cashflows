package org.delcom.services

import org.delcom.data.CashFlowQuery
import org.delcom.data.CashFlowRequest
import org.delcom.entities.CashFlow
import org.delcom.repositories.ICashFlowRepository
import java.time.Instant
import java.util.*

class CashFlowService(
    private val repository: ICashFlowRepository
) : ICashFlowService {

    override fun getAllCashFlows(query: CashFlowQuery): List<CashFlow> {
        var data = repository.getAll()

        query.type?.let { typeValue ->
            data = data.filter { it.type.equals(typeValue, true) }
        }

        query.source?.let { sourceValue ->
            data = data.filter { it.source.equals(sourceValue, true) }
        }

        query.labels?.let { labelsValue ->
            val labelList = labelsValue.split(",")
            data = data.filter { cashFlow ->
                labelList.any { label ->
                    cashFlow.label.contains(label.trim(), true)
                }
            }
        }

        query.gteAmount?.let { min ->
            data = data.filter { it.amount >= min }
        }

        query.lteAmount?.let { max ->
            data = data.filter { it.amount <= max }
        }

        query.search?.let { keyword ->
            data = data.filter {
                it.description.contains(keyword, true)
            }
        }

        return data
    }

    override fun getCashFlowById(id: String): CashFlow? =
        repository.getById(id)

    override fun createCashFlow(request: CashFlowRequest): CashFlow {
        val now = Instant.now().toString()

        val newData = CashFlow(
            id = UUID.randomUUID().toString(),
            type = request.type,
            source = request.source,
            label = request.label,
            amount = request.amount,
            description = request.description,
            createdAt = now,
            updatedAt = now
        )

        return repository.insert(newData)
    }

    override fun createRawCashFlow(
        id: String,
        type: String,
        source: String,
        label: String,
        amount: Double,
        description: String,
        createdAt: String,
        updatedAt: String
    ): CashFlow {

        val newData = CashFlow(
            id = id,
            type = type,
            source = source,
            label = label,
            amount = amount,
            description = description,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

        return repository.insert(newData)
    }

    override fun updateCashFlow(id: String, request: CashFlowRequest): CashFlow? {

        val existing = repository.getById(id) ?: return null

        val updated = existing.copy(
            type = request.type,
            source = request.source,
            label = request.label,
            amount = request.amount,
            description = request.description,
            updatedAt = Instant.now().toString()
        )

        repository.update(updated)

        return updated
    }

    override fun removeCashFlow(id: String): Boolean =
        repository.delete(id)

    override fun getCashFlowTypes(): List<String> =
        repository.getAll().map { it.type }.distinct()

    override fun getCashFlowSources(): List<String> =
        repository.getAll().map { it.source }.distinct()

    override fun getCashFlowLabels(): List<String> =
        repository.getAll()
            .flatMap { it.label.split(",") }
            .map { it.trim() }
            .distinct()
}
