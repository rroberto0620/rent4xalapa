package com.example.rent4xalapa

import android.os.Bundle
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.RevisarPerfilBinding
import com.example.rent4xalapa.databinding.RevisarPublicacionesBinding
import com.example.rent4xalapa.modelo.PublicacionesBD
import com.example.rent4xalapa.poko.Publicacion


class RevisarPublicacionesActivity : AppCompatActivity() {

    private lateinit var binding: RevisarPublicacionesBinding

    private var idPublicacion = 0
    private var titulo = ""
    private var descripcion = ""
    private var direccion = ""
    private var tipo = ""
    private var numHabitaciones = ""
    private var costo = 0
    private var petFriendly = 0
    private var servicios = 0
    private var amueblado = 0
    private var entradaCompartida = 0
    private var cochera = 0
    private var aire = 0
    private var calificacion = 0
    private var idUsuario = 0

    private lateinit var modelo : PublicacionesBD
    private lateinit var array: ArrayList<Publicacion>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RevisarPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo=PublicacionesBD(this@RevisarPublicacionesActivity)

        array = arrayListOf<Publicacion>()
        array = modelo.obtenerPublicaciones(idUsuario)

        idPublicacion = intent.getIntExtra("idPublicacion", 0)
        titulo = intent.getStringExtra("titulo")!!
        descripcion = intent.getStringExtra("descripcion")!!
        direccion = intent.getStringExtra("direccion")!!
        tipo = intent.getStringExtra("tipo")!!
        numHabitaciones = intent.getStringExtra("numero de habitaciones")!!
        costo = intent.getIntExtra("costo", 0)
        petFriendly = intent.getIntExtra("petFriendly", 0)
        servicios = intent.getIntExtra("servicios", 0)
        amueblado = intent.getIntExtra("amueblado", 0)
        entradaCompartida = intent.getIntExtra("entrada compartida", 0)
        cochera = intent.getIntExtra("cochera", 0)
        aire = intent.getIntExtra("aire acondicionado", 0)
        calificacion = intent.getIntExtra("calificacion", 0)
        idUsuario = intent.getIntExtra("idUsuario", 0)


        binding.tvNombreDepa.setText("Titulo: "+"$titulo")
        binding.tvNombreDepa.setText("Descripcion: "+"$descripcion")
        binding.tvNombreDepa.setText("Direccion: "+"$direccion")
        binding.tvNombreDepa.setText("Tipo: "+"$tipo")
        binding.tvNombreDepa.setText("Numero de habitaciones: "+"$numHabitaciones")
        binding.tvNombreDepa.setText("Costo: "+"$costo")
        binding.tvdetallesLugar.setText("Pet Friendly: "+"$petFriendly")
        binding.tvdetallesLugar.setText("Servicios: "+"$petFriendly")
        binding.tvdetallesLugar.setText("Amueblado: "+"$petFriendly")
        binding.tvdetallesLugar.setText("Entrada Compartida: "+"$petFriendly")
        binding.tvdetallesLugar.setText("Tiene cochera: "+"$petFriendly")
        binding.tvdetallesLugar.setText("Aire acondicionado: "+"$petFriendly")
        binding.tvdetallesLugar.setText("Calificacion: "+"$calificacion")



        binding.btnCalificar.setOnClickListener {
            AbrirCalificar()
        }

        binding.btnRealizar.setOnClickListener {
            irPantallaRealizarCita()
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


    fun irPantallaRealizarCita(){
        val intent = Intent(this@RevisarPublicacionesActivity, RealizarCitaActivity::class.java)
        startActivity(intent)
        finish()
    }
}
