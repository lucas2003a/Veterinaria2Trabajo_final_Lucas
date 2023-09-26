package com.example.veterinaria2_lucas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {
    final String MASCOTAS = "" +
            "CREATE TABLE 'mascotas'(" +
            "'idmascota'        INTEGER NOT NULL," +
            "'nombre'           TEXT    NOT NULL," +
            "'tipo'             TEXT    NOT NULL," +
            "'raza'             TEXT    NOT NULL," +
            "'peso'             REAL    NOT NULL," +
            "'color'            TEXT    NOT NULL," +
            "PRIMARY KEY        ('idmascota' AUTOINCREMENT)" +
            ")";
    final String CLIENTES = "" +
            "CREATE TABLE 'clientes'(" +
            "'idcliente'        INTEGER NOT NULL,"+
            "'apellidos'        TEXT    NOT NULL,"+
            "'nombres'          TEXT    NOT NULL,"+
            "'telefono'         TEXT    NOT NULL,"+
            "'email'            TEXT    NOT NULL,"+
            "'direccion'        TEXT    NOT NULL,"+
            "'fecha_nacimiento' TEXT    NOT NULL,"+
            "PRIMARY KEY        ('idcliente' AUTOINCREMENT)" +
            ")";
    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MASCOTAS);
        sqLiteDatabase.execSQL(CLIENTES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mascotas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS clientes");
        onCreate(sqLiteDatabase);
    }
}
