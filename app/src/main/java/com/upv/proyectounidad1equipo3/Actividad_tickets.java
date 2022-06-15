package com.upv.proyectounidad1equipo3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.res.Resources;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Actividad_tickets extends AppCompatActivity {

    Button boton_nuevo_producto;
    TextView TV2;
    EditText nombre_producto;
    EditText descripcion_producto;
    EditText cantidad_producto;
    EditText precio_producto;
    AlertDialog.Builder ADX;
    AlertDialog AD;
    Spinner sp;
    ListView mostrarTickets;

    ArrayList<String> arrTick;
    SimpleCursorAdapter sca;
    final String NOMBRE_BASE_DATOS = "ExperimentoTutores05.db";

    final String TABLA_PRINCIPAL = "Tickets";
    //final String TABLA_SECUNDARIA= "Tutorados";

    ProductosSqlite usdbh;
    Cursor cursor;
    int SiguienteID;
    String[] argumentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_tickets);



        Resources res = getResources();
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Nuevo ticket");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("agrega productos");

        TabHost.TabSpec spec3 = tabHost.newTabSpec("");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("tickets");
        //spec3.setIndicator("",getResources().getDrawable(R.mipmap.ic_launcher));

        TabHost.TabSpec spec4 = tabHost.newTabSpec("");
        spec4.setContent(R.id.tab4);
        spec4.setIndicator("Graficos");

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.addTab(spec4);

        // Otros Recursos (TextView, Buttons, ListView, EditText, etx)
        SiguienteID = 0;


        argumentos = new String[1];

        ADX = new AlertDialog.Builder(this);
        AD = ADX.create();
        mostrarTickets= (ListView) findViewById(R.id.listViewTickets);
        boton_nuevo_producto = (Button) findViewById(R.id.btnGuardar);
        TV2 = (TextView) findViewById(R.id.TV2);
        nombre_producto = findViewById(R.id.etnombre_producto);
        descripcion_producto = findViewById(R.id.etdescripcion_producto);
        //cantidad_producto = findViewById(R.id.etCantidad);
        //precio_producto = findViewById(R.id.etPrecio);
        sp = (Spinner) findViewById(R.id.spinnerproductos);

        obtenerInfo();

        ArrayAdapter adaptadorListViewTickets= new ArrayAdapter(Actividad_tickets.this, android.R.layout.simple_list_item_1,arrTick);

        mostrarTickets.setAdapter(adaptadorListViewTickets);


        boton_nuevo_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //Si hemos abierto correctamente la base de datos
                usdbh= new ProductosSqlite(Actividad_tickets.this);
                SQLiteDatabase db= usdbh.getWritableDatabase();
                if (db != null) {
                    DBTickets ticketsdb= new DBTickets(Actividad_tickets.this);

                    long id= ticketsdb.insertarNuevoTicket(nombre_producto.getText().toString(),descripcion_producto.getText().toString());

                    if(id>0){
                        Toast.makeText(Actividad_tickets.this,"REGISTRO GUARDADO EXITOSAMENTE", Toast.LENGTH_LONG).show();
                        limpiarNuevoTicket();
                        ConsultaTabla_ActualizaControl();
                        ConsultaTabla_ActualizaSpinner();


                    }else {
                        Toast.makeText(Actividad_tickets.this,"ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();

                    }

                }else{
                    Toast.makeText(Actividad_tickets.this, "erro al conectar bd",Toast.LENGTH_LONG).show();
                }

            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Spinner 1" + parent.getId() + "Elemento:[" + position + "]", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }




    void ConsultaTabla_ActualizaControl ()
    {
        usdbh= new ProductosSqlite(Actividad_tickets.this);
        SQLiteDatabase db= usdbh.getWritableDatabase();
        String C1, C2, C3,C4,C5,C6;
        String Fin="";
        cursor = db.rawQuery("select * from "+TABLA_PRINCIPAL, null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    C1 = cursor.getString(cursor
                            .getColumnIndexOrThrow("_id"));

                    C2 = cursor.getString(cursor
                            .getColumnIndexOrThrow("nombre_ticket"));

                    C3 = cursor.getString(cursor
                            .getColumnIndexOrThrow("fecha_ticket"));

                    C5 = cursor.getString(cursor
                            .getColumnIndexOrThrow("Place"));


                    Fin += C1 + "-" + C2 + "-" + C3 + "-" + C5 + "-" +"\n";

                } while (cursor.moveToNext());
            }
            TV2.setText(Fin);
        }
        cursor.close();
    }

    void ConsultaTabla_ActualizaSpinner ()
    {
        //cursor = db.rawQuery("select * from "+TABLA_PRINCIPAL, null);

        usdbh= new ProductosSqlite(Actividad_tickets.this);
        SQLiteDatabase db= usdbh.getWritableDatabase();

        String[] queryCols=new String[]{"_id", "nombre_ticket"};
        String[] adapterCols=new String[]{"nombre_ticket"};
        int[] adapterRowViews=new int[]{android.R.id.text1};
        cursor=db.query(true,TABLA_PRINCIPAL, queryCols,null,null,null,null,null,null);
        sca=new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, adapterCols, adapterRowViews,0);
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(sca);

    }


    private void limpiarNuevoTicket(){
        nombre_producto.setText("");
        descripcion_producto.setText("");
    }


    private void obtenerInfo(){

        arrTick= new ArrayList<String>();
        DBTickets tickets_db= new DBTickets(Actividad_tickets.this);


        for(int i=0; i<tickets_db.mostrarTickets().size();i++){

            arrTick.add(tickets_db.mostrarTickets().get(i).getNombre_ticket());
        }
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
