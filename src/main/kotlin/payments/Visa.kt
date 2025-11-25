package main.kotlin.payments

import java.time.LocalTime

class Visa : MedioDePago() {
    override fun calcularTotal(montoBase: Double): Double {
        val hora = LocalTime.now()
        val horaInicio = LocalTime.of(15, 0)
        val horaFin = LocalTime.of(22, 30)
        return if (hora.isAfter(horaInicio) && hora.isBefore(horaFin)) {
            montoBase * 1.01
        } else {
            montoBase * 1.03
        }
    }
override val name: String = "Visa"
}