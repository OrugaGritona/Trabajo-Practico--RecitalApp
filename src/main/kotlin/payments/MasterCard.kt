package main.kotlin.payments

import java.time.DayOfWeek
import java.time.LocalDate

class MasterCard : MedioDePago() {
    override fun calcularTotal(montoBase: Double): Double {
        val hoy = LocalDate.now().dayOfWeek
        val esFinde = hoy == DayOfWeek.SATURDAY || hoy == DayOfWeek.SUNDAY
        return  if (esFinde) {
            montoBase * 1.03
        }else {
            montoBase * 1.0075
        }
    }
override val name: String = "MasterCard"
}