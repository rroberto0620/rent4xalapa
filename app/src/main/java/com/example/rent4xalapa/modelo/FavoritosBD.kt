package com.example.rent4xalapa.modelo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rent4xalapa.poko.Publicacion

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
        db.close()
        return filasAfectadas
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}