package com.example.rent4xalapa


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.rent4xalapa.databinding.PrincipalPublicacionesBinding
import com.example.rent4xalapa.databinding.RealizarPublicacionesBinding
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

class RealizarPublicacionesActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding : RealizarPublicacionesBinding
    private lateinit var map:GoogleMap
    private lateinit var modelo : PublicacionesBD
    private var idUsuario = 0
    private var longitud = 0.0
    private var latitud = 0.0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    private lateinit var modeloUsuarios : Usuarios
    private lateinit var array: ArrayList<Usuario>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RealizarPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        modeloUsuarios=Usuarios(this@RealizarPublicacionesActivity)
        array = arrayListOf<Usuario>()
        array = modeloUsuarios.seleccionarUsuarios()

        modelo = PublicacionesBD(this@RealizarPublicacionesActivity)
        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        correo = intent.getStringExtra("correo")!!
        contrasena = intent.getStringExtra("contrasena")!!
        telefono = intent.getLongExtra("telefono",0)
        ine = intent.getStringExtra("ine")!!
        perfil = intent.getStringExtra("perfil")!!
        binding.btnRealizarPublicacion.setOnClickListener {
            if (validarCamposCorrectos()) {
                val nuevaPublicacion = crearPublicacion()
                if (nuevaPublicacion != null) {
                    agregarPublicacion(nuevaPublicacion)
                }
            }
        }
        binding.btnCancelar.setOnClickListener {
            irActivityPublicaciones()
        }
        createFragment()
    }

    private fun createFragment(){
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.frag_ubicacion) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Agregar un marcador inicial en una ubicación predeterminada
        val initialLocation = LatLng(19.541858509261434, -96.92754932747631) // Reemplaza con la ubicación deseada
        val initialMarker = map.addMarker(MarkerOptions().position(initialLocation).title("Marcador inicial"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))
        // Configurar un listener para obtener la latitud y longitud donde se hace clic en el mapa
        map.setOnMapClickListener { latLng ->
            // Limpiar marcadores existentes si deseas agregar solo un marcador a la vez
            map.clear()

            // Agregar un marcador en la ubicación clicada
            val marker = map.addMarker(MarkerOptions().position(latLng).title("Nuevo marcador"))

            // Obtener la latitud y longitud del marcador
            val latitude = latLng.latitude
            val longitude = latLng.longitude
            latitud = latLng.latitude
            longitud = latLng.longitude
            Toast.makeText(this, "Latitud: $latitude, Longitud: $longitude", Toast.LENGTH_LONG).show()
        }
    }

    private fun crearPublicacion(): Publicacion? {
        return try {
            Publicacion(
                idPublicacion = 0,
                titulo = binding.etTituloPublicacion.text.toString(),
                descripcion = binding.etDescripcion.text.toString(),
                direccion = binding.etDireccion.text.toString(),
                tipo = binding.etTipo.text.toString(),
                numHabitaciones = binding.etNumHabitaciones.text.toString().toInt(),
                costo = binding.etCosto.text.toString().toDouble(),
                petFriendly = if (binding.cbPetfriendly.isChecked) 1 else 0,
                servicios = if (binding.cbServicios.isChecked) 1 else 0,
                amueblado = if (binding.cbAmueblado.isChecked) 1 else 0,
                entradaCompartida = if (binding.cbEntradaCompartida.isChecked) 1 else 0,
                cochera = if (binding.cbCochera.isChecked) 1 else 0,
                aire = if (binding.cbAire.isChecked) 1 else 0,
                imagenes = binding.etImagenesUrl.text.toString(),
                longitud = longitud,
                latitud = latitud,
                calificacion = 0,
                idUsuario = idUsuario
            )
        } catch (e: NumberFormatException) {

            Toast.makeText(this, "Error en los datos ingresados", Toast.LENGTH_SHORT).show()
            null
        }
    }


    fun agregarPublicacion(publicacion : Publicacion){
        val resultadoInsercion = modelo.agregarPublicacion(publicacion)
        var mensaje = ""
        if (resultadoInsercion>0){
            mensaje = "Publicacion registrada"
        }else{
            mensaje = "Hubo un error al guardar la publicacion"

        }
        Toast.makeText(this@RealizarPublicacionesActivity, mensaje, Toast.LENGTH_LONG).show()
    }


    fun validarCamposCorrectos():Boolean{
        var valido = true
        if (binding.etTituloPublicacion.text.isEmpty()){
            binding.etTituloPublicacion.setError("El titulo es obligatorio")
            valido = false
        }
        if (binding.etDescripcion.text.isEmpty()){
            binding.etDescripcion.setError("La descripcion es obligatorio")
            valido = false
        }
        if (binding.etDireccion.text.isEmpty()){
            binding.etDireccion.setError("La direccion es obligatoria")
            valido = false
        }
        if (binding.etTipo.text.isEmpty()){
            binding.etTipo.setError("El tipo de vivienda es obligatorio")
            valido = false
        }
        if (binding.etCosto.text.isEmpty()){
            binding.etCosto.setError("El costo es obligatorio")
            valido = false
        }
        return valido
    }

    fun irPantallaPublicaciones(idUsuario:Int,nombre:String, correo:String , contrasena:String , telefono:Long , ine:String,perfil:String){
        val intent = Intent(this@RealizarPublicacionesActivity, PrincipalPublicacionesActivity::class.java)
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
        for (usuario in array) {
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
}