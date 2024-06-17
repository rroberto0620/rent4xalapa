package com.example.rent4xalapa

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.PrincipalPublicacionesBinding
import com.example.rent4xalapa.databinding.RealizarPublicacionesBinding
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.poko.Publicacion

class RealizarPublicacionesActivity : AppCompatActivity() {
    private lateinit var binding : RealizarPublicacionesBinding
    private lateinit var modelo : PublicacionesBD
    private var idUsuario = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RealizarPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo = PublicacionesBD(this@RealizarPublicacionesActivity)
        idUsuario = intent.getIntExtra("idUsuario",0)

        binding.btnRealizarPublicacion.setOnClickListener {
            if (validarCamposCorrectos()) {
                val nuevaPublicacion = crearPublicacion()
                if (nuevaPublicacion != null) {
                    agregarPublicacion(nuevaPublicacion)
                }
            }
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
                longitud = 0.0,
                latitud = 0.0,
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
}