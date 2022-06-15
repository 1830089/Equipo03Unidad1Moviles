package com.upv.proyectounidad1equipo3;

import static java.sql.DriverManager.println;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DBProducts extends ProductosSqlite{
    Context context;


    public DBProducts(@Nullable Context context) {
        super(context);

        this.context=context;
    }

    public long insertarNuevoProducto(String nombre_producto, String DescripcionProducto){

        long id=0;
        try{
            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put("Product_name", nombre_producto);
            values.put("Description", DescripcionProducto);


            id= db.insert(tabla_products, null, values);

        }catch (Exception e){
            e.toString();
            println("aqui esta el error");

        }



        return  id;
    }

    public Products devuelveUltimoRegistro(){
        Products obj= new Products();
        try{
            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();

            Cursor CursorTicket= null;

            CursorTicket= db.rawQuery("SELECT * FROM "+tabla_products+" ORDER BY _id DESC LIMIT 1",null);

            if(CursorTicket.moveToFirst()) {
                obj.setId(CursorTicket.getInt(0));
                obj.setNombre_producto(CursorTicket.getString(1));
                obj.setDescription(CursorTicket.getString(2));
            }
            CursorTicket.close();



        }catch (Exception e){
            e.toString();
        }

        return obj;

    }
    
    public String getAllEntries(){
        StringBuilder all_entries = new StringBuilder();


    public Cursor mostrarProductos(){

        try{

            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();

            Cursor filas= db.rawQuery("SELECT * FROM "+tabla_products,null);

            if (filas.moveToFirst()){
                return  filas;
            }else {
                return  null;
            }

        }catch (Exception e){
            return null;
        }
    }


    public Products devuelveRegistroId(int id){
        Products obj= new Products();
        try{
            ProductosSqlite p= new ProductosSqlite(context);
            SQLiteDatabase db= p.getWritableDatabase();

            Cursor CursorTicket= null;

            CursorTicket= db.rawQuery("SELECT * FROM "+tabla_products+" WHERE _id="+id,null);

            if(CursorTicket.moveToFirst()) {
                obj.setId(CursorTicket.getInt(0));
                obj.setNombre_producto(CursorTicket.getString(1));
                obj.setDescription(CursorTicket.getString(2));
            }
            CursorTicket.close();



        }catch (Exception e){
            e.toString();
        }

        return obj;

    }


    public boolean editarProducto(int id, String nombre_producto, String DescripcionProducto){

        boolean correcto=false;

        ProductosSqlite p= new ProductosSqlite(context);
        SQLiteDatabase db= p.getWritableDatabase();

        try{
            db.execSQL("UPDATE "+tabla_products+" SET Product_name= '"+nombre_producto+"', Description= '"+ DescripcionProducto +"'WHERE _id='" +id+"' ");

           correcto=true;

        }catch (Exception e){
            e.toString();
            println("aqui esta el error");

        }finally {
            db.close();
        }



        return  correcto;
    }


    public ArrayList<Products> mostrarProductoslista(){

        ProductosSqlite p= new ProductosSqlite(context);
        SQLiteDatabase db= p.getWritableDatabase();

        ArrayList<Products> arr = new ArrayList<>();

        Products producto= null;

        Cursor CursorTicket= null;

        CursorTicket= db.rawQuery("SELECT * FROM "+tabla_products,null);

        if(CursorTicket.moveToFirst()){
            do {
                producto= new Products();
                producto.setId(CursorTicket.getInt(0));
                producto.setNombre_producto(CursorTicket.getString(1));
                producto.setDescription(CursorTicket.getString(2));
                arr.add(producto);

            }while (CursorTicket.moveToNext());
        }
        CursorTicket.close();
        return arr;

    }

}
