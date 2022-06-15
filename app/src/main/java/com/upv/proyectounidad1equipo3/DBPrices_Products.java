package com.upv.proyectounidad1equipo3;

import static java.sql.DriverManager.println;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBPrices_Products extends ProductosSqlite{

    Context context;
    public DBPrices_Products(@Nullable Context context) {
        super(context);
        this.context= context;
    }


    public long insertarNuevaRelacion(int id_producto, int id_precio){

        long id=0;
        try{
            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(c.getTime());

            ContentValues values = new ContentValues();

            values.put("product_id", id_producto);
            values.put("price_id", id_precio);
            values.put("start_date", date);


            id= db.insert(tabla_Products_prices, null, values);

        }catch (Exception e){
            e.toString();

        }



        return  id;

    }



}
