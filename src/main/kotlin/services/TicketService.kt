package main.kotlin.services

import main.kotlin.data.Event
import main.kotlin.data.Ticket
import main.kotlin.data.TicketCollection
import main.kotlin.data.User
import main.kotlin.exceptions.SinSaldoSuficienteException
import main.kotlin.payments.MedioDePago
import main.kotlin.repositories.EventRepository
import main.kotlin.repositories.TicketCollectionRepository
import main.kotlin.repositories.TicketRepository
import java.time.LocalDate

class TicketService(
    private val eventRepository: EventRepository,
    private val ticketRepository: TicketRepository
) {
    companion object {
        const val PRECIO_BASE_TICKET = 10000.0
    }

    // Listar todos los eventos
    fun listarEventos(): List<Event> = eventRepository.getAll()

    // Listar tickets desde el repositorio de colecciones
    fun mostrarTickets(): List<TicketCollection> = TicketCollectionRepository.get()

    // Comprar ticket(s) para un usuario
    fun comprarTicket(
        user: User,
        eventId: Long,
        cantidad: Int,
        seccion: String,
        medioDePago: MedioDePago
    ) {
        val evento = eventRepository.findById(eventId)
            ?: throw Exception("Evento no encontrado")

        // Verificar capacidad
        if (!hasCapacity(evento, cantidad)) {
            throw Exception("El recinto alcanzó su capacidad máxima")
        }

        // Calcular costo con el medio de pago
        val costoBase = cantidad * PRECIO_BASE_TICKET
        val costoFinal = medioDePago.calcularTotal(costoBase)

        // Verificar saldo del usuario
        if (user.money < costoFinal) {
            throw SinSaldoSuficienteException("El usuario no tiene dinero suficiente para la compra")
        }

        // Descontar saldo
        user.money -= costoFinal

        // Crear ticket y guardarlo
        val ticket = Ticket(0, eventId, cantidad, seccion)
        val ticketGuardado = ticketRepository.add(ticket)
        user.tickets.add(ticketGuardado)

        // Actualizar evento
        evento.soldTickets += cantidad
    }

    // Validación de capacidad disponible
    private fun hasCapacity(evento: Event, cantidad: Int): Boolean {
        return evento.soldTickets + cantidad <= evento.localidadesDisponibles
    }
    fun marcarticketUsado(user: User) {
        val hoy =LocalDate.now()
        user.tickets.forEach { ticket ->
            val evento =eventRepository.findById(ticket.eventId)
            if(evento != null && LocalDate.parse(evento.date).isBefore (hoy)){
                ticket.usado = true;
            }

        }
    }

}
