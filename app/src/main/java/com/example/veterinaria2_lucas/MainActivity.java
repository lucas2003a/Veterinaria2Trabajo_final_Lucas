package com.example.veterinaria2_lucas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAccederMascotas, btnAccederClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUI();

        btnAccederMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), registrar_mascotas.class));
            }
        });

        btnAccederClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), registrar_clientes.class));
            }
        });
    }

    private void loadUI(){

        btnAccederMascotas = findViewById(R.id.btnAccederMascotas);
        btnAccederClientes = findViewById(R.id.btnAccederClientes);
    }
}