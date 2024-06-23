package com.example.rent4xalapa.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rent4xalapa.R
import com.example.rent4xalapa.interfaces.ListenerRecyclerReservaciones
import com.example.rent4xalapa.poko.Reservacion

class ReservacionesAdapter(val reservaciones: List<Reservacion>, val listenerRecyclerReservaciones: ListenerRecyclerReservaciones) : RecyclerView.Adapter<ReservacionesAdapter.ViewHolderReservacion>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderReservacion {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_reservaciones, parent, false)
        return ViewHolderReservacion(view)
    }



    override fun onBindViewHolder(holder: ViewHolderReservacion, position: Int) {
        val reservacion = reservaciones.get(position)

        holder.tv2Fecha.text = reservacion.fecha
        holder.tv2Hora.text = reservacion.hora


        holder.btnEliminar.setOnClickListener {
            listenerRecyclerReservaciones.clicEliminarReservacion(reservacion,position)
        }
    }

    override fun getItemCount(): Int = reservaciones.size

    class ViewHolderReservacion(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnEliminar: Button = itemView.findViewById(R.id.btn_eliminar)
        val tv2Fecha: TextView = itemView.findViewById(R.id.tv2_fecha)
        val tv2Hora: TextView = itemView.findViewById(R.id.tv2_hora)

    }
}