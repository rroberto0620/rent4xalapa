package com.example.rent4xalapa.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rent4xalapa.R
import com.example.rent4xalapa.interfaces.ListenerRecyclerPublicaciones
import com.example.rent4xalapa.poko.Publicacion

class PublicacionesAdapter(val publicaciones: List<Publicacion>, val listenerRecyclerPublicaciones: ListenerRecyclerPublicaciones) : RecyclerView.Adapter<PublicacionesAdapter.ViewHolderPublicacion>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPublicacion {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_publicaciones, parent, false)
        return ViewHolderPublicacion(view)
    }



    override fun onBindViewHolder(holder: ViewHolderPublicacion, position: Int) {
        val publicacion = publicaciones.get(position)
        val idPublicacion = publicacion.idPublicacion
        val tituloValor = publicacion.titulo
        val descripcionValor = publicacion.descripcion
        val direccion = publicacion.direccion
        val tipo = publicacion.tipo
        val numHabitaciones = publicacion.numHabitaciones
        val costo = publicacion.costo
        val petFriendly = publicacion.petFriendly
        val servicios = publicacion.servicios
        val amueblado = publicacion.amueblado
        val entradaCompartida = publicacion.entradaCompartida
        val cochera = publicacion.cochera
        val aire = publicacion.aire
        val imagenes = publicacion.imagenes
        val latitud = publicacion.latitud
        val longitud = publicacion.longitud
        val calificacion = publicacion.calificacion
        val idUser = publicacion.idUsuario

        holder.tv2TituloPublicacion.text = publicacion.titulo
        holder.tv2DescripcionPublicacion.text = publicacion.descripcion


        /*holder.imageButtonFavoritoPublicacion.setOnClickListener {
            listenerRecyclerPublicaciones.clicFavoritoPublicacion(publicacion,position)
        }*/

        holder.btnVerPublicacion.setOnClickListener {
            listenerRecyclerPublicaciones.clicVerPublicacion(publicacion,position)
        }
    }

    override fun getItemCount(): Int = publicaciones.size

    class ViewHolderPublicacion(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val imageButtonFavoritoPublicacion: ImageButton = itemView.findViewById(R.id.imageButton_favoritos)
        val btnVerPublicacion: Button = itemView.findViewById(R.id.btn_ver_publicacion)
        val tv2TituloPublicacion: TextView = itemView.findViewById(R.id.tv2_titulo_publicacion)
        val tv2DescripcionPublicacion: TextView = itemView.findViewById(R.id.tv2_descripcion_publicacion)

    }
}