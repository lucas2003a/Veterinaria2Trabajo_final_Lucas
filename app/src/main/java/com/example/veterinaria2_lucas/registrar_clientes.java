package com.example.veterinaria2_lucas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registrar_clientes extends AppCompatActivity {

    EditText etApellidoCliente, etNombreCliente, etTelefonoCliente, etEmailCliente, etDireccionCliente,etFechaNacimientoCliente;

    Button btnRegistrarCliente, btnAbrirBusquedaCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_clientes);


        loadUI();

        btnRegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        btnAbrirBusquedaCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), buscar_clientes.class));
            }
        });
    }

    private void validarCampos(){

        String apellidos, nombres, telefono, email, direccion, fechaNac;

        apellidos   = etApellidoCliente.getText().toString();
        nombres     = etNombreCliente.getText().toString();
        telefono    = etTelefonoCliente.getText().toString();
        email       = etEmailCliente.getText().toString();
        direccion   = etDireccionCliente.getText().toString();
        fechaNac    = etFechaNacimientoCliente.getText().toString();

        if(apellidos.isEmpty() || nombres.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || fechaNac.isEmpty()){
            notificar("Llene todos los campos");
        }else{
            preguntar();
        }

    }

    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VeterinariaPET");
        dialogo.setMessage("¿Desea registrar al cliente?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarCliente();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    private void registrarCliente(){
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"dbveterinaria",null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues parametros = new ContentValues();

        parametros.put("apellidos",etApellidoCliente.getText().toString());
        parametros.put("nombres",etNombreCliente.getText().toString());
        parametros.put("telefono",etTelefonoCliente.getText().toString());
        parametros.put("email",etEmailCliente.getText().toString());
        parametros.put("direccion",etDireccionCliente.getText().toString());
        parametros.put("fecha_nacimiento",etFechaNacimientoCliente.getText().toString());

        long idobtenido = db.insert("clientes","idcliente",parametros);
        notificar("Datos guardado correctamente - " + String.valueOf(idobtenido));
        reiniciar();
        etApellidoCliente.requestFocus();
    }

    private void reiniciar(){
        etApellidoCliente.setText(null);
        etNombreCliente.setText(null);
        etTelefonoCliente.setText(null);
        etEmailCliente.setText(null);
        etDireccionCliente.setText(null);
        etFechaNacimientoCliente.setText(null);
    }

    private void notificar(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

    private void loadUI(){
        etApellidoCliente   = findViewById(R.id.etApellidoCliente);
        etNombreCliente     = findViewById(R.id.etNombreCliente);
        etTelefonoCliente   = findViewById(R.id.etTelefonoCliente);
        etEmailCliente      = findViewById(R.id.etEmailCliente);
        etDireccionCliente  = findViewById(R.id.etDireccionCliente);
        etFechaNacimientoCliente = findViewById(R.id.etFechaNacimientoCliente);

        btnRegistrarCliente = findViewById(R.id.btnRegistrarCliente);
        btnAbrirBusquedaCli = findViewById(R.id.btnAbrirBusquedaCli);
    }

}