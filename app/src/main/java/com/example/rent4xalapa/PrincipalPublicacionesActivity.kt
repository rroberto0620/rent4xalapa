package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.PrincipalPublicacionesBinding
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Usuario

class PrincipalPublicacionesActivity: AppCompatActivity() {
    private lateinit var binding : PrincipalPublicacionesBinding

    private var idUsuario=0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0

    private lateinit var modelo : Usuarios
    private lateinit var array: ArrayList<Usuario>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrincipalPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        modelo= Usuarios(this@PrincipalPublicacionesActivity)

        array = arrayListOf<Usuario>()
        array = modelo.seleccionarUsuarios()

        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)

        binding.imageButtonFavoritos.setOnClickListener {
            irPantallaPublicacionesFavoritas()
        }

        binding.imageButtonPublicacionNueva.setOnClickListener {
            irPantallaRealizarPublicacion()
        }

        binding.imageButtonMiCuenta.setOnClickListener {
            for (usuario in array) {
                if (usuario.correo == correo && usuario.contrasena == contrasena) {
                    irPantallaMiCuenta(
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

    }



    fun irPantallaPublicacionesFavoritas(){
        val intent = Intent(this@PrincipalPublicacionesActivity,PublicacionesFavoritasActivity::class.java)
        startActivity(intent)
    }

    fun irPantallaRealizarPublicacion(){
        val intent = Intent(this@PrincipalPublicacionesActivity,RealizarPublicacionesActivity::class.java)
        startActivity(intent)
    }

    fun irPantallaMiCuenta(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String){
        val intent = Intent(this@PrincipalPublicacionesActivity,RevisarPerfilActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        startActivity(intent)
    }
}