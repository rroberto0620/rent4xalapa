package com.example.rent4xalapa.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rent4xalapa.poko.Publicacion

class PublicacionesBD (contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_BD,null, VERSION_BD){

    companion object{
        private const val NOMBRE_BD ="usuarios.db"
        private const val NOMBRE_TABLA ="publicaciones"
        private const val COL_ID_PUBLICACION = "idPublicacion"
        private const val COL_TITULO="titulo"
        private const val COL_DESCRIPCION="descripcion"
        private const val COL_DIRECCION="direccion"
        private const val COL_TIPO="tipo"
        private const val COL_NUM_HABITACIONES="numHabitaciones"
        private const val COL_COSTO="costo"
        private const val COL_PET_FRIENDLY="petFriendly"
        private const val COL_SERVICIOS="servicios"
        private const val COL_AMUEBLADO="amueblado"
        private const val COL_ENTRADA_COMPARTIDA="entradaCompartida"
        private const val COL_COCHERA="cochera"
        private const val COL_AIRE="aire"
        private const val COL_IMAGENES="imagenes"
        private const val COL_LONGITUD="longitud"
        private const val COL_LATITUD="latitud"
        private const val COL_CALIFICACION="calificacion"
        private const val COL_ID_USUARIO="idUsuario"
        private const val VERSION_BD =1

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var query = "CREATE TABLE $NOMBRE_TABLA($COL_ID_PUBLICACION INTEGER PRIMARY KEY AUTOINCREMENT,$COL_TITULO VARCHAR(40),$COL_DESCRIPCION VARCHAR(100),$COL_DIRECCION VARCHAR(70),$COL_TIPO VARCHAR(40),$COL_NUM_HABITACIONES INTEGER(10),$COL_COSTO INTEGER(20),$COL_PET_FRIENDLY INTEGER,$COL_SERVICIOS INTEGER,$COL_AMUEBLADO INTEGER,$COL_ENTRADA_COMPARTIDA INTEGER,$COL_COCHERA INTEGER,$COL_AIRE INTERGER,$COL_IMAGENES VARCHAR(150),$COL_LONGITUD DOUBLE,$COL_LATITUD DOUBLE,$COL_CALIFICACION INTEGER,$COL_ID_USUARIO INTEGER,foreign key($COL_ID_USUARIO) references Usuarios(idUsuario))"
        p0?.execSQL(query)
    }
    fun crearTabla(){
        val db = writableDatabase
        var valor = db.execSQL("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA($COL_ID_PUBLICACION INTEGER PRIMARY KEY AUTOINCREMENT,$COL_TITULO VARCHAR(40),$COL_DESCRIPCION VARCHAR(100),$COL_DIRECCION VARCHAR(70),$COL_TIPO VARCHAR(40),$COL_NUM_HABITACIONES INTEGER(10),$COL_COSTO INTEGER(20),$COL_PET_FRIENDLY INTEGER,$COL_SERVICIOS INTEGER,$COL_AMUEBLADO INTEGER,$COL_ENTRADA_COMPARTIDA INTEGER,$COL_COCHERA INTEGER,$COL_AIRE INTEGER,$COL_IMAGENES VARCHAR(150),$COL_LONGITUD DOUBLE,$COL_LATITUD DOUBLE,$COL_CALIFICACION INTEGER,$COL_ID_USUARIO INTEGER,foreign key($COL_ID_USUARIO) references Usuarios(idUsuario))")
        return valor
    }
    fun eliminarTabla(){
        val db = writableDatabase
        val ELIMINAR_TABLA = ("DROP TABLE $NOMBRE_TABLA")
        db!!.execSQL(ELIMINAR_TABLA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun agregarPublicacion(publicacion:Publicacion):Long{
        val db= writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_TITULO, publicacion.titulo)
        contentValue.put(COL_DESCRIPCION,publicacion.descripcion)
        contentValue.put(COL_DIRECCION, publicacion.direccion)
        contentValue.put(COL_TIPO,publicacion.tipo)
        contentValue.put(COL_NUM_HABITACIONES, publicacion.numHabitaciones)
        contentValue.put(COL_COSTO,publicacion.costo)
        contentValue.put(COL_PET_FRIENDLY, publicacion.petFriendly)
        contentValue.put(COL_SERVICIOS, publicacion.servicios)
        contentValue.put(COL_AMUEBLADO, publicacion.amueblado)
        contentValue.put(COL_ENTRADA_COMPARTIDA,publicacion.entradaCompartida)
        contentValue.put(COL_COCHERA,publicacion.cochera)
        contentValue.put(COL_AIRE,publicacion.aire)
        contentValue.put(COL_IMAGENES,publicacion.imagenes)
        contentValue.put(COL_LONGITUD,publicacion.longitud)
        contentValue.put(COL_LATITUD,publicacion.longitud)
        contentValue.put(COL_ID_USUARIO,publicacion.idUsuario)
        val filasAfectadas = db.insert(NOMBRE_TABLA,null,contentValue)
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun seleccionarNotas() : List<Publicacion>{
        val publicaciones = mutableListOf<Publicacion>()
        val db = readableDatabase
        val resultadoConsulta : Cursor? = db.query(NOMBRE_TABLA,null,null,null,null,null,null)
        if(resultadoConsulta !=null){
            while(resultadoConsulta.moveToNext()){
                val idPublicacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_ID_PUBLICACION))
                val titulo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_TITULO))
                val descripcion = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_DESCRIPCION))
                val direccion = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_DIRECCION))
                val tipo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_TIPO))
                val numHabitaciones = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_NUM_HABITACIONES))
                val costo = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_COSTO))
                val petFriendly = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_PET_FRIENDLY))
                val servicios = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_SERVICIOS))
                val amueblado = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_AMUEBLADO))
                val entradaCompartida = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_ENTRADA_COMPARTIDA))
                val cochera = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_COCHERA))
                val aire = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_AIRE))
                val imagenes = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_IMAGENES))
                val longitud = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_LONGITUD))
                val latitud = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_LATITUD))
                val califiacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_CALIFICACION))
                val idUsuario = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_USUARIO))
                val publicacion = Publicacion(idPublicacion,titulo,descripcion,direccion,tipo,numHabitaciones,costo,petFriendly,servicios,amueblado,entradaCompartida,cochera,aire,imagenes,longitud,latitud,califiacion,idUsuario)
                publicaciones.add(publicacion)
            }
            resultadoConsulta.close()
        }
        db.close()
        return publicaciones
    }

    @SuppressLint("Range")
    fun obtenerPublicaciones(idUsuario:Int): List<Publicacion>{
        val publicaciones = mutableListOf<Publicacion>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA,null,"$COL_ID_USUARIO=?", arrayOf(arrayOf(idUsuario).toString()),null,null,null)
        while (resultadoConsulta.moveToNext()){
            val idPublicacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                COL_ID_PUBLICACION))
            val titulo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_TITULO))
            val descripcion = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                COL_DESCRIPCION))
            val direccion = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                COL_DIRECCION))
            val tipo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_TIPO))
            val numHabitaciones = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                COL_NUM_HABITACIONES))
            val costo = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_COSTO))
            val petFriendly = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                COL_PET_FRIENDLY))
            val servicios = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_SERVICIOS))
            val amueblado = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_AMUEBLADO))
            val entradaCompartida = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                COL_ENTRADA_COMPARTIDA))
            val cochera = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_COCHERA))
            val aire = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_AIRE))
            val imagenes = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_IMAGENES))
            val longitud = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_LONGITUD))
            val latitud = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_LATITUD))
            val califiacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                COL_CALIFICACION))
            val idUsuario = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_USUARIO))
            val publicacion = Publicacion(idPublicacion,titulo,descripcion,direccion,tipo,numHabitaciones,costo,petFriendly,servicios,amueblado,entradaCompartida,cochera,aire,imagenes,longitud,latitud,califiacion,idUsuario)
            publicaciones.add(publicacion)
        }
        return publicaciones
    }

    fun eliminarPublicacion(idPublicacion : Int):Int{
        val db = writableDatabase
        val resultado = db.delete(NOMBRE_TABLA,"$COL_ID_PUBLICACION=?", arrayOf(idPublicacion.toString()))
        db.close()
        return resultado
    }

}