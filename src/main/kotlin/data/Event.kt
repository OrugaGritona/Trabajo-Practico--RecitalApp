package main.kotlin.data

data class Event(
     val id: Long,
     val date: String,
     val time: String,
     val location: String,
     val artist: String,
     val image: String,
     val capacity: Int,
     var soldTickets: Int = 0,
     var localidadesDisponibles: Int
) {

}
