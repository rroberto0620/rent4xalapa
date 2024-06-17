package com.example.rent4xalapa.interfaces

import com.example.rent4xalapa.poko.Publicacion

interface ListenerRecyclerPublicaciones {

    fun clicFavoritoPublicacion(publicacion : Publicacion , posicion : Int)

    fun clicVerPublicacion(publicacion : Publicacion, posicion: Int)
}