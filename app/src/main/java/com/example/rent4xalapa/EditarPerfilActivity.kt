package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import com.example.rent4xalapa.databinding.EditarPerfilBinding
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Usuario

private lateinit var binding: EditarPerfilBinding
private var idUsuario=0
private var nombre = ""
private var correo = ""
private var contrasena = ""
private var telefono:Long = 0
private lateinit var modelo : Usuarios
private lateinit var array: ArrayList<Usuario>

class EditarPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo= Usuarios(this@EditarPerfilActivity)
        array = arrayListOf<Usuario>()
        array = modelo.seleccionarUsuarios()
        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)

        binding.etNombre.setText(nombre)
        binding.etCorreo.setText(correo)
        binding.etContrasena.setText(contrasena)
        binding.etTelefono.setText(telefono.toString())

        binding.btnBack.setOnClickListener {
            irPantallaLogin()
        }
        binding.btnActualizar.setOnClickListener {
            val usuario = Usuario(idUsuario.toInt(), binding.etNombre.text.toString(), binding.etCorreo.text.toString(), binding.etContrasena.text.toString(), binding.etTelefono.text.toString().toLong(),"")
            actualizarUsuario(usuario)
        }


    }

    fun actualizarUsuario(usuario: Usuario){
        val resultadoActualizado = modelo.actualizarUsuario(usuario)
        var msg = ""
        if(resultadoActualizado>0){
            msg ="Usuario actualizado"
            irPantallaLogin()
        }else{
            msg = "Error al actualizar usuario"
        }

        mostrarToast(msg)
    }

    fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun irPantallaLogin(){
        val intent = Intent(this@EditarPerfilActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}