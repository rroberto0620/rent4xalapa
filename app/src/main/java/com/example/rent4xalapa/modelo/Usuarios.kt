package com.example.rent4xalapa.modelo


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rent4xalapa.poko.Usuario


class Usuarios (contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_BD,null, VERSION_BD){

    companion object{
        private const val NOMBRE_BD ="usuarios.db"
        private const val NOMBRE_TABLA ="usuarios"
        private const val COL_ID_USUARIO ="idUsuario"
        private const val COL_NOMBRE ="nombre"
        private const val COL_CORREO ="correo"
        private const val COL_CONTRASENA ="contrasena"
        private const val COL_TELEFONO ="telefono"
        private const val COL_INE="INE"
        private const val VERSION_BD =1

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE_NOTAS = "CREATE TABLE $NOMBRE_TABLA( $COL_ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT,$COL_NOMBRE VARCHAR(40),$COL_CORREO VARCHAR(40),$COL_CONTRASENA VARCHAR(20),$COL_TELEFONO INTEGER(10),$COL_INE VARCHAR(130))"
        p0!!.execSQL(CREATE_TABLE_NOTAS)
    }

    /*fun eliminarTabla(){
        val db = writableDatabase
        val ELIMINAR_TABLA = ("DROP TABLE $NOMBRE_TABLA")
        db!!.execSQL(ELIMINAR_TABLA)
    }*/

    fun crearTabla(){
        val db = writableDatabase
        val valor = db.execSQL("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA( $COL_ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT,$COL_NOMBRE VARCHAR(40),$COL_CORREO VARCHAR(40),$COL_CONTRASENA VARCHAR(20),$COL_TELEFONO INTEGER(10),$COL_INE VARCHAR(130))")
        return valor
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Para actulizacion de la verion de BD
    }

    fun insertarUsuarios(usuario: Usuario): Long {
        val db = writableDatabase
        val valoresInsert = ContentValues()
        valoresInsert.put(COL_NOMBRE,usuario.nombre)
        valoresInsert.put(COL_CORREO, usuario.correo)
        valoresInsert.put(COL_CONTRASENA, usuario.contrasena)
        valoresInsert.put(COL_TELEFONO,usuario.telefono)
        valoresInsert.put(COL_INE,usuario.ine)

        val filasAfectadas = db.insert(NOMBRE_TABLA,null,valoresInsert)
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun seleccionarUsuarios() : ArrayList<Usuario>{
        val misUsuarios = ArrayList<Usuario>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA,null,null,null,null,null,null)
        if (resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val idUsuario = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_ID_USUARIO))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val correo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_CORREO))
                val contrasena =resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_CONTRASENA))
                val telefono = resultadoConsulta.getLong(resultadoConsulta.getColumnIndex(
                    COL_TELEFONO))
                val ine=resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_INE))


                val usuario = Usuario(idUsuario,nombre,correo,contrasena,telefono,ine)
                misUsuarios.add(usuario)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misUsuarios
    }


    fun actualizarUsuario(usuario :Usuario):Int{
        val db = writableDatabase
        val valoresUpdate = ContentValues().apply {
            put(COL_NOMBRE, usuario.nombre)
            put(COL_CORREO, usuario.correo)
            put(COL_CONTRASENA, usuario.contrasena)
            put(COL_TELEFONO, usuario.telefono)
        }
        val filasAfectadas = db.update(NOMBRE_TABLA,valoresUpdate,"${COL_ID_USUARIO} = ?", arrayOf(usuario.idUsuario.toString()))
        db.close()
        return filasAfectadas
    }

    fun eliminarUsuario(idUsuario: Int): Int{
        val db = writableDatabase
        val resultado = db.delete(NOMBRE_TABLA,"$COL_ID_USUARIO=?", arrayOf(idUsuario.toString()))
        db.close()
        return resultado
    }


}