package com.example.rent4xalapa.interfaces
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Reservacion
interface ListenerRecyclerReservaciones {

    fun clicEliminarReservacion(reservacion: Reservacion, posicion : Int)

    fun clicDireccion(publicacion: Publicacion, posicion : Int)
}