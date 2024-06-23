package com.example.rent4xalapa.interfaces
import com.example.rent4xalapa.poko.Reservacion
interface ListenerRecyclerReservaciones {

    fun clicEliminarReservacion(reservacion: Reservacion, posicion : Int)
}