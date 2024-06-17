package com.example.rent4xalapa.poko

data class Publicacion(
    val idPublicacion: Int,
    val titulo: String,
    val descripcion:String,
    val direccion: String,
    val tipo: String,
    val numHabitaciones:Int,
    val costo:Double,
    val petFriendly: Int,
    val servicios: Int,
    val amueblado: Int,
    val entradaCompartida: Int,
    val cochera: Int,
    val aire: Int,
    val longitud: Double,
    val latitud:Double,
    val calificacion:Int,
    val idUsuario: Int
)
