package com.upv.proyectounidad1equipo3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class ProductosSqlite extends SQLiteOpenHelper {

    private static  final String NOMBREBD= "ExperimentoTutores13.db";
    private static final  int version_base= 1;
    protected  static  final String tabla_tickets= "Tickets";
    protected static  final  String tabla_products= "Products";
    protected  static final String tabla_ticket_details= "TicketDetails";
    protected static final  String tabla_precios= "Prices";
    protected  static final String tabla_Products_prices= "ProductsPrices";

    String sqlCreateTickets   = "CREATE TABLE Tickets   (_id INTEGER PRIMARY KEY, nombre_ticket TEXT, fecha_ticket DATE, Total REAL, Place TEXT)";
    String sqlCreateProducts= "CREATE TABLE Products (_id INTEGER PRIMARY KEY, Product_name TEXT, Description TEXT)";

    String sqlCreatePrices= "CREATE TABLE Prices(_id INTEGER PRIMARY KEY, Price REAL) ";

    String sqlCreateTicket_details= "CREATE TABLE TicketsDetails (Ticket_id INTEGER, Products_id INTEGER, Quantity INTEGER, price_id INTEGER, FOREIGN KEY (Ticket_id) REFERENCES Tickets(_id), FOREIGN KEY (price_id) REFERENCES Prices(_id), FOREIGN KEY (Products_id) REFERENCES Products(_id), PRIMARY KEY(Ticket_id,Products_id,price_id))";


    String SqlCreateProducts_Prices= "CREATE TABLE ProductsPrices(product_id INTEGER, price_id INTEGER, start_date DATE, FOREIGN KEY (product_id) REFERENCES Products(_id), FOREIGN KEY (price_id) REFERENCES Prices(_id), PRIMARY KEY(product_id,price_id))";

    public ProductosSqlite(@Nullable Context context){
        super(context, NOMBREBD, null, version_base);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTickets);
        db.execSQL(sqlCreateProducts);
        db.execSQL(sqlCreatePrices);
        db.execSQL(SqlCreateProducts_Prices);
        db.execSQL(sqlCreateTicket_details);


    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //eliminamos la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Tickets");
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Prices");
        db.execSQL("DROP TABLE IF EXISTS  ProductsPrices");
        db.execSQL("DROP TABLE IF EXISTS TicketsDetails");

        //aqu� creamos la nueva versi�n de la tabla
        db.execSQL(sqlCreateTickets);
        db.execSQL(sqlCreateProducts);
        db.execSQL(sqlCreatePrices);
        db.execSQL(SqlCreateProducts_Prices);
        db.execSQL(sqlCreateTicket_details);


        //db.execSQL(sqlCreate2);
    }


}
