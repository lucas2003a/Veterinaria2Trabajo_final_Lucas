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

public class registrar_mascotas extends AppCompatActivity {
    EditText etNombreMascota, etTipoMascota, etRazaMascota, etPesoMascota, etColorMascota;

    Button btnRegistrarMascota, btnAbrirBusquedaMascota, btnAbrirListaMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascotas);

        loadUI();

        btnRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarCampos();
            }
        });

        btnAbrirBusquedaMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), buscar_mascotas.class));
            }
        });

        btnAbrirListaMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), listas.class));
            }
        });
    }

    private void ValidarCampos(){

        String nombre, tipo, raza, color;
        double peso;

        nombre  = etNombreMascota.getText().toString();
        tipo    = etTipoMascota.getText().toString();
        raza    = etRazaMascota.getText().toString();
        color   = etColorMascota.getText().toString();

        peso =(etPesoMascota.getText().toString().trim().isEmpty()) ? 0.0 : Double.parseDouble(etPesoMascota.getText().toString());

        if(nombre.isEmpty() || tipo.isEmpty() || raza.isEmpty() || color.isEmpty() || peso == 0.0){
            notificar("Completar formulario");
        }else{
            preguntar();
        }
    }
    private void preguntar(){
        AlertDialog.Builder dialogo =new AlertDialog.Builder(this);
        dialogo.setTitle("VeterinariaPET");
        dialogo.setMessage("¿Seguro de guardar el registro?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarMascota();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogo.show();
    }

    private void registrarMascota(){
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"dbveterinaria",null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues parametros = new ContentValues();

        parametros.put("nombre",etNombreMascota.getText().toString());
        parametros.put("tipo",etTipoMascota.getText().toString());
        parametros.put("raza",etRazaMascota.getText().toString());
        parametros.put("peso",etPesoMascota.getText().toString());
        parametros.put("color",etColorMascota.getText().toString());

        long idobtenido = db.insert("mascotas","idmascota",parametros);
        notificar("Datos guardados correctamente - " + String.valueOf(idobtenido));
        reiniciar();
        etNombreMascota.requestFocus();
    }

    private void reiniciar(){
        etNombreMascota.setText(null);
        etTipoMascota.setText(null);
        etRazaMascota.setText(null);
        etPesoMascota.setText(null);
        etColorMascota.setText(null);
    }
    private void notificar(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
    private void loadUI(){

        etNombreMascota = findViewById(R.id.etNombreMascota);
        etTipoMascota   = findViewById(R.id.etTipoMascota);
        etRazaMascota   = findViewById(R.id.etRazaMascota);
        etPesoMascota   = findViewById(R.id.etPesoMascota);
        etColorMascota  = findViewById(R.id.etColorMascota);

        btnRegistrarMascota = findViewById(R.id.btnRegistrarMascota);
        btnAbrirBusquedaMascota = findViewById(R.id.btnAbrirBusquedaMascota);
        btnAbrirListaMascota    = findViewById(R.id.btnAbrirListaMascota);
    }
}