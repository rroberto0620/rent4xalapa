package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.example.rent4xalapa.databinding.ActivityRegisterBinding
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Usuario

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var modelo : Usuarios



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo = Usuarios(this)

        binding.btnRegistrarse.setOnClickListener {
            if (validarCamposCorrectos()) {
                val correo = binding.etEmail.text.toString()
                if (modelo.correoExiste(correo)) {
                    Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_LONG).show()
                } else {
                    val nuevoUsuario = Usuario(
                        0,
                        binding.etNombre.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etContrasena.text.toString(),
                        binding.etNumeroTel.text.toString().toLong(),
                        binding.etIdentifiacion.text.toString(),
                        binding.etPerfil.text.toString()
                    )
                    agregarUsuario(nuevoUsuario)
                }
            }
        }

        binding.tvTieneCuenta.setOnClickListener {
            irPantallaLogin()
        }


    }


    fun agregarUsuario(usuario: Usuario){
        val resultadoInsercion = modelo.insertarUsuarios(usuario)
        var mensaje = ""
        if (resultadoInsercion>0){
            mensaje = "Usuario registrado correctamente"
            binding.etNombre.setText("")
            binding.etEmail.setText("")
            binding.etContrasena.setText("")
            binding.etNumeroTel.setText("")
            binding.etIdentifiacion.setText("")
            binding.etPerfil.setText("")
        }else{
            mensaje = "Hubo un error al guardar el usuario, intentelo más tarde"

        }
        Toast.makeText(this@RegisterActivity, mensaje,Toast.LENGTH_LONG).show()
    }


    fun validarCamposCorrectos():Boolean{
        var valido = true
        if (binding.etNombre.text.isEmpty()){
            binding.etNombre.setError("El nombre es obligatorio")
            valido = false
        }
        if (binding.etEmail.text.isEmpty()) {
            binding.etEmail.setError("El email es obligatorio")
            valido = false
        } else if (!esCorreoValido(binding.etEmail.text.toString())) {
            binding.etEmail.setError("Formato de email inválido")
            valido = false
        }
        if (binding.etContrasena.text.isEmpty()){
            binding.etContrasena.setError("La contraseña es obligatoria")
            valido = false
        }
        if (binding.etNumeroTel.text.isEmpty()) {
            binding.etNumeroTel.setError("El número telefónico es obligatorio")
            valido = false
        } else if (!esTelefonoValido(binding.etNumeroTel.text.toString())) {
            binding.etNumeroTel.error = "Formato de teléfono inválido"
            valido = false
        }

        if (binding.etIdentifiacion.text.isEmpty()){
            binding.etIdentifiacion.setError("Tu identifiacion es requerida")
            valido = false
        }else if (!esUrlValida(binding.etIdentifiacion.text.toString())) {
            binding.etIdentifiacion.error = "Formato de URL inválido"
            valido = false
        }
        if (binding.etPerfil.text.isNotEmpty() && !esUrlValida(binding.etPerfil.text.toString())) {
            binding.etPerfil.setError("Formato de URL inválido")
            valido = false
        }

        return valido
    }

    fun esUrlValida(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }
    fun esTelefonoValido(tel : String): Boolean{
        return Patterns.PHONE.matcher(tel).matches()
    }

    fun esCorreoValido(correo:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    fun irPantallaLogin(){
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}