package com.example.rent4xalapa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rent4xalapa.databinding.PublicacionesFavoritasBinding

class PublicacionesFavoritasActivity : AppCompatActivity() {

    private lateinit var binding : PublicacionesFavoritasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PublicacionesFavoritasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageButtonPrincipal.setOnClickListener {
            irPantallaPublicaciones()
        }

        binding.imageButtonPublicacionNueva.setOnClickListener {
            irPantallaRealizarPublicacion()
        }

        binding.imageButtonMiCuenta.setOnClickListener {
            irPantallaMiCuenta()
        }

    }

    fun irPantallaRevisarPerfil(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String){
        val intent = Intent(this@PublicacionesFavoritasActivity, RevisarPerfilActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        startActivity(intent)
        finish()
    }

    fun irPantallaPublicaciones(){
        val intent = Intent(this@PublicacionesFavoritasActivity, PrincipalPublicacionesActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun irPantallaMiCuenta(){
        val intent = Intent(this@PublicacionesFavoritasActivity,RevisarPerfilActivity::class.java)
        startActivity(intent)
    }

    fun irPantallaRealizarPublicacion(){
        val intent = Intent(this@PublicacionesFavoritasActivity,RealizarPublicacionesActivity::class.java)
        startActivity(intent)
    }
}