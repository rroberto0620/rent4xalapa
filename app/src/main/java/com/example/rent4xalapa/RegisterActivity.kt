package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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

        binding.btnRegistrarse.setOnClickListener{
            if(validarCamposCorrectos()){
                val nuevoUsuario = Usuario(0,binding.etNombre.text.toString(),binding.etEmail.text.toString(),binding.etContrasena.text.toString(),binding.etNumeroTel.text.toString().toLong(),binding.etIdentifiacion.text.toString())
                agregarUsuario(nuevoUsuario)
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
        }else{
            mensaje = "Hubo un error al guardar la nota, intentelo más tarde"

        }
        Toast.makeText(this@RegisterActivity, mensaje,Toast.LENGTH_LONG).show()
    }


    fun validarCamposCorrectos():Boolean{
        var valido = true
        if (binding.etNombre.text.isEmpty()){
            binding.etNombre.setError("El nombre es obligatorio")
            valido = false
        }
        if (binding.etEmail.text.isEmpty()){
            binding.etEmail.setError("El email es obligatorio")
            valido = false
        }
        if (binding.etContrasena.text.isEmpty()){
            binding.etContrasena.setError("La contraseña es obligatoria")
            valido = false
        }
        if (binding.etNumeroTel.text.isEmpty()){
            binding.etNumeroTel.setError("El numero telefonico es obligatorio")
            valido = false
        }
        if (binding.etIdentifiacion.text.isEmpty()){
            binding.etIdentifiacion.setError("Tu identifiacion es requerida")
            valido = false
        }

        return valido
    }

    fun irPantallaLogin(){
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}