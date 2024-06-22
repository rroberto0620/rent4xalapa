package com.example.rent4xalapa

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.ActivityMainBinding
import com.example.rent4xalapa.modelo.FavoritosBD
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Usuario

private lateinit var binding: ActivityMainBinding
private lateinit var db : Usuarios
private lateinit var array: ArrayList<Usuario>
private lateinit var publicacionesDB: PublicacionesBD
private lateinit var favoritosDB: FavoritosBD
private lateinit var usuariosDB:Usuarios

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db= Usuarios(this@LoginActivity)
        publicacionesDB = PublicacionesBD(this@LoginActivity)
        publicacionesDB.crearTabla()
//        favoritosDB = FavoritosBD(this@LoginActivity)
//        favoritosDB.eliminarTabla()
//        usuariosDB = Usuarios(this@LoginActivity)
//        usuariosDB.eliminarTabla()
      db.crearTabla()
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

    fun verificarCredenciales() {
        val correo = binding.etEmail.text.toString()
        val contrasena = binding.etContrasena.text.toString()

        if (correo.isEmpty()) {
            binding.etEmail.setError("Campo requerido")
            return
        }

        if (contrasena.isEmpty()) {
            binding.etContrasena.setError("Campo requerido")
            return
        }

        // Validar el formato del correo
        if (!esCorreoValido(correo)) {
            binding.etEmail.setError("Formato de correo inválido")
            return
        }

        var usuarioEncontrado = false

        for (usuario in array) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaPrincipalPublicaciones(
                    usuario.idUsuario,
                    usuario.nombre,
                    usuario.correo,
                    usuario.contrasena,
                    usuario.telefono,
                    usuario.ine,
                    usuario.perfil
                )
                usuarioEncontrado = true
                break  // Salir del bucle una vez encontrado el usuario
            }
        }

        if (!usuarioEncontrado) {
            mostrarToast("Usuario y/o contraseña son incorrectos")
        }
    }

    fun esCorreoValido(correo:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }


    fun irPantallaPrincipalPublicaciones(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@LoginActivity,PrincipalPublicacionesActivity::class.java)
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

    fun irPantallaRegister(){
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

}