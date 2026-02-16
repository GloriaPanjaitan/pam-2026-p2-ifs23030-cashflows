package org.delcom.data

import org.delcom.entities.CashFlow
import kotlinx.serialization.Serializable

@Serializable
data class CashFlowsResponse(
    val success: Boolean,
    val data: List<CashFlow>
)
