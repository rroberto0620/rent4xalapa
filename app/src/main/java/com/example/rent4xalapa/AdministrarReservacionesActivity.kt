package com.example.rent4xalapa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rent4xalapa.Adaptadores.PublicacionesAdapter
import com.example.rent4xalapa.Adaptadores.ReservacionesAdapter
import com.example.rent4xalapa.databinding.AdministrarReservacionesBinding
import com.example.rent4xalapa.databinding.RealizarCitaBinding
import com.example.rent4xalapa.interfaces.ListenerRecyclerPublicaciones
import com.example.rent4xalapa.interfaces.ListenerRecyclerReservaciones
import com.example.rent4xalapa.modelo.ReservacionBD
import com.example.rent4xalapa.poko.Reservacion


class AdministrarReservacionesActivity : AppCompatActivity(), ListenerRecyclerReservaciones {

    private lateinit var binding : AdministrarReservacionesBinding
    private lateinit var modeloReservacion : ReservacionBD

    private var idUsuario=0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdministrarReservacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        modeloReservacion = ReservacionBD(this@AdministrarReservacionesActivity)

        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)
        ine = intent.getStringExtra("ine")!!
        perfil = intent.getStringExtra("perfil")!!


        binding.btnRegresar.setOnClickListener {
            finish()
        }

        configurarRecyclePublicaciones()
    }

    override fun onResume(){
        super.onResume()
        cargarMisCitas()
    }

    fun cargarMisCitas(){
        Log.d("msjCargarPublicaciones",idUsuario.toString())
        val citas = modeloReservacion.seleccionarReservacion(idUsuario.toString())
        if(citas.size > 0){
            binding.recyclerReservaciones.visibility = View.VISIBLE
            binding.recyclerReservaciones.adapter = ReservacionesAdapter(citas,this)
        }else{
            Toast.makeText(this@AdministrarReservacionesActivity, "No se pueden mostrar las citas = ${citas.size}  "+ idUsuario, Toast.LENGTH_LONG).show()
        }
    }

    private fun configurarRecyclePublicaciones() {
        binding.recyclerReservaciones.layoutManager = LinearLayoutManager(this@AdministrarReservacionesActivity)
        binding.recyclerReservaciones.setHasFixedSize(true)
    }

    override fun clicEliminarReservacion(reservacion: Reservacion, posicion: Int) {
        TODO("Not yet implemented")
    }
}