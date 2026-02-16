import io.ktor.server.application.*
import io.ktor.server.routing.*
import controllers.cashFlowController
import repositories.CashFlowRepository
import services.CashFlowService

fun Application.configureRouting() {

    val repository = CashFlowRepository()
    val service = CashFlowService(repository)

    routing {
        cashFlowController(service)
    }
}
