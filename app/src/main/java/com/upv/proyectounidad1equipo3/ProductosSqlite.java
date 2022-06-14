package com.upv.proyectounidad1equipo3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProductosSqlite extends SQLiteOpenHelper {

    private static  final String NOMBREBD= "ExperimentoTutores06.db";
    private static final  int version_base= 1;
    protected  static  final String tabla_tickets= "Tickets";
    protected static  final  String tabla_products= "Products";
    protected  static final String tabla_ticket_details= "TicketDetails";

    String sqlCreateTickets   = "CREATE TABLE Tickets   (_id INTEGER PRIMARY KEY, nombre_ticket TEXT, fecha_ticket DATE, Total REAL, Place TEXT)";
    String sqlCreateProducts= "CREATE TABLE Products (_id INTEGER PRIMARY KEY, Product_name TEXT, Description TEXT, Price REAL)";
    String sqlCreateTicket_details= "CREATE TABLE TicketsDetails (Ticket_id INTEGER, Products_id INTEGER, Quantity INTEGER, Unit_price REAL, FOREIGN KEY (Ticket_id) REFERENCES Tickets(_id), FOREIGN KEY (Products_id) REFERENCES Products(_id), PRIMARY KEY(Ticket_id,Products_id))";

    //String sqlCreatePrices= "CREATE TABLE Prices(precio_id INTEGER, Products_id INTEGER, Price REAL, Start_date DATE, FOREIGN KEY (Products_id) REFERENCES Products(_id), PRIMARY KEY(precio_id,Products_id)) ";

    public ProductosSqlite(@Nullable Context context){
        super(context, NOMBREBD, null, version_base);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTickets);
        db.execSQL(sqlCreateProducts);
        //db.execSQL(sqlCreatePrices);
        db.execSQL(sqlCreateTicket_details);


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
        db.execSQL("DROP TABLE IF EXISTS Tickets");
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Prices");
        db.execSQL("DROP TABLE IF EXISTS TicketsDetails");

        //aqu� creamos la nueva versi�n de la tabla
        db.execSQL(sqlCreateTickets);
        db.execSQL(sqlCreateProducts);
        //db.execSQL(sqlCreatePrices);
        db.execSQL(sqlCreateTicket_details);
        //db.execSQL(sqlCreate2);
    }


}
