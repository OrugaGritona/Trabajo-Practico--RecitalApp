package main.kotlin.payments

class MercadoPago: MedioDePago() {
    override fun calcularTotal(montoBase: Double): Double =montoBase * 1.02
override  val name: String ="MercadoPago"
}