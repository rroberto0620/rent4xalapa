package com.example.rent4xalapa.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rent4xalapa.R
import com.example.rent4xalapa.interfaces.ListenerRecyclerReservaciones
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Reservacion
import com.example.rent4xalapa.poko.Usuario

class ReservacionesAdapter(val reservaciones: List<Reservacion>, val publicaciones: List<Publicacion>, val usuarios: List<Usuario>, val listenerRecyclerReservaciones: ListenerRecyclerReservaciones) : RecyclerView.Adapter<ReservacionesAdapter.ViewHolderReservacion>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderReservacion {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_reservaciones, parent, false)
        return ViewHolderReservacion(view)
    }



    override fun onBindViewHolder(holder: ViewHolderReservacion, position: Int) {
        val reservacion = reservaciones.get(position)
        val publicacion = publicaciones.get(position)
        val usuario = usuarios.get(position)

        holder.tv2Fecha.text = reservacion.fecha
        holder.tv2Hora.text = reservacion.hora
        holder.tv2Titulo.text = publicacion.titulo
        holder.tv2Direccion.text = publicacion.direccion
        holder.tv2Nombre.text = usuario.nombre
        holder.tv2Contacto.text = usuario.telefono.toString()

        holder.btnEliminar.setOnClickListener {
            listenerRecyclerReservaciones.clicEliminarReservacion(reservacion,position)
        }

        holder.tv2Direccion.setOnClickListener {
            listenerRecyclerReservaciones.clicDireccion(publicacion,position)
        }
    }

    override fun getItemCount(): Int = reservaciones.size

    class ViewHolderReservacion(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnEliminar: Button = itemView.findViewById(R.id.btn_eliminar)
        val tv2Fecha: TextView = itemView.findViewById(R.id.tv2_fecha)
        val tv2Hora: TextView = itemView.findViewById(R.id.tv2_hora)
        val tv2Titulo: TextView = itemView.findViewById(R.id.tv2_titulo)
        val tv2Nombre: TextView = itemView.findViewById(R.id.tv2_nombre)
        val tv2Contacto: TextView = itemView.findViewById(R.id.tv2_contacto)
        val tv2Direccion: TextView = itemView.findViewById(R.id.tv2_direccion)

    }
}