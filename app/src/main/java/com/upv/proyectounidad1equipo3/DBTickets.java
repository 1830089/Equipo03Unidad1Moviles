package com.upv.proyectounidad1equipo3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBTickets extends ProductosSqlite {
    Context context;

    public DBTickets(@Nullable Context context) {
        super(context);
        this.context= context;
    }

    public long insertarNuevoTicket(String nombre, String descripcion){

        long id=0;
        try{
            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(c.getTime());

            ContentValues values = new ContentValues();

            values.put("nombre_ticket", nombre);
            values.put("fecha_ticket", date);
            values.put("Place", descripcion);


           id= db.insert(tabla_tickets, null, values);

        }catch (Exception e){
            e.toString();

        }



        return  id;
    }


    public ArrayList<Tickets> mostrarTickets(){

        ProductosSqlite p= new ProductosSqlite(context);
        SQLiteDatabase db= p.getWritableDatabase();

        ArrayList<Tickets> arr = new ArrayList<>();

        Tickets ticket= null;

        Cursor CursorTicket= null;

        CursorTicket= db.rawQuery("SELECT * FROM "+tabla_tickets,null);

        if(CursorTicket.moveToFirst()){
            do {
                ticket= new Tickets();
                ticket.setId(CursorTicket.getInt(0));
                ticket.setNombre_ticket(CursorTicket.getString(1));
                ticket.setFecha_ticket(CursorTicket.getString(2));
                ticket.setTotal(CursorTicket.getFloat(3));
                ticket.setPlace(CursorTicket.getString(4));
                arr.add(ticket);

            }while (CursorTicket.moveToNext());
        }
        CursorTicket.close();
        return arr;

    }




}
