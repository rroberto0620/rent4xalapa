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
import java.io.Console

class FavoritosBD (contexto: Context) : SQLiteOpenHelper(contexto, FavoritosBD.NOMBRE_BD,null, FavoritosBD.VERSION_BD) {

    companion object{
        private const val NOMBRE_BD ="usuarios.db"
        private const val NOMBRE_TABLA ="favoritos"
        private const val COL_ID_FAVORITO = "idFavorito"
        private const val COL_ID_PUBLICACION = "idPublicacion"
        private const val COL_ID_USUARIO="idUsuario"
        private const val VERSION_BD =1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var query = "CREATE TABLE ${NOMBRE_TABLA}(${COL_ID_FAVORITO} INTEGER PRIMARY KEY AUTOINCREMENT,foreign key(${COL_ID_PUBLICACION}) references PublicacionesBD(idPublicacion),foreign key(${COL_ID_USUARIO}) references Usuarios(idUsuario))"
        p0?.execSQL(query)
    }

    fun crearTabla(){
        val db = writableDatabase
        var valor = db.execSQL("CREATE TABLE IF NOT EXISTS ${NOMBRE_TABLA}(${COL_ID_FAVORITO} INTEGER PRIMARY KEY AUTOINCREMENT,$COL_ID_PUBLICACION INTEGER,$COL_ID_USUARIO INTEGER,foreign key(${COL_ID_PUBLICACION}) references PublicacionesBD(idPublicacion),foreign key(${COL_ID_USUARIO}) references Usuarios(idUsuario))")
        return valor
    }

    fun eliminarTabla(){
        val db = writableDatabase
        val ELIMINAR_TABLA = ("DROP TABLE ${NOMBRE_TABLA}")
        db!!.execSQL(ELIMINAR_TABLA)
    }

    fun agregarFavorito(favorito: Publicacion):Long{
        val db= writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_ID_PUBLICACION, favorito.idPublicacion)
        contentValue.put(COL_ID_USUARIO,favorito.idUsuario)
        val filasAfectadas = db.insert(NOMBRE_TABLA,null,contentValue)
        Log.d("mensaje", filasAfectadas.toString())
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun obtenerPublicaciones(idUsuario:String): List<Favorito>{
        val favoritos = mutableListOf<Favorito>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(
            NOMBRE_TABLA,null,"$COL_ID_USUARIO=?",
            arrayOf(idUsuario),null,null,null)
        if(resultadoConsulta !=null) {
            while (resultadoConsulta.moveToNext()) {
                val idFavorito = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_ID_FAVORITO)
                )
                val idPublicacion = resultadoConsulta.getInt(
                    resultadoConsulta.getColumnIndex(
                        COL_ID_PUBLICACION
                    )
                )
                val idUsuario =
                    resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_USUARIO))
                val favorito = Favorito(
                    idFavorito,
                    idPublicacion,
                    idUsuario)
                favoritos.add(favorito)
            }
            Log.d("msj", resultadoConsulta.count.toString())
            resultadoConsulta.close()
        }
        db.close()
        return favoritos
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}