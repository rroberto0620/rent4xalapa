package com.example.rent4xalapa.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rent4xalapa.R
import com.example.rent4xalapa.poko.Publicacion

class PublicacionesAdapter(private val publicaciones: List<Publicacion>) : RecyclerView.Adapter<PublicacionesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_publicaciones, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val publicacion = publicaciones[position]
        holder.bind(publicacion)
    }

    override fun getItemCount(): Int = publicaciones.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitulo: TextView = itemView.findViewById(R.id.tv_titulo_publicacion)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tv_descripcion_publicacion)
        private val tv2Titulo: TextView = itemView.findViewById(R.id.tv2_titulo_publicacion)
        private val tv2Descripcion: TextView = itemView.findViewById(R.id.tv2_descripcion_publicacion)
        private val btnVerPublicacion: Button = itemView.findViewById(R.id.tv_ver_publicacion)
        private val imgBtnFavorito: ImageButton = itemView.findViewById(R.id.imageButton_favorito_publicacion)

        fun bind(publicacion: Publicacion) {
            tvTitulo.text = "Título:"
            tv2Titulo.text = publicacion.titulo
            tvDescripcion.text = "Descripción:"
            tv2Descripcion.text = publicacion.descripcion

            // Aquí puedes manejar eventos de clic si es necesario
            btnVerPublicacion.setOnClickListener {
                // Manejar clic en "Ver publicación"
            }

            imgBtnFavorito.setOnClickListener {
                // Manejar clic en el botón de favorito
            }
        }
    }
}