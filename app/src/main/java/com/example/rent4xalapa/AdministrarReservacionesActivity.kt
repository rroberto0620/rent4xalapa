package com.example.rent4xalapa

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.modelo.ReservacionBD
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Reservacion
import com.example.rent4xalapa.poko.Usuario


class AdministrarReservacionesActivity : AppCompatActivity(), ListenerRecyclerReservaciones {

    private lateinit var binding : AdministrarReservacionesBinding
    private lateinit var modeloReservacion : ReservacionBD
    private lateinit var modeloPublicaciones : PublicacionesBD
    private lateinit var modeloUsuario : Usuarios

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
        modeloPublicaciones = PublicacionesBD(this@AdministrarReservacionesActivity)
        modeloUsuario = Usuarios(this@AdministrarReservacionesActivity)

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
        val reservaciones = modeloReservacion.obtenerPublicacionesReservacion(idUsuario.toString())
        val publicaciones = modeloPublicaciones.obtenerPublicacionesReservacion(reservaciones)
        Log.d("msjNum de reservaciones",publicaciones.size.toString())
        val citas = modeloReservacion.seleccionarReservacion(idUsuario.toString())
        val usuarios = modeloUsuario.seleccionarUsuariosReservacion(publicaciones)
        if(citas.size > 0){
            binding.recyclerReservaciones.visibility = View.VISIBLE
            binding.recyclerReservaciones.adapter = ReservacionesAdapter(citas,publicaciones,usuarios,this)
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

    override fun clicDireccion(publicacion: Publicacion, posicion: Int) {
        openGoogleMaps(publicacion.latitud,publicacion.longitud)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openGoogleMaps(latitude: Double, longitude: Double) {
        try {
            val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo abrir Google Maps, abriendo en el navegador", Toast.LENGTH_LONG).show()
            openGoogleMapsInBrowser(latitude, longitude)
        }
    }

    private fun openGoogleMapsInBrowser(latitude: Double, longitude: Double) {
        val mapsUrl = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
        startActivity(webIntent)
    }
}