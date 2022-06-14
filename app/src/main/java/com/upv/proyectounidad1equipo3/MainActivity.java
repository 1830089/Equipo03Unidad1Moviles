package com.upv.proyectounidad1equipo3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.content.res.Resources;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder ADX;
    AlertDialog AD;

    Button btnTickets;
    Button btnProductos;
    Button btnConfBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTickets= (Button) findViewById(R.id.btnTickets);
        btnProductos= (Button) findViewById(R.id.btnProductos);
        btnConfBD= (Button) findViewById(R.id.btnImportar);

        btnTickets.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, Actividad_tickets.class);
                // Si se desea que la actividad hija regrese cadenas
                int CodigoPeticion;
                CodigoPeticion=2;
                startActivityForResult (intent,CodigoPeticion);
            }
        });

        btnProductos.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, Actividad_productos.class);
                // Si se desea que la actividad hija regrese cadenas
                int CodigoPeticion;
                CodigoPeticion=3;
                startActivityForResult (intent,CodigoPeticion);
            }
        });


        /*
        btnConfBD.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, Actividad_tickets.class);
                // Si se desea que la actividad hija regrese cadenas
                int CodigoPeticion;
                CodigoPeticion=2;
                startActivityForResult (intent,CodigoPeticion);
            }
        });*/

    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        ADX = new AlertDialog.Builder(this);
        AD = ADX.create();


        switch(item.getItemId()){
            case R.id.acerca:
                // Set the text color to red
                AD.setMessage("Integrantes del equipo\n 1.- CERDA PORRAS AMARIS AGLAHEL\n 2.- ZAVALA ARIAS AGUSTIN\n 3.- PINEDA AMADOR MARIANA LEILANY\n 4.- TORRES GRIMALDO JUAN JOSE\n 5.- HIGUERA SANCHEZ ROBERTO EDUARDO");
                AD.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}