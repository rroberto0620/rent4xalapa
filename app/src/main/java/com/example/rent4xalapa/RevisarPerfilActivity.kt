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


    private var idUsuario=0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0


    private lateinit var modelo : Usuarios
    private lateinit var array: ArrayList<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RevisarPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo= Usuarios(this@RevisarPerfilActivity)

        array = arrayListOf<Usuario>()
        array = modelo.seleccionarUsuarios()

        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)



        binding.tvName.setText("Nombre: "+"$nombre")
        binding.tvTelefono.setText("Telefono: "+"$telefono")
        binding.tvEmail.setText("Correo: "+"$correo")

        binding.btnEditProfile.setOnClickListener {
            irActivityEditar()
        }
        binding.btnBack.setOnClickListener {
            irPantallaLogin()
        }
    }

    fun irActivityEditar(){
        for (usuario in array) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaEditarPerfil(
                    usuario.idUsuario,
                    usuario.nombre,
                    usuario.correo,
                    usuario.contrasena,
                    usuario.telefono,
                    usuario.ine
                )


                break  // Salir del bucle una vez encontrado el usuario
            }
        }
    }
    fun irPantallaEditarPerfil(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String){
        val intent = Intent(this@RevisarPerfilActivity, EditarPerfilActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        startActivity(intent)
        finish()
    }

    fun irPantallaLogin(){
        val intent = Intent(this@RevisarPerfilActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}