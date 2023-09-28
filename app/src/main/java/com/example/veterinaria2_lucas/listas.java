package com.example.veterinaria2_lucas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class listas extends AppCompatActivity {

    ConexionSQLiteHelper conexion;
    ListView lvRegistroMascota;

    ArrayList<String> listaInformacion;

    ArrayList<Mascota> listaMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        conexion = new ConexionSQLiteHelper(getApplicationContext(),"dbveterinaria",null,1);

        loadUI();

        //Obtener datos
        ConsultarListaMascotas();


        //Creamos el adaptador (puente)
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);

        //Asginamos el adapatador al Listview
        lvRegistroMascota.setAdapter(adaptador);

        lvRegistroMascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mensaje = "";

                //i  = posición

                mensaje += "Peso: " + listaMascota.get(i).getPeso() + "\n"; //"\n" => salto de línea
                mensaje += "Color: " + listaMascota.get(i).getColor();
                Toast.makeText(listas.this,mensaje, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void ConsultarListaMascotas(){
        //Permisos
        SQLiteDatabase db = conexion.getReadableDatabase();

        //Iniciando el objeto mascota
        Mascota mascota = null;

        //Construir nuestra colección de mascotas
        listaMascota = new ArrayList<Mascota>();

        //Consulta a la base de datos

        Cursor cursor =db.rawQuery("SELECT * FROM mascotas",null);

        //Recorrer el cursor(resultado de los registros)
        while(cursor.moveToNext()){

            //Guardar el valor obtenido dentro de un obejeto mascota
            mascota = new Mascota();

            mascota.setIdmascota(cursor.getInt(0));
            mascota.setNombre(cursor.getString(1));
            mascota.setTipo(cursor.getString(2));
            mascota.setRaza(cursor.getString(3));
            mascota.setPeso(cursor.getDouble(4));
            mascota.setColor(cursor.getString(5));

            //Agregar el objeto Persona a la colección ArrayList<Mascota>
            listaMascota.add(mascota);
        }

        //Invocamos al método
        obtenerLista();

    }
    private void obtenerLista(){
        //Construimos una lista con los datos a mostrar
        listaInformacion = new ArrayList<String>();

        for(int i = 0; i < listaMascota.size();i++){
            listaInformacion.add(listaMascota.get(i).getNombre().toUpperCase() + "\n" + listaMascota.get(i).getTipo() + "\n" + listaMascota.get(i).getRaza());
        }
    }

    private void loadUI(){
        lvRegistroMascota = findViewById(R.id.lvRegistroMascota);
    }
}