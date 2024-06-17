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
private var ine = ""
private var perfil= ""

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
        ine = intent.getStringExtra("ine")!!
        perfil = intent.getStringExtra("perfil")!!

        binding.etNombre.setText(nombre)
        binding.etCorreo.setText(correo)
        binding.etContrasena.setText(contrasena)
        binding.etTelefono.setText(telefono.toString())
        binding.etIdentificacion.setText(ine)
        binding.etPerfil.setText(perfil)

        binding.btnBack.setOnClickListener {
            irVerPerfil()
        }
        binding.btnActualizar.setOnClickListener {
            val usuario = Usuario(idUsuario.toInt(), binding.etNombre.text.toString(), binding.etCorreo.text.toString(), binding.etContrasena.text.toString(), binding.etTelefono.text.toString().toLong(),binding.etIdentificacion.text.toString(),binding.etPerfil.text.toString())
            actualizarUsuario(usuario)
        }


    }

    fun actualizarUsuario(usuario: Usuario){
        val resultadoActualizado = modelo.actualizarUsuario(usuario)
        var msg = ""
        if(resultadoActualizado>0){
            msg ="Usuario actualizado"
        }else{
            msg = "Error al actualizar usuario"
        }

        mostrarToast(msg)
    }

    fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun irVerPerfil(){
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
        val intent = Intent(this@EditarPerfilActivity, RevisarPerfilActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)
        startActivity(intent)
        finish()
    }

}