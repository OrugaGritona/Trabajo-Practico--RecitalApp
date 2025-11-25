package main.kotlin.repositories

import main.kotlin.data.Event

object EventRepository {

    private val events = mutableListOf<Event>()

    init {
        events.add(
            Event(
                1L,
                "2025-10-02",
                "21:00",
                "Luna Park",
                "Abel Pintos",
                "https://i.pinimg.com/originals/ea/3c/84/ea3c844c21b0812535bafe66358a213d.jpg",
                8000,
                localidadesDisponibles = 7000

            )
        )

        events.add(
            Event(
                2L,
                "2025-12-29",
                "20:00",
                "Estadio River Plate",
                "Duki",
                "https://nuebo.es/wp-content/uploads/2023/02/P2250134.jpg",
                80000,
                localidadesDisponibles = 77000
            )
        )

        events.add(
            Event(
                3L,
                "2021-07-30",
                "22:00",
                "Estadio Velez Sarsfield",
                "Fito Paez",
                "https://valaaguelaquesipuedo.com/wp-content/uploads/2017/02/FITO-PAEZ-768x1024.jpg",
                50000,
                localidadesDisponibles = 48000
            )
        )

        events.add(
            Event(
                4L,
                "2025-11-16",
                "20:00",
                "Teatro Gran Rex",
                "Tini",
                "https://www.hitfm.es/wp-content/uploads/2021/11/TINI-4-768x1024.jpg",
                3000,
                localidadesDisponibles = 2700
            )
        )

        events.add(
            Event(
                5L,
                "2025-09-21",
                "19:00",
                "Movistar Arena",
                "La Renga",
                "https://www.lapoliticaonline.com/files/image/252/252925/67fd77a5c3b5d-screen-and-max-width768px_768_1024!.jpg?s=e69c0d47fdddc9b64d747994d26f0bc2&d=1751382069",
                15000,
                localidadesDisponibles = 13000
            )
        )

        events.add(
            Event(
                6L,
                "2025-11-09",
                "21:00",
                "Hipodromo de Palermo",
                "Bizarrap",
                "https://urbandamagazine.com/wp-content/uploads/2023/01/CROP-Bizarrap-8-sept-2021-prensa22980-1-768x1024.jpg",
                2000,
                localidadesDisponibles = 1800

            )
        )

        events.add(
            Event(
                7L,
                "2025-18-07",
                "20:00",
                "Teatro Vorterix",
                "Skrillex",
                "https://myhotposters.com/cdn/shop/products/mR0034.jpeg?v=1748542540",
                1500,
                localidadesDisponibles = 1400

            )
        )
    }

    fun findById(id: Long): Event? =events.find{
        it.id == id
    }
    fun getAll(): List<Event> =events
}