package com.example.rent4xalapa.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        holder.tv2TituloPublicacion.text = publicacion.titulo
        holder.tv2DescripcionPublicacion.text = publicacion.direccion

        Glide.with(holder.itemView.context).load(publicacion.imagenes).override(200, 100).centerCrop() .into(holder.imagen)

        holder.btnAgregarFavorito.setOnClickListener {
            listenerRecyclerPublicaciones.clicFavoritoPublicacion(publicacion,position)
        }

        holder.btnVerPublicacion.setOnClickListener {
            listenerRecyclerPublicaciones.clicVerPublicacion(publicacion,position)
        }
    }

    override fun getItemCount(): Int = publicaciones.size

    class ViewHolderPublicacion(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnAgregarFavorito: Button = itemView.findViewById(R.id.btn_favorito)
        val btnVerPublicacion: Button = itemView.findViewById(R.id.btn_ver_publicacion)
        val tv2TituloPublicacion: TextView = itemView.findViewById(R.id.tv_titulo_publicacion)
        val tv2DescripcionPublicacion: TextView = itemView.findViewById(R.id.tv2_direccion)
        val imagen: ImageView = itemView.findViewById(R.id.imagen)

    }
}