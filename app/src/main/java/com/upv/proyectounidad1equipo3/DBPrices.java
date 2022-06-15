package com.upv.proyectounidad1equipo3;

import static java.sql.DriverManager.println;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBPrices extends ProductosSqlite{

    Context context;
    public DBPrices(@Nullable Context context) {
        super(context);
        this.context= context;
    }


    public long insertarNuevoPrecio(float precio_producto){

        long id=0;
        try{
            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put("Price", precio_producto);


            id= db.insert(tabla_precios, null, values);

        }catch (Exception e){
            e.toString();
            println("aqui esta el error");

        }



        return  id;
    }


    public Prices devuelveUltimoRegistro(){
        Prices obj= new Prices();
        try{
            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();


            Cursor CursorTicket= null;

            CursorTicket= db.rawQuery("SELECT * FROM "+tabla_precios+" ORDER BY _id DESC LIMIT 1",null);

            //System.out.println("estas imprimiendo esto bro "+CursorTicket.getInt(0));

            if(CursorTicket.moveToFirst()){
                obj.setId(CursorTicket.getInt(0));
                obj.setPrice(CursorTicket.getFloat(1));
            }



            CursorTicket.close();



        }catch (Exception e){
            e.toString();
        }

        return obj;

    }
}
