package com.upv.proyectounidad1equipo3;

import static java.sql.DriverManager.println;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBTicket_Details extends ProductosSqlite{

    Context context;
    public DBTicket_Details(@Nullable Context context) {
        super(context);
        this.context=context;
    }


}
