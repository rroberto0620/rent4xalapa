package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rent4xalapa.Adaptadores.MisPublicacionesAdapter
import com.example.rent4xalapa.Adaptadores.PublicacionesAdapter
import com.example.rent4xalapa.databinding.MisPublicacionesBinding
import com.example.rent4xalapa.databinding.PublicacionesFavoritasBinding
import com.example.rent4xalapa.interfaces.ListenerRecyclerMisPublicaciones
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Usuario

class MisPublicacionesActivity : AppCompatActivity(),ListenerRecyclerMisPublicaciones {
    private var idUsuario=0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    private lateinit var binding : MisPublicacionesBinding
    private lateinit var array: ArrayList<Usuario>
    private lateinit var modelo : Usuarios
    private lateinit var modeloPublicaciones: PublicacionesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MisPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        modelo= Usuarios(this@MisPublicacionesActivity)
        modeloPublicaciones = PublicacionesBD(this@MisPublicacionesActivity)

        array = arrayListOf<Usuario>()
        array = modelo.seleccionarUsuarios()

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
        cargarMisPublicaciones()
    }

    fun cargarMisPublicaciones(){
        Log.d("msjCargarPublicaciones",idUsuario.toString())
        val publicaciones = modeloPublicaciones.seleccionarMisPublicaciones(idUsuario)
        if(publicaciones.size > 0){
            binding.recyclerMisPublicaciones.visibility = View.VISIBLE
            binding.recyclerMisPublicaciones.adapter = MisPublicacionesAdapter(publicaciones,this)
        }else{
            Toast.makeText(this@MisPublicacionesActivity, "Aun no haz realizado ninguna publicacion", Toast.LENGTH_LONG).show()
        }
    }

    private fun configurarRecyclePublicaciones() {
        binding.recyclerMisPublicaciones.layoutManager = LinearLayoutManager(this@MisPublicacionesActivity)
        binding.recyclerMisPublicaciones.setHasFixedSize(true)
    }



    override fun clicVerPublicacion(publicacion: Publicacion, posicion: Int) {
        val intent = Intent(this@MisPublicacionesActivity,RevisarPublicacionesActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)


        intent.putExtra("idPublicacion",publicacion.idPublicacion)
        intent.putExtra("titulo",publicacion.titulo)
        intent.putExtra("descripcion",publicacion.descripcion)
        intent.putExtra("direccion",publicacion.direccion)
        intent.putExtra("tipo",publicacion.tipo)
        intent.putExtra("numHabitaciones",publicacion.numHabitaciones)
        intent.putExtra("costo",publicacion.costo)
        intent.putExtra("petFriendly",publicacion.petFriendly)
        intent.putExtra("servicios",publicacion.servicios)
        intent.putExtra("amueblado",publicacion.amueblado)
        intent.putExtra("entradaCompartida",publicacion.entradaCompartida)
        intent.putExtra("cochera",publicacion.cochera)
        intent.putExtra("aire",publicacion.aire)
        intent.putExtra("imagenes",publicacion.imagenes)
        intent.putExtra("latitud",publicacion.latitud)
        intent.putExtra("longitud",publicacion.longitud)
        intent.putExtra("calificacion",publicacion.calificacion)
        intent.putExtra("idUser",publicacion.idUsuario)
        startActivity(intent)
    }

    override fun clicEliminarPublicacion(publicacion: Publicacion, posicion: Int) {
        modeloPublicaciones.eliminarPublicacion(publicacion.idPublicacion)
        Toast.makeText(this@MisPublicacionesActivity, "Se elimina la publicacion", Toast.LENGTH_LONG).show()
        recreate()
    }
}