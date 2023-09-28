package com.example.veterinaria2_lucas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class buscar_clientes extends AppCompatActivity {

    ConexionSQLiteHelper conexion;
    EditText etBuscarIdCliente, etBuscarNombreCliente, etBuscarApellidoCliente, etBuscarTelefonoCliente, etBuscarEmailCliente, etBuscarDireccionCLiente, etBuscarFechaNacCliente;

    Button btnBuscarCliente, btnLimpiarCliente, btnActualizarCliente, btnEliminarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_clientes);

        conexion = new ConexionSQLiteHelper(getApplicationContext(),"dbveterinaria",null,1);



        loadUI();

        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarCliente();
            }
        });

        btnLimpiarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarCajas();
            }
        });

        btnActualizarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        btnEliminarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarCliente();
            }
        });
    }

    private void eliminarCliente(){
        try {
            SQLiteDatabase db = conexion.getWritableDatabase();

            String IdClienteEliminar = etBuscarIdCliente.getText().toString();

            String WhereClause = "idcliente=?";
            String[] WhereArgs = {IdClienteEliminar};

            int filaActualizada = db.delete("clientes", WhereClause, WhereArgs);

            if(filaActualizada >0){
                notificacion("Registro eliminado corrrectamente");
                limpiarCajas();
            }else{
                notificacion("El registro no se ha eliminado");
            }


        }catch(Exception e) {
            notificacion("Error al eliminar");
            limpiarCajas();
        }
    }
    private void validarCampos(){
        String apellidos, nombres, telefono, email, direccion, fechaNac;

        apellidos   = etBuscarApellidoCliente.getText().toString();
        nombres     = etBuscarNombreCliente.getText().toString();
        telefono    = etBuscarTelefonoCliente.getText().toString();
        email       = etBuscarEmailCliente.getText().toString();
        direccion   = etBuscarDireccionCLiente.getText().toString();
        fechaNac    = etBuscarFechaNacCliente.getText().toString();

        if(apellidos.isEmpty() || nombres.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || fechaNac.isEmpty()){
            notificacion("Llene todos los campos");
        }else{
            preguntar();
        }
    }

    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VeterinariaPET");
        dialogo.setMessage("¿Desea guardar los cambios?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actualizarCliente();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogo.show();
    }

    private void actualizarCliente(){
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues parametros = new ContentValues();

        parametros.put("apellidos",etBuscarApellidoCliente.getText().toString());
        parametros.put("nombres",etBuscarNombreCliente.getText().toString());
        parametros.put("telefono",etBuscarTelefonoCliente.getText().toString());
        parametros.put("email",etBuscarEmailCliente.getText().toString());
        parametros.put("fecha_nacimiento",etBuscarFechaNacCliente.getText().toString());

        //Identificamos el registro a actualizar
        String WhereClause = "idcliente=?";
        String[] WhereArgs = {etBuscarIdCliente.getText().toString()};

        int filaActualizar = db.update("clientes",parametros,WhereClause,WhereArgs);

        if(filaActualizar >0){
            notificacion("Cliente actualizado corrrectamenta");
        }else{
            notificacion("No se puede actualizar el cliente");
        }
    }
    private void limpiarCajas(){
        etBuscarIdCliente.setText(null);
        etBuscarApellidoCliente.setText(null);
        etBuscarNombreCliente.setText(null);
        etBuscarTelefonoCliente.setText(null);
        etBuscarEmailCliente.setText(null);
        etBuscarDireccionCLiente.setText(null);
        etBuscarFechaNacCliente.setText(null);
    }

    private void buscarCliente(){

        SQLiteDatabase db = conexion.getReadableDatabase();

        String[] CampoCriterio = {etBuscarIdCliente.getText().toString()};

        String[] campos = {"apellidos","nombres","telefono","email","direccion","fecha_nacimiento"};

        try{
            Cursor cursor = db.query("clientes",campos,"idcliente=?",CampoCriterio,null,null,null);
            cursor.moveToFirst();

            etBuscarApellidoCliente.setText(cursor.getString(0));
            etBuscarNombreCliente.setText(cursor.getString(1));
            etBuscarTelefonoCliente.setText(cursor.getString(2));
            etBuscarEmailCliente.setText(cursor.getString(3));
            etBuscarDireccionCLiente.setText(cursor.getString(4));
            etBuscarFechaNacCliente.setText(cursor.getString(5));

            cursor.close();

        }catch(Exception e) {
            notificacion("Error al iniciar busqueda");
            limpiarCajas();
        }
    }

    private void notificacion(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

    private void loadUI(){

        etBuscarIdCliente = findViewById(R.id.etBuscarIdCliente);
        etBuscarNombreCliente = findViewById(R.id.etBuscarNombreCliente);
        etBuscarApellidoCliente = findViewById(R.id.etBuscarApellidoCliente);
        etBuscarTelefonoCliente = findViewById(R.id.etBuscarTelefonoCliente);
        etBuscarEmailCliente = findViewById(R.id.etBuscarEmailCliente);
        etBuscarDireccionCLiente = findViewById(R.id.etBuscarDireccionCliente);
        etBuscarFechaNacCliente = findViewById(R.id.etBuscarFechaNacimientoCliente);

        btnBuscarCliente = findViewById(R.id.btnBuscarCliente);
        btnLimpiarCliente = findViewById(R.id.btnLimpiarCliente);
        btnActualizarCliente = findViewById(R.id.btnActualizarCliente);
        btnEliminarCliente = findViewById(R.id.btnEliminarCliente);

    }
}