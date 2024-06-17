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