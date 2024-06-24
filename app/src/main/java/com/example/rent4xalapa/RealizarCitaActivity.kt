package com.example.rent4xalapa

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.RealizarCitaBinding
import com.example.rent4xalapa.modelo.ReservacionBD
import com.example.rent4xalapa.modelo.Usuarios
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Reservacion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Calendar
import java.util.TimeZone

class RealizarCitaActivity :AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : RealizarCitaBinding
    private lateinit var map: GoogleMap

    private var idUsuarioPub = 0
    private var idUsuario= 0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    private var idPublicacion = 0
    private var titulo = ""
    private var direccion = ""

    private var longitud = 0.0
    private var latitud = 0.0

    private var dayExt = 0
    private var monthExt = 0
    private var yearExt = 0

    private var hora = 0
    private var minuto = 0

    private lateinit var modelo : ReservacionBD
    private lateinit var modeloUsuario: Usuarios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RealizarCitaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        modelo = ReservacionBD(this@RealizarCitaActivity)
        modeloUsuario = Usuarios(this@RealizarCitaActivity)
        modelo.crearTabla()

        idUsuarioPub = intent.getIntExtra("idUser",0)
        Log.d("idUsuarioPub",idUsuarioPub.toString())
        val usuarioPublicacion = modeloUsuario.obtenerDatosUsuario(idUsuarioPub)

        idUsuario = intent.getIntExtra("idUsuario",0)
        nombre = intent.getStringExtra("nombre")!!
        telefono = intent.getLongExtra("telefono",0)
        idPublicacion = intent.getIntExtra("idPublicacion",0)
        titulo = intent.getStringExtra("titulo")!!
        direccion = intent.getStringExtra("direccion")!!
        latitud = intent.getDoubleExtra("latitud",0.0)
        longitud = intent.getDoubleExtra("longitud",0.0)

        if (usuarioPublicacion != null) {
            binding.etArrendador.setText(usuarioPublicacion.nombre)
        }

        binding.etContacto.setText(telefono.toString())

        binding.etFecha.setOnClickListener {
            showDatePickerDialog()
        }

        binding.etHora.setOnClickListener {
            showTimePickerDialog()
        }
        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnAceptar.setOnClickListener {
            Log.d("mensajeCitaValores","$dayExt/$monthExt/$yearExt")
            if (validarCamposCorrectos()) {
                val nuevaCita = crearCita()
                if (nuevaCita != null) {
                    agregarCita(nuevaCita)
                    añadirEvento()
                    Toast.makeText(this@RealizarCitaActivity, "Se realizo la cita ", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
        createFragment()
    }

    fun validarCamposCorrectos():Boolean{
        var valido = true
        if (binding.etFecha.text.isEmpty()){
            binding.etFecha.setError("La fecha es obligatorio")
            valido = false
        }
        if (binding.etHora.text.isEmpty()){
            binding.etHora.setError("La hora es obligatorio")
            valido = false
        }
        return valido
    }


    private fun crearCita(): Reservacion? {
        return try {
            Reservacion(
                idReservacion = 0,
                fecha = binding.etFecha.text.toString(),
                hora = binding.etHora.text.toString(),
                idPublicacion = idPublicacion,
                idUsuario = idUsuario
            )
        } catch (e: NumberFormatException) {

            Toast.makeText(this, "Error en los datos ingresados", Toast.LENGTH_SHORT).show()
            null
        }
    }

    fun agregarCita(reservacion : Reservacion){
        val resultadoInsercion = modelo.agregarReservacion(reservacion)
        var mensaje = ""
        if (resultadoInsercion>0){
            mensaje = "Cita registrada "
        }else{
            mensaje = "Hubo un error al guardar la cita"

        }
        Toast.makeText(this@RealizarCitaActivity, mensaje+reservacion.idReservacion, Toast.LENGTH_LONG).show()
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment{time,hourOfDay,minute -> onTimeSelected(time,hourOfDay,minute)}
        timePicker.show(supportFragmentManager,"time")
    }

    private fun onTimeSelected(time:String,hourOfDay:Int,minute:Int){
        binding.etHora.setText("$time")
        hora = hourOfDay
        minuto = minute

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager,"datePicker")
    }

    fun onDateSelected(day:Int, month:Int,year:Int){
        binding.etFecha.setText("$day/$month/$year")
        dayExt = day
        monthExt = month
        yearExt = year
    }

    private fun añadirEvento() {
        val beginCalendar = Calendar.getInstance()
        beginCalendar.set(yearExt, monthExt, dayExt, hora, minuto)

        val endCalendar = Calendar.getInstance()
        endCalendar.set(yearExt, monthExt, dayExt, hora+1, minuto)

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = android.provider.CalendarContract.Events.CONTENT_URI
            putExtra(android.provider.CalendarContract.Events.TITLE, "Cita $titulo")
            putExtra(android.provider.CalendarContract.Events.DESCRIPTION, "Recordatorio de una cita para acudir a revisar la casa en $direccion")
            putExtra(android.provider.CalendarContract.Events.EVENT_LOCATION, direccion)
            putExtra(android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCalendar.timeInMillis)
            putExtra(android.provider.CalendarContract.EXTRA_EVENT_END_TIME, endCalendar.timeInMillis)
            putExtra(android.provider.CalendarContract.Events.CALENDAR_ID, 1)  // Especifica el ID del calendario si es necesario
            putExtra(android.provider.CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        }
        intent.setPackage("com.google.android.calendar")
        startActivity(intent)
    }

    private fun createFragment(){
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.frag_ubicacion3) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isScrollGesturesEnabled = false
        map.uiSettings.isZoomGesturesEnabled = false
        map.uiSettings.isTiltGesturesEnabled = false
        map.uiSettings.isRotateGesturesEnabled = false

        // Agregar un marcador inicial en una ubicación predeterminada
        val initialLocation = LatLng(latitud,longitud) // Reemplaza con la ubicación deseada
        val initialMarker = map.addMarker(MarkerOptions().position(initialLocation).title("Marcador inicial"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15f))

        map.setOnMapClickListener { latLng ->
            // Obtener la latitud y longitud donde se hizo clic
            val latitude = latLng.latitude
            val longitude = latLng.longitude
            Toast.makeText(this, "Latitud: $latitude, Longitud: $longitude", Toast.LENGTH_LONG).show()
            // Redirigir a Google Maps con la ubicación clicada
            openGoogleMaps(latitude, longitude)
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun openGoogleMaps(latitude: Double, longitude: Double) {
        try {
            val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo abrir Google Maps, abriendo en el navegador...", Toast.LENGTH_LONG).show()
            openGoogleMapsInBrowser(latitude, longitude)
        }
    }

    private fun openGoogleMapsInBrowser(latitude: Double, longitude: Double) {
        val mapsUrl = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
        startActivity(webIntent)
    }
}