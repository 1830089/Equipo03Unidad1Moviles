package com.upv.proyectounidad1equipo3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import  java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Actividad_productos extends AppCompatActivity {

    Button boton_nuevo_producto;
    TextView TV2;
    EditText nombre_producto;
    EditText descripcion_producto;
    EditText precio_producto;

    EditText nombre_productoEditar;
    EditText descripcion_productoEditar;
    Button otroPrecio;
    Button guardarEditar;
    Spinner spinnerProductos;
    TextView tvid;
    ListView productoslistView;

    AlertDialog.Builder ADX;
    AlertDialog AD;
    Spinner sp;

    SimpleCursorAdapter sca;

    ProductosSqlite usdbh;
    SQLiteDatabase db;
    Cursor cursor;
    int SiguienteID;
    String[] argumentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_productos);

        Resources res = getResources();
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Nuevo producto");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Editar Producto");

        TabHost.TabSpec spec3 = tabHost.newTabSpec("");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Productos");
        //spec3.setIndicator("",getResources().getDrawable(R.mipmap.ic_launcher));

        TabHost.TabSpec spec4 = tabHost.newTabSpec("");
        spec4.setContent(R.id.tab4);
        spec4.setIndicator("Graficos");

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.addTab(spec4);

        // Otros Recursos (TextView, Buttons, ListView, EditText, etx)

        SiguienteID=0;


        argumentos = new String[1];

        ADX = new AlertDialog.Builder(this);
        AD = ADX.create();

        boton_nuevo_producto= (Button) findViewById(R.id.btnGuardar);
        TV2= (TextView) findViewById(R.id.TV2);
        nombre_producto= findViewById(R.id.etnombre_producto);
        descripcion_producto= findViewById(R.id.etdescripcion_producto);
       // cantidad_producto= findViewById(R.id.etCantidad);
        precio_producto= findViewById(R.id.precio_producto);
        spinnerProductos= findViewById(R.id.spinnerproductoss);

        nombre_productoEditar= findViewById(R.id.etnombre_productoEditar);
        descripcion_productoEditar= findViewById(R.id.etdescripcion_productoEditar);
        otroPrecio= findViewById(R.id.btnguardaprecio);
        guardarEditar= findViewById(R.id.btnGuardarEditar);
        tvid= findViewById(R.id.TVidproducto);

        productoslistView= findViewById(R.id.listViewproductos);

        otroPrecio.setEnabled(false);
        guardarEditar.setEnabled(false);

        // ConsultaTabla_ActualizaSpinner();
        List<Products> spinProductos= llenarProductos();
        ArrayAdapter<Products> arradap= new ArrayAdapter<Products>(Actividad_productos.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spinProductos);
        ArrayAdapter<Products> arradaplist= new ArrayAdapter<Products>(Actividad_productos.this, android.R.layout.simple_list_item_1,spinProductos);



        spinnerProductos.setAdapter(arradap);
        productoslistView.setAdapter(arradaplist);


        boton_nuevo_producto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //Si hemos abierto correctamente la base de datos



                //Si hemos abierto correctamente la base de datos
                usdbh= new ProductosSqlite(Actividad_productos.this);
                SQLiteDatabase db= usdbh.getWritableDatabase();
                if (db != null) {
                    DBProducts productsdb= new DBProducts(Actividad_productos.this);
                    DBPrices pricesdb= new DBPrices(Actividad_productos.this);

                    long id2= pricesdb.insertarNuevoPrecio(Float.parseFloat(precio_producto.getText().toString()));

                    long id= productsdb.insertarNuevoProducto(nombre_producto.getText().toString(),descripcion_producto.getText().toString());

                    if((id>0)&&(id2>0)){


                        Prices p1= pricesdb.devuelveUltimoRegistro();
                        Products p2= productsdb.devuelveUltimoRegistro();

                        DBPrices_Products dbpp= new DBPrices_Products(Actividad_productos.this);
                        System.out.println(p1.getId());
                        System.out.println(p2.getId());

                        long id3= dbpp.insertarNuevaRelacion(p2.getId(), p1.getId());

                        if(id3>0) {
                            Toast.makeText(Actividad_productos.this, "REGISTRO GUARDADO EXITOSAMENTE", Toast.LENGTH_LONG).show();

                            limpiarNuevoTicket();
                        }

                    }else {
                        Toast.makeText(Actividad_productos.this,"ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();

                    }

                }else{
                    Toast.makeText(Actividad_productos.this, "erro al conectar bd",Toast.LENGTH_LONG).show();
                }



            }
        });

        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int id_producto= ((Products) adapterView.getSelectedItem()).getId();
                String nombre_producto= ((Products) adapterView.getSelectedItem()).getNombre_producto();
                String descripcion_producto= ((Products) adapterView.getSelectedItem()).getDescription();

                tvid.setText(String.valueOf(id_producto));

                DBProducts dbp= new DBProducts(Actividad_productos.this);


                Products producto= dbp.devuelveRegistroId(id_producto);

                nombre_productoEditar.setText(producto.getNombre_producto());
                descripcion_productoEditar.setText(producto.getDescription());


                otroPrecio.setEnabled(true);
                guardarEditar.setEnabled(true);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        guardarEditar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

                DBProducts dbp= new DBProducts(Actividad_productos.this);

                boolean correcto= dbp.editarProducto(Integer.parseInt(tvid.getText().toString()),nombre_productoEditar.getText().toString(),descripcion_productoEditar.getText().toString());

                if(correcto){
                    Toast.makeText(Actividad_productos.this,"Registro modificado correctamente", Toast.LENGTH_LONG).show();
                    limpiarNuevoTicket();

                }else {
                    Toast.makeText(Actividad_productos.this,"ERROR AL modificar REGISTRO", Toast.LENGTH_LONG).show();

                }



            }
        });


        otroPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TextView text1, text2;
                EditText ETD1;
                // custom dialog
                final Dialog dialog = new Dialog(Actividad_productos.this);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Agregar nuevo precio");

                // set the custom dialog components - text, image and button
                text1 = (TextView) dialog.findViewById(R.id.textDialog1);
                text1.setText("Ingrese nuevo precio");

                text2 = (TextView) dialog.findViewById(R.id.textDialog2);
                text2.setText(tvid.getText());



                ETD1 = (EditText) dialog.findViewById(R.id.ETDialog1);



                Button dialogButton1 = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //String Cadena=ETD1.getText().toString();
                        //Toast.makeText(getApplicationContext(),"COSTO NUEVO:"+Cadena,Toast.LENGTH_SHORT).show();

                        DBPrices pricesdb= new DBPrices(Actividad_productos.this);
                        DBProducts productsdb= new DBProducts(Actividad_productos.this);

                        long id= pricesdb.insertarNuevoPrecio(Float.parseFloat(ETD1.getText().toString()));


                        if(id>0){
                           Prices p1= pricesdb.devuelveUltimoRegistro();

                            DBPrices_Products dbpp= new DBPrices_Products(Actividad_productos.this);
                            System.out.println(Integer.parseInt(text2.getText().toString()));
                            System.out.println(p1.getId());

                            long id3= dbpp.insertarNuevaRelacion(Integer.parseInt(text2.getText().toString()), p1.getId());

                            if(id3>0) {
                                Toast.makeText(Actividad_productos.this, "NUEVO PRECIO GUARDADO EXITOSAMENTE", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }


                        }




                    }
                });


                dialog.show();

            }
        });

        productoslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String texto;
                //int id_producto= ((Products) parent.getOnItemClickListener()).getId();
                String nombre_producto= ((Products) parent.getItemAtPosition(position)).toString();
                //String descripcion_producto= ((Products) adapterView.getSelectedItem()).getDescription();

                texto= nombre_producto+ "\n";
                texto+= descripcion_producto+"\n";
                texto+= "Precios existentes\n";

                System.out.println(texto);

               /* DBPrices dbp= new DBPrices(Actividad_productos.this);

                Cursor cursor= dbp.mostrarfilasprecios(id_producto);

                if(cursor!= null){
                    if (cursor.moveToFirst()){
                        do {
                            Prices p= new Prices();
                            p.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                            p.setPrice(cursor.getFloat(cursor.getColumnIndex("Price")));

                            texto+=p.getPrice().toString()+"\n";


                        }while (cursor.moveToNext());
                    }
                }

                dbp.close();

                ADX = new AlertDialog.Builder(getApplicationContext());
                AD = ADX.create();

                AD.setMessage(texto);
                AD.show();*/
            }
        });

    }


    private void limpiarNuevoTicket(){
        nombre_producto.setText("");
        descripcion_producto.setText("");
        precio_producto.setText("");
        nombre_productoEditar.setText("");
        descripcion_productoEditar.setText("");
    }

    private List<Products> llenarProductos(){

        List<Products> listProd= new ArrayList<>();

        DBProducts dbp= new DBProducts(Actividad_productos.this);

        Cursor cursor= dbp.mostrarProductos();

        if(cursor!= null){
            if (cursor.moveToFirst()){
                do {
                    Products p= new Products();
                    p.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    p.setNombre_producto(cursor.getString(cursor.getColumnIndex("Product_name")));
                    p.setDescription(cursor.getString(cursor.getColumnIndex("Description")));

                    listProd.add(p);

                }while (cursor.moveToNext());
            }
        }

        dbp.close();

        return listProd;
    }

    private List<Products> llenarProductoslistview(){


        DBProducts dbp= new DBProducts(Actividad_productos.this);

        List<Products> listProd= dbp.mostrarProductoslista();


        return listProd;
    }
    /*void ConsultaTabla_ActualizaSpinner ()
    {
        //cursor = db.rawQuery("select * from "+TABLA_PRINCIPAL, null);


        String[] queryCols=new String[]{"_id", "nombre_producto"};
        String[] adapterCols=new String[]{"nombre_producto"};
        int[] adapterRowViews=new int[]{android.R.id.text1};
        cursor=db.query(true,TABLA_PRINCIPAL, queryCols,null,null,null,null,null,null);
        sca=new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, adapterCols, adapterRowViews,0);
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(sca);
    }*/











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

