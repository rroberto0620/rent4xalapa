package com.example.rent4xalapa.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rent4xalapa.R
import com.example.rent4xalapa.interfaces.ListenerRecyclerMisPublicaciones
import com.example.rent4xalapa.poko.Publicacion

class MisPublicacionesAdapter(val publicaciones: List<Publicacion>, val listenerRecyclerMisPublicaciones: ListenerRecyclerMisPublicaciones) : RecyclerView.Adapter<MisPublicacionesAdapter.ViewHolderMiPublicacion>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMiPublicacion {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_mis_publicaciones, parent, false)
        return ViewHolderMiPublicacion(view)
    }


    override fun onBindViewHolder(holder: ViewHolderMiPublicacion, position: Int) {
        val publicacion = publicaciones.get(position)
        holder.tv2TituloPublicacion.text = publicacion.titulo
        holder.tv2DescripcionPublicacion.text = publicacion.direccion

        Glide.with(holder.itemView.context).load(publicacion.imagenes).override(200, 100).centerCrop() .into(holder.imagen)

        holder.btnVerPublicacion.setOnClickListener {
            listenerRecyclerMisPublicaciones.clicVerPublicacion(publicacion,position)
        }

        holder.btnEliminarPublicacion.setOnClickListener {
            listenerRecyclerMisPublicaciones.clicEliminarPublicacion(publicacion,position)
        }
    }

    override fun getItemCount(): Int = publicaciones.size

    class ViewHolderMiPublicacion(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnVerPublicacion: Button = itemView.findViewById(R.id.btn_ver_publicacion)
        val btnEliminarPublicacion: Button = itemView.findViewById(R.id.btn_eliminar)
        val tv2TituloPublicacion: TextView = itemView.findViewById(R.id.tv_titulo_publicacion)
        val tv2DescripcionPublicacion: TextView = itemView.findViewById(R.id.tv2_direccion)
        val imagen: ImageView = itemView.findViewById(R.id.imagen)

    }
}