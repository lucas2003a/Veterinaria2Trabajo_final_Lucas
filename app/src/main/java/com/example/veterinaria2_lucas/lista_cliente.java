package com.example.veterinaria2_lucas;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class lista_cliente extends AppCompatActivity {

    ConexionSQLiteHelper conexion;
    ListView lvRegistroCliente;
    ArrayList<String> listaInformacion;
    ArrayList<Cliente> listaCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);

        conexion = new ConexionSQLiteHelper(getApplicationContext(),"dbveterinaria",null,1);

        loadUI();

        //Obtener datos

        consultarDatos();

        //Creamos el adaptador(puente)
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);

        //Assigamos el adaptador al ListView

        lvRegistroCliente.setAdapter(adaptador);

        lvRegistroCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mensaje = "";

                mensaje += "Teléfono: " +listaCliente.get(i).getTelefono() +"\n";
                mensaje += "Fecha de nacimiento: "+listaCliente.get(i).getFecha_nacimiento();
                Toast.makeText(lista_cliente.this,mensaje,Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void consultarDatos(){
        //Permisos
        SQLiteDatabase db = conexion.getReadableDatabase();

        //Iniciando el objeto cliente
        Cliente cliente = null;

        //Construir nueva colección de clientes
        listaCliente = new ArrayList<Cliente>();

        //Consulta en la base de datos
        Cursor cursor = db.rawQuery("SELECT * FROM clientes",null);

        //Recorrer el cursor(resultado de los registros)
        while(cursor.moveToNext()){

            //Guardar un valor obtenido dentro de un objeto cliente
            cliente = new Cliente();

            cliente.setIdcliente(cursor.getInt(0));
            cliente.setApellidos(cursor.getString(1));
            cliente.setNombres(cursor.getString(2));
            cliente.setTelefono(cursor.getString(3));
            cliente.setEmail(cursor.getString(4));
            cliente.setDireccion(cursor.getString(5));
            cliente.setFecha_nacimiento(cursor.getString(6));

            listaCliente.add(cliente);
        }

        obtenerLista();
    }

    private void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for(int i = 0; i < listaCliente.size();++i){
            listaInformacion.add(listaCliente.get(i).getApellidos().toUpperCase() + " " +listaCliente.get(i).getNombres());
        }
    }

    private void loadUI(){
        lvRegistroCliente = findViewById(R.id.lvRegistroCliente);
    }
}