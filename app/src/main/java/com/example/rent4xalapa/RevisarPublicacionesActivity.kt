package com.example.rent4xalapa

import android.os.Bundle
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.RevisarPublicacionesBinding

class RevisarPublicacionesActivity : AppCompatActivity() {

    private lateinit var binding: RevisarPublicacionesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.btnCalificar.setOnClickListener {
            AbrirCalificar()
        }

        binding.btnRealizar.setOnClickListener {
//            irPantallaRealizarCita()
        }
    }

    fun AbrirCalificar() {
        val builder = AlertDialog.Builder(this@RevisarPublicacionesActivity)
        builder.setTitle("Calificar publicación")
        builder.setMessage("Selecciona una calificación del 1 al 5")


        val calificaciones = arrayOf("1", "2", "3", "4", "5")
        val calificacionSeleccionada = IntArray(1)


        builder.setSingleChoiceItems(calificaciones, -1, DialogInterface.OnClickListener { dialog, which ->
            calificacionSeleccionada[0] = which
        })

        builder.setPositiveButton("Calificar") { dialog, _ ->
            val calificacion = calificaciones[calificacionSeleccionada[0]]
            Toast.makeText(this@RevisarPublicacionesActivity, "Gracias por calificar con $calificacion estrellas", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancelar", null)


        val dialog = builder.create()
        dialog.show()
    }


//    fun irPantallaRealizarCita(){
//        val intent = Intent(this@RevisarPublicaciones, RealizarCita::class.java)
//        startActivity(intent)
//        finish()
//    }
}
