package main.kotlin

import main.kotlin.data.User
import main.kotlin.menu.MenuInicio
import main.kotlin.menu.MenuOption
import main.kotlin.payments.MasterCard
import main.kotlin.payments.MercadoPago
import main.kotlin.payments.Visa
import main.kotlin.repositories.EventRepository
import main.kotlin.repositories.TicketCollectionRepository
import main.kotlin.repositories.TicketRepository
import main.kotlin.repositories.UserRepository
import main.kotlin.services.TicketService
import main.kotlin.services.UserService
import main.kotlin.utils.Emojis
import main.kotlin.utils.ConsolaPrinter
import java.time.LocalDate

fun main() {
    // Repositorios
    val userRepo = UserRepository
    val eventRepo = EventRepository
    val ticketRepo = TicketRepository
    val ticketCollectionRepo = TicketCollectionRepository

    // Servicios
    val userService = UserService(userRepo)
    val ticketService = TicketService(eventRepo, ticketRepo)

    // ========================
    // PANTALLA DE INICIO
    // ========================
    var loggedIn = false
    do {
        println("\n${Emojis.MUSIC}${Emojis.MUSIC} ============= RECITALAPP =============")
        MenuInicio.values().forEach { println("${it.option}. ${it.description}") }
        print("Seleccione una opción: ")


        val opcionInicio = readln().toIntOrNull()
        val inicio = MenuInicio.election(opcionInicio ?: -1)



        when (inicio) {
            MenuInicio.LOGIN -> {
                loggedIn = mostrarPantallaLogin(userService)
            }

            MenuInicio.REGISTER -> {
                mostrarPantallaRegistro(userRepo)
            }

            MenuInicio.EXIT -> {
                println("${Emojis.WAVE} Hasta pronto!")
                return
            }

            null -> errorMsg("${Emojis.ERROR} Opción no válida")
        }
    } while (!loggedIn)

    // ========================
    // MENÚ PRINCIPAL
    // ========================
    var opcion: MenuOption?
    do {
        println("\n============= MENÚ PRINCIPAL =============")
        MenuOption.values().forEach (imprimirOpcion)
        print("Seleccione una opción: ")

        val opcionIngresada = readln().toIntOrNull()
        opcion = if (opcionIngresada != null) {
            MenuOption.elegirOpcion(opcionIngresada)
        } else null

        when (opcion) {
            MenuOption.BUY_TICKETS -> {
                val user = userService.getLoggedUser()
                if (user == null) {
                    errorMsg("${Emojis.WARNING} Primero debe iniciar sesión")
                } else {

                    //Las funciones para mostrar los eventos, las entradas compradas y las entradas ya utilizadas
                    //están dentro de la clase ConsolaPrinter

                    ConsolaPrinter.mostrarCarteleraEventos(ticketService.listarEventos())

                    print("Ingrese ID del evento: ")
                    val eventoId = readln().toLongOrNull() ?: -1L

                    print("Cantidad de entradas: ")
                    val cantidad = readln().toIntOrNull() ?: 0

                    print("Sección: ")
                    val seccion = readln()

                    println("Seleccione medio de pago: 1. MercadoPago  2. Visa  3. MasterCard")
                    val medioPago = when (readln().toIntOrNull()) {
                        1 -> MercadoPago()
                        2 -> Visa()
                        3 -> MasterCard()
                        else -> MercadoPago()
                    }

                    try {
                        ticketService.comprarTicket(user, eventoId, cantidad, seccion, medioPago)
                        println("${Emojis.CHECK} Compra realizada con éxito. Su saldo restante: ${user.money}")
                    } catch (e: Exception) {
                        errorMsg("${Emojis.ERROR} ${e.message}")
                    }
                }
            }

            MenuOption.VIEW_TICKETS -> {
                val user = userService.getLoggedUser()
                if (user == null) {
                    errorMsg("${Emojis.WARNING} Debe iniciar sesión primero")
                } else {
                    ConsolaPrinter.mostrarEntradasDeUsuario(user, eventRepo)
                }
            }

            MenuOption.VIEW_EVENTS -> {
                ConsolaPrinter.mostrarCarteleraEventos(ticketService.listarEventos())
            }

            MenuOption.VIEW_USED_TICKETS -> {
                val user =userService.getLoggedUser()
                if(user ==null){
                    errorMsg("${Emojis.WARNING} Debe iniciar sesión primero")
                }else{
                    ticketService.marcarticketUsado(user)
                    ConsolaPrinter.mostrarEntradasUtilizadas(user, eventRepo)
                }


            }

            MenuOption.EXIT -> println("${Emojis.WAVE} Hasta pronto!")
            null -> errorMsg("${Emojis.ERROR} Opción no válida")
        }
    } while (opcion != MenuOption.EXIT)
}

// Función para imprimir errores
fun errorMsg(message: String) = System.err.println(message)


//Función que imprime login
fun mostrarPantallaLogin(userService: UserService): Boolean {
    print(" Ingrese su nickname: ")
    val nick = readln()
    print(" Ingrese su password: ")
    val pass = readln()

    return if (userService.login(nick, pass)) {
        println("${Emojis.CHECK} Bienvenido $nick, login exitoso")
        true
    } else {
        errorMsg("${Emojis.ERROR} Usuario o contraseña incorrectos")
        false
    }
}

//Función para registro de usuario nuevo
fun mostrarPantallaRegistro(userRepo: UserRepository) {
    print("${Emojis.POINT_RIGHT}Ingrese nombre: ")
    val name = readln()
    print("${Emojis.POINT_RIGHT}Ingrese apellido: ")
    val surname = readln()

    print("Ingrese nickname: ")
    val nickname = readln()
    if (userRepo.registeredUser(nickname)) {
        errorMsg("${Emojis.ERROR} El nickname ya existe")
        return
    }

    print("Ingrese contraseña: ")
    val password = readln()

    print("Ingrese monto inicial: ")
    val money = readln().toDoubleOrNull()
    if (money == null || money <= 0) {
        errorMsg("${Emojis.ERROR} El monto inicial debe ser mayor a 0")
        return
    }

    val newId = (userRepo.getAll().maxOfOrNull { it.id } ?: 1000L) + 1

    val newUser = User(
        id = newId,
        nickname = nickname,
        password = password,
        name = name,
        surname = surname,
        money = money,
        createdDate = LocalDate.now().toString()
    )

    userRepo.add(newUser)
    println("${Emojis.CHECK} Usuario registrado con éxito. ID asignado: ${newUser.id}")
}



//Lambda para imprimir las opciones de menú y su descripción
private val imprimirOpcion: (MenuOption) -> Unit = { opcion ->
    println("${opcion.option}. ${opcion.description}")
}

