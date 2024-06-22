package com.example.rent4xalapa

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.RealizarCitaBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RealizarCitaActivity :AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : RealizarCitaBinding
    private lateinit var map: GoogleMap
    private var idUsuario= 0
    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var telefono:Long = 0
    private var ine = ""
    private var perfil = ""

    private var titulo = ""
    private var direccion = ""

    private var longitud = 0.0
    private var latitud = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RealizarCitaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        nombre = intent.getStringExtra("nombre")!!
        telefono = intent.getLongExtra("telefono",0)
        titulo = intent.getStringExtra("titulo")!!
        direccion = intent.getStringExtra("direccion")!!
        latitud = intent.getDoubleExtra("latitud",0.0)
        longitud = intent.getDoubleExtra("longitud",0.0)

        binding.etArrendador.setText(nombre)
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
            Toast.makeText(this@RealizarCitaActivity, "Se realizo la cita ", Toast.LENGTH_LONG).show()
            finish()
        }
        createFragment()
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment{onTimeSelected(it)}
        timePicker.show(supportFragmentManager,"time")
    }

    private fun onTimeSelected(time:String){
        binding.etHora.setText("$time")
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager,"datePicker")
    }

    fun onDateSelected(day:Int, month:Int,year:Int){
        binding.etFecha.setText("$day/$month/$year")
    }

//    private fun addEventToCalendar() {
//        val calendar = Calendar.getInstance()
//        calendar.add(Calendar.SECOND, 10) // Configura la hora del recordatorio (ejemplo: 10 segundos desde ahora)
//
//        val intent = Intent(Intent.ACTION_INSERT).apply {
//            data = android.provider.CalendarContract.Events.CONTENT_URI
//            putExtra(android.provider.CalendarContract.Events.TITLE, titulo)
//            putExtra(android.provider.CalendarContract.Events.DESCRIPTION, "Cita para acudir a revisar la casa en $direccion")
//            putExtra(android.provider.CalendarContract.Events.EVENT_LOCATION, "$direccion")
//            putExtra(android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.timeInMillis)
//            putExtra(android.provider.CalendarContract.EXTRA_EVENT_END_TIME, calendar.timeInMillis + 60 * 60 * 1000) // Duración de una hora
//        }
//
//        if (intent.resolveActivity(packageManager) != null) {
//            startActivity(intent)
//        }
//    }

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
            Toast.makeText(this, "No se pudo abrir Google Maps, abriendo en el navegador", Toast.LENGTH_LONG).show()
            openGoogleMapsInBrowser(latitude, longitude)
        }
    }

    private fun openGoogleMapsInBrowser(latitude: Double, longitude: Double) {
        val mapsUrl = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
        startActivity(webIntent)
    }
}