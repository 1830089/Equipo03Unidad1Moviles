package com.upv.proyectounidad1equipo3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductosSqlite extends SQLiteOpenHelper {

    String sqlCreate   = "CREATE TABLE Productos   (_id INTEGER PRIMARY KEY, nombre_producto TEXT, descripcion_producto TEXT, cantidad_producto INTEGER, precio_producto REAL)";

    public ProductosSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //eliminamos la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Productos");

        //aqu� creamos la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
        //db.execSQL(sqlCreate2);
    }


}
