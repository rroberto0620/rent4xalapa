package com.example.rent4xalapa.modelo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
        db.close()
        return filasAfectadas
    }
}