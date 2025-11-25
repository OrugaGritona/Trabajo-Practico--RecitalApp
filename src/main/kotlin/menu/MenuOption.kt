package main.kotlin.menu

enum class MenuOption(val option: Int, val description: String) {


    BUY_TICKETS(1, "Comprar entradas"),
    VIEW_TICKETS(2, "Ver mis entradas"),
    VIEW_EVENTS(3,"Ver recitales disponibles en cartelera"),
   VIEW_USED_TICKETS(4,"Entradas ya utilizadas"),
    EXIT (0,"Salir");

    companion object{
        fun elegirOpcion(value: Int): MenuOption? =values().find {
            it.option == value
        }
    }


}