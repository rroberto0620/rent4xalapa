package com.example.rent4xalapa

import android.os.Bundle
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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
    private var numHabitaciones = 0
    private var costo = 0.0
    private var petFriendly = 0
    private var servicios = 0
    private var amueblado = 0
    private var entradaCompartida = 0
    private var cochera = 0
    private var aire = 0
    private var imagenes = ""
    private var longitud = 0
    private var latitud = 0
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
        //array = modelo.obtenerPublicaciones(idUsuario)

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
        entradaCompartida = intent.getIntExtra("entrada compartida", 0)
        cochera = intent.getIntExtra("cochera", 0)
        aire = intent.getIntExtra("aire acondicionado", 0)
        imagenes = intent.getStringExtra("imagenes")!!
        calificacion = intent.getIntExtra("calificacion", 0)
        idUsuario = intent.getIntExtra("idUser", 0)


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
            AbrirCalificar()
        }

        binding.btnRealizar.setOnClickListener {
            irPantallaRealizarCita()
        }
        binding.btnRegresar.setOnClickListener {
            irPantallaPrincipal()
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

    fun irPantallaPrincipal(){
        val intent = Intent(this@RevisarPublicacionesActivity, PrincipalPublicacionesActivity::class.java)
        startActivity(intent)
        finish()
    }
}
