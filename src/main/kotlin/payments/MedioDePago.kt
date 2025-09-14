package main.kotlin.payments
import java.time.*

abstract class MedioDePago {
abstract val name: String
    abstract fun calcularTotal(montoBase: Double): Double

    fun obtenerTodosTiposDePago(): List<MedioDePago> = listOf(
        MercadoPago(),
        Visa(),
        MasterCard()
    )


}

