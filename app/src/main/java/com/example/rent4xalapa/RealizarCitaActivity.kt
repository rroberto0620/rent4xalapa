package com.example.rent4xalapa

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.RealizarCitaBinding

class RealizarCitaActivity :AppCompatActivity() {

    private lateinit var binding : RealizarCitaBinding
    private var idUsuario= 0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RealizarCitaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        nombre = intent.getStringExtra("nombre")!!
        telefono = intent.getLongExtra("telefono",0)

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.etArrendador.setText(nombre)
        binding.etContacto.setText(telefono.toString())

        binding.btnAceptar.setOnClickListener {
            Toast.makeText(this@RealizarCitaActivity, "Se realizo la cita ", Toast.LENGTH_LONG).show()
            finish()
        }

    }
}