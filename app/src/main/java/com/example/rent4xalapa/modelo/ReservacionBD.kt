package com.example.rent4xalapa.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.rent4xalapa.poko.Favorito
import com.example.rent4xalapa.poko.Publicacion
import com.example.rent4xalapa.poko.Reservacion

class ReservacionBD(contexto: Context) : SQLiteOpenHelper(contexto,NOMBRE_BD,null, VERSION_BD){
companion object{
    private const val NOMBRE_BD ="usuarios.db"
    private const val NOMBRE_TABLA ="reservaciones"
    private const val COL_ID_RESERVACION = "idReservacion"
    private const val COL_FECHA = "fecha"
    private const val COL_HORA = "hora"
    private const val COL_ID_PUBLICACION = "idPublicacion"
    private const val COL_ID_USUARIO="idUsuario"
    private const val VERSION_BD =1
}

override fun onCreate(p0: SQLiteDatabase?) {
    var query = "CREATE TABLE ${NOMBRE_TABLA}(${COL_ID_RESERVACION} INTEGER PRIMARY KEY AUTOINCREMENT,${COL_FECHA} VARCHAR(15),${COL_HORA} VARCHAR(15),foreign key(${COL_ID_PUBLICACION}) references PublicacionesBD(idPublicacion),foreign key(${COL_ID_USUARIO}) references Usuarios(idUsuario))"
    p0?.execSQL(query)
}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearTabla(){
    val db = writableDatabase
    var valor = db.execSQL("CREATE TABLE IF NOT EXISTS ${NOMBRE_TABLA}(${COL_ID_RESERVACION} INTEGER PRIMARY KEY AUTOINCREMENT,${COL_FECHA} VARCHAR(15),${COL_HORA} VARCHAR(15),${COL_ID_USUARIO} INTEGER,${COL_ID_PUBLICACION} INTEGER,foreign key(${COL_ID_PUBLICACION}) references PublicacionesBD(idPublicacion),foreign key(${COL_ID_USUARIO}) references Usuarios(idUsuario))")
    return valor
}

    fun agregarReservacion(reservacion: Reservacion):Long{
        val db= writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_FECHA, reservacion.fecha)
        contentValue.put(COL_HORA, reservacion.hora)
        contentValue.put(COL_ID_PUBLICACION, reservacion.idPublicacion)
        contentValue.put(COL_ID_USUARIO,reservacion.idUsuario)
        val filasAfectadas = db.insert(NOMBRE_TABLA,null,contentValue)
        Log.d("mensajeReservacion", filasAfectadas.toString())
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun seleccionarReservacion(idUsuario: String): List<Reservacion> {
        val misReservaciones = mutableListOf<Reservacion>()
        val db = readableDatabase
        val selection = "$COL_ID_USUARIO = ?"
        val selectionArgs = arrayOf(idUsuario)
        val resultadoConsulta: Cursor? = db.query(NOMBRE_TABLA, null, selection, selectionArgs, null, null, null)
        if (resultadoConsulta != null) {
            while (resultadoConsulta.moveToNext()) {
                val idReservacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_RESERVACION))
                val fecha = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_FECHA))
                val hora = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_HORA))
                val idUsuario = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_USUARIO))
                val idPublicacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_PUBLICACION))
                val reservacion = Reservacion(idReservacion,fecha,hora,idUsuario,idPublicacion)
                Log.d("msjReservacionConsulta","reservacion ${reservacion.idReservacion} ${reservacion.idPublicacion} ${reservacion.fecha}")
                misReservaciones.add(reservacion)
            }
            Log.d("msj", resultadoConsulta.count.toString())
            resultadoConsulta.close()
        }
        db.close()
        return misReservaciones
    }

    @SuppressLint("Range")
    fun obtenerPublicacionesReservacion(idUsuario:String): List<Reservacion>{
        val reservaciones = mutableListOf<Reservacion>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(
            NOMBRE_TABLA,null,"$COL_ID_USUARIO=?",
            arrayOf(idUsuario),null,null,null)
        if(resultadoConsulta !=null) {
            while (resultadoConsulta.moveToNext()) {
                val idReservacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_ID_RESERVACION
                ))
                val fecha = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_FECHA))
                val hora = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_HORA))
                val idUsuario =
                    resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_USUARIO))
                val idPublicacion = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_ID_PUBLICACION))
                val reservacion = Reservacion(
                    idReservacion,
                    fecha,
                    hora,
                    idUsuario,
                    idPublicacion)
                reservaciones.add(reservacion)
            }
            Log.d("msj", resultadoConsulta.count.toString())
            resultadoConsulta.close()
        }
        db.close()
        return reservaciones
    }

    fun reservacionExiste(idUsuario: Int, idPublicacion: Int): Boolean {
        val db = readableDatabase
        val selection = "$COL_ID_USUARIO = ? AND $COL_ID_PUBLICACION = ?"
        val selectionArgs = arrayOf(idUsuario.toString(), idPublicacion.toString())

        val cursor = db.query(
            NOMBRE_TABLA,
            arrayOf(COL_ID_USUARIO,COL_ID_PUBLICACION),
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }

    fun eliminarReservacion(idReservacion: Int): Int{
        val db = writableDatabase
        val resultado = db.delete(NOMBRE_TABLA,"$COL_ID_RESERVACION=?", arrayOf(idReservacion.toString()))
        db.close()
        return resultado
    }

    fun eliminarTabla(){
        val db = writableDatabase
        val ELIMINAR_TABLA = ("DROP TABLE $NOMBRE_TABLA")
        db!!.execSQL(ELIMINAR_TABLA)
    }
}