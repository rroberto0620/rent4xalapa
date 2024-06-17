package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.rent4xalapa.databinding.RevisarPerfilBinding
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Usuario

class RevisarPerfilActivity : AppCompatActivity() {

    private lateinit var binding: RevisarPerfilBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnEditProfile.setOnClickListener {
            irPantallaEditarPerfil()
        }
    }

    fun irPantallaEditarPerfil(){
        val intent = Intent(this@RevisarPerfilActivity, EditarPerfilActivity::class.java)
        startActivity(intent)
        finish()
    }
}