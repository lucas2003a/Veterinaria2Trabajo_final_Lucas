package com.example.veterinaria2_lucas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class buscar_mascotas extends AppCompatActivity {
    ConexionSQLiteHelper    conexion;
    EditText etBuscarNombreMascota, etBuscarTipoMascota, etBuscarRazaMascota, etBuscarPesoMascota, etBuscarColorMascota, etBuscarMascotaID;

    Button btnBuscarMascota, btnLimpiarMascota, btnActualizarMascota, btnEliminarMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mascotas);

        conexion = new ConexionSQLiteHelper(getApplicationContext(),"dbveterinaria",null,1);

        loadUI();

        btnBuscarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarMascota();
            }
        });

        btnLimpiarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciar();
            }
        });

        btnActualizarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        btnEliminarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarMascota();
            }
        });
    }
    private void eliminarMascota(){
        try{
            SQLiteDatabase db = conexion.getWritableDatabase();

            String idMascotaEliminar = etBuscarMascotaID.getText().toString();

            String WhereClause = "idmascota=?";
            String[] WhereArgs = {idMascotaEliminar};

            int filaEliminada = db.delete("mascotas",WhereClause,WhereArgs);

            if(filaEliminada > 0){
                notificar("Registro eliminado correctamente");
                reiniciar();
            }else{
                notificar("El registro no se ha eliminado");
            }
            reiniciar();
        }
        catch(Exception e){
            notificar("Error al eliminar");
        }

    }

    private void validarCampos(){

        String nombre,tipo,raza,color;
        double peso;

        nombre  = etBuscarNombreMascota.getText().toString();
        tipo    = etBuscarTipoMascota.getText().toString();
        raza    = etBuscarRazaMascota.getText().toString();
        color   = etBuscarColorMascota.getText().toString();

        peso = (etBuscarPesoMascota.getText().toString().trim().isEmpty()) ? 0 : Double.parseDouble(etBuscarPesoMascota.getText().toString());

        if(nombre.isEmpty() || tipo.isEmpty() || raza.isEmpty() || color.isEmpty() || peso == 0.0){
            notificar("campos vacios");
        }else{
            preguntar();
        }
    }

    private void notificar(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VeterinariaPET");
        dialogo.setMessage("¿Seguro de guardar los cambios?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actualizarMascota();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogo.show();
    }

    private void actualizarMascota(){

        SQLiteDatabase db = conexion.getWritableDatabase();

        //OBJETO QUE CONTENGA LOS VALORES

        ContentValues valores = new ContentValues();

        valores.put("nombre",etBuscarNombreMascota.getText().toString());
        valores.put("tipo",etBuscarTipoMascota.getText().toString());
        valores.put("raza",etBuscarRazaMascota.getText().toString());
        valores.put("peso",etBuscarPesoMascota.getText().toString());
        valores.put("color",etBuscarColorMascota.getText().toString());

        //Definir la cláuse WHERE para identificar el registro a actualizar
        String WhereClause = "idmascota=?";
        String[] WhereArgs = {etBuscarMascotaID.getText().toString()};

        int filaActualizar = db.update("mascotas",valores,WhereClause,WhereArgs);

        if(filaActualizar > 0){
            notificar("Mascota actualizada correctamente");
        }else{
            notificar("No se puede actualizar la mascota");
        }
    }

    private void reiniciar(){
        etBuscarMascotaID.setText(null);
        etBuscarNombreMascota.setText(null);
        etBuscarTipoMascota.setText(null);
        etBuscarRazaMascota.setText(null);
        etBuscarPesoMascota.setText(null);
        etBuscarColorMascota.setText(null);
    }
    private void buscarMascota(){
        //Permiso
        SQLiteDatabase db = conexion.getReadableDatabase();

        //arreglo con los datos a buscar
        String[] camposCriterio = {etBuscarMascotaID.getText().toString()};

        //campos a obtener(retorno)
        String[] campos = {"nombre","tipo","raza","peso","color"};

        //exepxiones
        try{
            //ejecutar la consulta
            Cursor cursor = db.query("mascotas",campos,"idmascota=?",camposCriterio,null,null,null);
            cursor.moveToFirst();

            //cursor envia la informacion a las cajas
            etBuscarNombreMascota.setText(cursor.getString(0));
            etBuscarTipoMascota.setText(cursor.getString(1));
            etBuscarRazaMascota.setText(cursor.getString(2));
            etBuscarPesoMascota.setText(cursor.getString(3));
            etBuscarColorMascota.setText(cursor.getString(4));
            //terminar ..

            //cerrar el cursor
            cursor.close();

        }catch (Exception e){
            Toast.makeText(this,"Error en la consuta", Toast.LENGTH_LONG).show();
            reiniciar();
        }
    }

    private void loadUI(){
        etBuscarNombreMascota   = findViewById(R.id.etBuscarNombreMascota);
        etBuscarTipoMascota     = findViewById(R.id.etBuscarTipoMascota);
        etBuscarRazaMascota     = findViewById(R.id.etBuscarRazaMascota);
        etBuscarPesoMascota     = findViewById(R.id.etBuscarPesoMascota);
        etBuscarColorMascota    = findViewById(R.id.etBuscarColorMascota);
        etBuscarMascotaID       = findViewById(R.id.etBuscarMascotaID);

        btnBuscarMascota        = findViewById(R.id.btnBuscarMascota);
        btnLimpiarMascota       = findViewById(R.id.btnLimpiarMascota);
        btnActualizarMascota    = findViewById(R.id.btnActualizarMascota);
        btnEliminarMascota      = findViewById(R.id.btnEliminarMascota
        );
    }
}