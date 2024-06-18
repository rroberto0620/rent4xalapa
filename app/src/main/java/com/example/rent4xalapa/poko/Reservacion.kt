package com.example.rent4xalapa.poko

data class Reservacion(
    val idReservacion: Int,
    val fecha: String,
    val hora: String,
    val idUsuario: Int,
    val idPublicacion: Int
)
