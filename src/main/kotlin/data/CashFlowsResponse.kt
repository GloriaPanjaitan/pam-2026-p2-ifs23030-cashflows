package data

import entities.CashFlow
import kotlinx.serialization.Serializable

@Serializable
data class CashFlowsResponse(
    val success: Boolean,
    val data: List<CashFlow>
)
