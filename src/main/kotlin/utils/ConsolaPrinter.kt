package main.kotlin.utils

import main.kotlin.data.Event
import main.kotlin.data.User
import main.kotlin.repositories.EventRepository

    object ConsolaPrinter {

        fun mostrarCarteleraEventos(eventos: List<Event>) {
            if (eventos.isEmpty()) {
                println("${Emojis.WARNING} No hay eventos disponibles en este momento")
            } else {
                println("\n${Emojis.MUSIC} CARTELERA DE EVENTOS ${Emojis.MUSIC}")
                println("=".repeat(40))
                eventos.forEach { evento ->
                    println("${Emojis.MICROPHONE} Artista: ${evento.artist}")
                    println("${Emojis.LOCATION} Lugar:   ${evento.location}")
                    println("${Emojis.CALENDAR} Fecha:   ${evento.date}")
                    println("${Emojis.TICKET} Tickets: ${evento.soldTickets}/${evento.localidadesDisponibles}")
                    println("-".repeat(40))
                }
            }
        }

        fun mostrarEntradasDeUsuario(user: User, eventRepo: EventRepository) {
            if (user.tickets.isEmpty()) {
                println("${Emojis.WARNING} Aún no tienes tickets comprados")
            } else {
                println("\n${Emojis.MUSIC} TUS ENTRADAS ${Emojis.MUSIC}")
                println("=".repeat(40))
                user.tickets.forEach { ticket ->
                    val evento = eventRepo.findById(ticket.eventId)
                    println("${Emojis.TICKET} ENTRADA #${ticket.id}")
                    println("${Emojis.MICROPHONE} Evento:   ${evento?.artist ?: "Desconocido"}")
                    println("${Emojis.LOCATION} Lugar:    ${evento?.location ?: "N/A"}")
                    println("${Emojis.CALENDAR} Fecha:    ${evento?.date ?: "N/A"}")
                    println("${Emojis.PEOPLE} Cantidad de entradas: ${ticket.quantity}")
                    println("${Emojis.SEAT} Sección elegida:  ${ticket.section}")
                    println("-".repeat(40))
                }

            }
        }
        fun mostrarEntradasUtilizadas (user: User, eventRepo: EventRepository){
            val usadas = user.tickets.filter { it.usado}
            if(usadas.isEmpty()) {
                println ("${Emojis.WARNING} No  tenés entradas utilizadas todavía")
            }else{
                println("\n${Emojis.TICKET}ENTRADAS UTILIZADAS ${Emojis.TICKET}")
                println("=".repeat(40))
                usadas.forEach { ticket ->
                    val evento = eventRepo.findById(ticket.eventId)
                    println("${Emojis.TICKET} ENTRADA #${ticket.id}")
                    println("${Emojis.MICROPHONE} Evento:   ${evento?.artist ?: "Desconocido"}")
                    println("${Emojis.LOCATION} Lugar:    ${evento?.location ?: "N/A"}")
                    println("${Emojis.CALENDAR} Fecha:  ${evento?.date ?: "N/A"}")
                    println("${Emojis.SEAT} Sección:  ${ticket.section}")
                    println("${Emojis.CHECK}  Estado:   ${ticket.usado}")
                    println("-".repeat(40))

                }
            }

        }


    }

