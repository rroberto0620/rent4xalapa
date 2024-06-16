package com.example.rent4xalapa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rent4xalapa.databinding.EditarPerfilBinding

private lateinit var binding: EditarPerfilBinding
private var nombre = ""
private var correo = ""
private var contrasena = ""
private var telefono = 0


class EditarPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getIntExtra("telefono",0)

        binding.etNombre.setText(nombre)
        binding.etCorreo.setText(correo)
        binding.etContrasena.setText(contrasena)
        binding.etTelefono.setText(telefono)

    }




}