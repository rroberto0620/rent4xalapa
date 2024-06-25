package com.example.rent4xalapa.interfaces

import com.example.rent4xalapa.poko.Publicacion

interface ListenerRecyclerMisPublicaciones {
    fun clicVerPublicacion(publicacion : Publicacion, posicion: Int)
}