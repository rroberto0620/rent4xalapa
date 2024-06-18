package com.example.rent4xalapa

import android.app.Dialog
import android.os.Bundle
import android.content.DialogInterface
import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rent4xalapa.databinding.RevisarPerfilBinding
import com.example.rent4xalapa.databinding.RevisarPublicacionesBinding
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Usuario
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RevisarPublicacionesActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: RevisarPublicacionesBinding
    private lateinit var map: GoogleMap

    private var idUsuario1=0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    private var idPublicacion = 0
    private var titulo = ""
    private var descripcion = ""
    private var direccion = ""
    private var tipo = ""
    private var numHabitaciones = 0
    private var costo = 0.0
    private var petFriendly = 0
    private var servicios = 0
    private var amueblado = 0
    private var entradaCompartida = 0
    private var cochera = 0
    private var aire = 0
    private var imagenes = ""
    private var longitud = 0.0
    private var latitud = 0.0
    private var calificacion = 0
    private var idUsuario = 0

    private lateinit var modeloUsuarios : Usuarios
    private lateinit var arrayUsu: ArrayList<Usuario>


    private lateinit var modelo : PublicacionesBD
    private lateinit var array: ArrayList<Publicacion>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RevisarPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo=PublicacionesBD(this@RevisarPublicacionesActivity)
        modeloUsuarios=Usuarios(this@RevisarPublicacionesActivity)
        arrayUsu = arrayListOf<Usuario>()
        arrayUsu = modeloUsuarios.seleccionarUsuarios()

        array = arrayListOf<Publicacion>()

        idUsuario1 = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)
        ine = intent.getStringExtra("ine")!!
        perfil = intent.getStringExtra("perfil")!!


        idPublicacion = intent.getIntExtra("idPublicacion", 0)
        titulo = intent.getStringExtra("titulo")!!
        descripcion = intent.getStringExtra("descripcion")!!
        direccion = intent.getStringExtra("direccion")!!
        tipo = intent.getStringExtra("tipo")!!
        numHabitaciones = intent.getIntExtra("numHabitaciones",0)
        costo = intent.getDoubleExtra("costo", 0.0)
        petFriendly = intent.getIntExtra("petFriendly", 0)
        servicios = intent.getIntExtra("servicios", 0)
        amueblado = intent.getIntExtra("amueblado", 0)
        entradaCompartida = intent.getIntExtra("entradaCompartida", 0)
        cochera = intent.getIntExtra("cochera", 0)
        aire = intent.getIntExtra("aire", 0)
        imagenes = intent.getStringExtra("imagenes")!!
        calificacion = intent.getIntExtra("calificacion", 0)
        idUsuario = intent.getIntExtra("idUsuario", 0)
        latitud = intent.getDoubleExtra("latitud",0.0)
        longitud = intent.getDoubleExtra("longitud",0.0)


        binding.tvTitulo.setText("Titulo: "+"$titulo")
        binding.tvDescripcion.setText("Descripcion: "+"$descripcion")
        binding.tvDireccion.setText("Direccion: "+"$direccion")
        binding.tvTipo.setText("Tipo: "+"$tipo")
        binding.tvNumHabitaciones.setText("Numero de habitaciones: "+"$numHabitaciones")
        binding.tvCosto.setText("Costo: "+"$costo")
        //Pet Friendly
        if (petFriendly == 1){
            binding.tvPetFriendly.setText("Pet Friendly: Si")
        }else{
            binding.tvPetFriendly.setText("Pet Friendly: No")
        }
        //Servicios
        if(servicios == 1){
            binding.tvServicios.setText("Servicios: Si")
        }else{
            binding.tvServicios.setText("Servicios: No")
        }
        //Amueblado
        if(amueblado == 1){
            binding.tvAmueblado.setText("Amueblado: Si")
        }else{
            binding.tvAmueblado.setText("Amueblado: No")
        }
        //Entrada Compartida
        if(entradaCompartida == 1){
            binding.tvEntradaCompartida.setText("Entrada Compartida: Si")
        }else{
            binding.tvEntradaCompartida.setText("Entrada Compartida: No")
        }
        //Cochera
        if(cochera == 1){
            binding.tvCochera.setText("Tiene cochera: Si")
        }else{
            binding.tvCochera.setText("Tiene cochera: No")
        }
        //Aire acondicionado
        if(aire == 1){
            binding.tvAire.setText("Aire acondicionado: Si")
        }else{
            binding.tvAire.setText("Aire acondicionado: No")
        }


        Glide.with(this).load(imagenes).into(binding.imagenPerfil)



        binding.btnCalificar.setOnClickListener {
            abrirDialogoCalificar()
        }

        binding.btnRealizar.setOnClickListener {
            irActivityRealizarCita()
        }
        binding.btnRegresar.setOnClickListener {
           irActivityPublicaciones()
        }
        createFragment()
    }

    private fun createFragment(){
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.frag_ubicacion2) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Agregar un marcador inicial en una ubicación predeterminada
        val initialLocation = LatLng(latitud,longitud) // Reemplaza con la ubicación deseada
        val initialMarker = map.addMarker(MarkerOptions().position(initialLocation).title("Marcador inicial"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))
    }

    private fun abrirDialogoCalificar() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layour_calificar)  // Asignar el layout del diálogo personalizado

        // Asignar acciones a los botones de estrellas
        val btn1Estrella = dialog.findViewById<Button>(R.id.btn1Estrella)
        val btn2Estrella = dialog.findViewById<Button>(R.id.btn2Estrella)
        val btn3Estrella = dialog.findViewById<Button>(R.id.btn3Estrella)
        val btn4Estrella = dialog.findViewById<Button>(R.id.btn4Estrella)
        val btn5Estrella = dialog.findViewById<Button>(R.id.btn5Estrella)

        btn1Estrella.setOnClickListener {
            calificarConEstrellas(1)
            dialog.dismiss()
        }

        btn2Estrella.setOnClickListener {
            calificarConEstrellas(2)
            dialog.dismiss()
        }

        btn3Estrella.setOnClickListener {
            calificarConEstrellas(3)
            dialog.dismiss()
        }

        btn4Estrella.setOnClickListener {
            calificarConEstrellas(4)
            dialog.dismiss()
        }

        btn5Estrella.setOnClickListener {
            calificarConEstrellas(5)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun calificarConEstrellas(calificacion: Int) {
        Toast.makeText(this, "Gracias por calificar con $calificacion estrellas", Toast.LENGTH_SHORT).show()
        // Aquí puedes realizar cualquier acción adicional con la calificación seleccionada
    }


    fun irPantallaPublicaciones(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@RevisarPublicacionesActivity, PrincipalPublicacionesActivity::class.java)
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


    fun irActivityPublicaciones(){
        for (usuario in arrayUsu) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaPublicaciones(
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

    fun irActivityRealizarCita(){
        for (usuario in arrayUsu) {
            if (usuario.correo == correo && usuario.contrasena == contrasena) {
                irPantallaRealizarCita(
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

    fun irPantallaRealizarCita(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@RevisarPublicacionesActivity,RealizarCitaActivity::class.java)
        intent.putExtra("idUsuario",idUsuario)
        intent.putExtra("nombre",nombre)
        intent.putExtra("correo",correo)
        intent.putExtra("contrasena",contrasena)
        intent.putExtra("telefono",telefono)
        intent.putExtra("ine",ine)
        intent.putExtra("perfil",perfil)
        startActivity(intent)
    }
}
