package main.kotlin.data

data class
User(
    val id: Long,
     val nickname: String,
   val password: String,
    val name: String,
   val surname: String,
    var money: Double,
    val createdDate: String,
    val tickets: MutableList<Ticket> = mutableListOf()
)
