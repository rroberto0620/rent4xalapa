package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rent4xalapa.Adaptadores.PublicacionesAdapter
import com.example.rent4xalapa.databinding.PrincipalPublicacionesBinding
import com.example.rent4xalapa.interfaces.ListenerRecyclerPublicaciones
import com.example.rent4xalapa.modelo.FavoritosBD
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Usuario

class PrincipalPublicacionesActivity: AppCompatActivity(), ListenerRecyclerPublicaciones {
    private lateinit var binding : PrincipalPublicacionesBinding

    private var idUsuario= 0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    private lateinit var modelo : Usuarios
    private lateinit var array: ArrayList<Usuario>
    private lateinit var modeloPublicaciones: PublicacionesBD
    private lateinit var modeloFavoritos: FavoritosBD
    private lateinit var db : FavoritosBD
    private lateinit var publicacionesAdapter: PublicacionesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrincipalPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        modelo= Usuarios(this@PrincipalPublicacionesActivity)
        modeloPublicaciones = PublicacionesBD(this@PrincipalPublicacionesActivity)
        modeloFavoritos = FavoritosBD(this@PrincipalPublicacionesActivity)

        array = arrayListOf<Usuario>()
        array = modelo.seleccionarUsuarios()

        db= FavoritosBD(this@PrincipalPublicacionesActivity)
        modeloFavoritos= FavoritosBD(this@PrincipalPublicacionesActivity)
        modeloFavoritos.crearTabla()
        db.crearTabla()

        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)
        ine = intent.getStringExtra("ine")!!
        perfil = intent.getStringExtra("perfil")!!

        binding.imageButtonFavoritos.setOnClickListener {
            irActivityPublicacionesFavoritas()
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
        val publicaciones = modeloPublicaciones.seleccionarPublicaciones(idUsuario)
        if(publicaciones.size > 0){
            binding.recyclerPublicaciones.visibility = View.VISIBLE
            binding.recyclerPublicaciones.adapter = PublicacionesAdapter(publicaciones,this)
        }else{
            Toast.makeText(this@PrincipalPublicacionesActivity, "No hay publicaciones disponibles para mostrar", Toast.LENGTH_LONG).show()
        }
    }

    private fun configurarRecyclePublicaciones() {
        binding.recyclerPublicaciones.layoutManager = LinearLayoutManager(this@PrincipalPublicacionesActivity)
        binding.recyclerPublicaciones.setHasFixedSize(true)
    }

    fun irActivityPublicacionesFavoritas(){
        for (usuario in array) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaPublicacionesFavoritas(
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

    fun irPantallaPublicacionesFavoritas(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@PrincipalPublicacionesActivity,PublicacionesFavoritasActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)
        startActivity(intent)
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
        val intent = Intent(this@PrincipalPublicacionesActivity,RealizarPublicacionesActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)
        startActivity(intent)
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
        val intent = Intent(this@PrincipalPublicacionesActivity,RevisarPerfilActivity::class.java)
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
        val idUsuarioExistente = publicacion.idPublicacion
        if(modeloFavoritos.favoritoExiste(idUsuario,idUsuarioExistente)){
            Toast.makeText(this, "La publicacion ya está en favoritos", Toast.LENGTH_LONG).show()
        }else {
            modeloFavoritos.agregarFavorito(publicacion,idUsuario)
            Toast.makeText(this@PrincipalPublicacionesActivity, "Se agrego ${publicacion.titulo} a favoritos ", Toast.LENGTH_LONG).show()
        }
    }

    override fun clicVerPublicacion(publicacion: Publicacion, posicion: Int) {
        val intent = Intent(this@PrincipalPublicacionesActivity,RevisarPublicacionesActivity::class.java)

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