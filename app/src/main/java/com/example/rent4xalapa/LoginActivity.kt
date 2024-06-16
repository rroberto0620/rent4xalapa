package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.ActivityMainBinding
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Usuario

private lateinit var binding: ActivityMainBinding
private lateinit var db : Usuarios
private lateinit var array: ArrayList<Usuario>

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        db= Usuarios(this@LoginActivity)
        array = arrayListOf<Usuario>()
        array = db.seleccionarUsuarios()
        binding.tvRegistrar.setOnClickListener{
            irPantallaRegister()
        }
        binding.btnLogin.setOnClickListener {
            verificarCredenciales()
        }
    }




    fun mostrarToast(mensaje : String){
        Toast.makeText(this@LoginActivity,mensaje, Toast.LENGTH_LONG).show()
    }

    fun verificarCredenciales(){
        val correo = binding.etEmail.text.toString()
        val contrasena = binding.etContrasena.text.toString()
        for (usuario in array){
            if(usuario.correo == correo && usuario.contrasena == contrasena){
                mostrarToast("Iniciaste sesion "+"${usuario.nombre}")
                irPantallaEditarPerfil(usuario.nombre,usuario.correo,usuario.contrasena,usuario.telefono,usuario.ine)
            }else{
                mostrarToast("Usuario y/o contraseña son incorrectos")
            }
        }
    }


    fun irPantallaEditarPerfil(nombre:String, correo:String , contrasena:String , telefono:Int , ine:String){
        val intent = Intent(this@LoginActivity,EditarPerfilActivity::class.java)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        startActivity(intent)
        finish()
    }

    fun irPantallaRegister(){
        val intent = Intent(this@LoginActivity, registerActivity::class.java)
        startActivity(intent)
        finish()
    }
    
}