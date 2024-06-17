package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.PrincipalPublicacionesBinding

class PrincipalPublicacionesActivity: AppCompatActivity() {
    private lateinit var binding : PrincipalPublicacionesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrincipalPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageButtonFavoritos.setOnClickListener {
            irPantallaPublicacionesFavoritas()
        }

        binding.imageButtonPublicacionNueva.setOnClickListener {
            irPantallaRealizarPublicacion()
        }

        binding.imageButtonMiCuenta.setOnClickListener {
            irPantallaMiCuenta()
        }

    }


    fun irPantallaPublicacionesFavoritas(){
        val intent = Intent(this@PrincipalPublicacionesActivity,PublicacionesFavoritasActivity::class.java)
        startActivity(intent)
    }

    fun irPantallaRealizarPublicacion(){
        val intent = Intent(this@PrincipalPublicacionesActivity,RealizarPublicacionesActivity::class.java)
        startActivity(intent)
    }

    fun irPantallaMiCuenta(){
        val intent = Intent(this@PrincipalPublicacionesActivity,RevisarPerfilActivity::class.java)
        startActivity(intent)
    }
}