package com.example.rent4xalapa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rent4xalapa.Adaptadores.PublicacionesAdapter
import com.example.rent4xalapa.databinding.PublicacionesFavoritasBinding
import com.example.rent4xalapa.interfaces.ListenerRecyclerPublicaciones
import com.example.rent4xalapa.modelo.FavoritosBD
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Usuario

class PublicacionesFavoritasActivity : AppCompatActivity(), ListenerRecyclerPublicaciones {
    private var idUsuario=0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    private lateinit var binding : PublicacionesFavoritasBinding
    private lateinit var array: ArrayList<Usuario>
    private lateinit var modelo : Usuarios
    private lateinit var modeloPublicaciones: PublicacionesBD
    private lateinit var modeloFavoritos: FavoritosBD
    private lateinit var db : FavoritosBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PublicacionesFavoritasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        modelo= Usuarios(this@PublicacionesFavoritasActivity)
        modeloPublicaciones = PublicacionesBD(this@PublicacionesFavoritasActivity)
        modeloFavoritos = FavoritosBD(this@PublicacionesFavoritasActivity)

        array = arrayListOf<Usuario>()
        array = modelo.seleccionarUsuarios()


        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)
        ine = intent.getStringExtra("ine")!!
        perfil = intent.getStringExtra("perfil")!!

        binding.imageButtonPrincipal.setOnClickListener {
            irActivityPublicaciones()
        }

        binding.imageButtonPublicacionNueva.setOnClickListener {
            irActivityRealizarPublicacion()
        }

        binding.imageButtonMiCuenta.setOnClickListener {
            irActivityRevisarPerfil()
        }
        configurarRecyclePublicaciones()
    }

    override fun onResume(){
        super.onResume()
        cargarMisPublicaciones()
    }

    fun cargarMisPublicaciones(){
        val publicaciones = modeloPublicaciones.seleccionarPublicaciones()
        if(publicaciones.size > 0){
            binding.recyclerPublicaciones.visibility = View.VISIBLE
            binding.recyclerPublicaciones.adapter = PublicacionesAdapter(publicaciones,this)
        }else{
            Toast.makeText(this@PublicacionesFavoritasActivity, "No se pueden mostrar las publicaciones = "+ idUsuario, Toast.LENGTH_LONG).show()
        }
    }

    private fun configurarRecyclePublicaciones() {
        binding.recyclerPublicaciones.layoutManager = LinearLayoutManager(this@PublicacionesFavoritasActivity)
        binding.recyclerPublicaciones.setHasFixedSize(true)
    }

    fun irActivityRevisarPerfil(){
        for (usuario in array) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaRevisarPerfil(
                    usuario.idUsuario,
                    usuario.nombre,
                    usuario.correo,
                    usuario.contrasena,
                    usuario.telefono,
                    usuario.ine,
                    usuario.perfil
                )
                break  // Salir del bucle una vez encontrado el usuario
            }
        }
    }

    fun irPantallaRevisarPerfil(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@PublicacionesFavoritasActivity, RevisarPerfilActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)
        startActivity(intent)
        finish()
    }

    fun irActivityPublicaciones(){
        for (usuario in array) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaPublicaciones(
                    usuario.idUsuario,
                    usuario.nombre,
                    usuario.correo,
                    usuario.contrasena,
                    usuario.telefono,
                    usuario.ine,
                    usuario.perfil
                )
                break  // Salir del bucle una vez encontrado el usuario
            }
        }
    }

    fun irPantallaPublicaciones(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@PublicacionesFavoritasActivity, PrincipalPublicacionesActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)
        startActivity(intent)
        finish()
    }

    fun irActivityRealizarPublicacion(){
        for (usuario in array) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaRealizarPublicacion(
                    usuario.idUsuario,
                    usuario.nombre,
                    usuario.correo,
                    usuario.contrasena,
                    usuario.telefono,
                    usuario.ine,
                    usuario.perfil
                )
                break  // Salir del bucle una vez encontrado el usuario
            }
        }
    }

    fun irPantallaRealizarPublicacion(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@PublicacionesFavoritasActivity,RealizarPublicacionesActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)
        startActivity(intent)
    }

    override fun clicFavoritoPublicacion(publicacion: Publicacion, posicion: Int) {
        Toast.makeText(this@PublicacionesFavoritasActivity, "Eliminando de favoritos...", Toast.LENGTH_LONG).show()
    }

    override fun clicVerPublicacion(publicacion: Publicacion, posicion: Int) {
        val intent = Intent(this@PublicacionesFavoritasActivity,RevisarPublicacionesActivity::class.java)

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
}