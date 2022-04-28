package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PantallaRegistre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registre);
        Toast.makeText(getApplicationContext(),"Pantalla registre",Toast.LENGTH_SHORT).show();


    }

    public void returnLogin(View view) {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    public void Register(View view) {
        //Aqui van guardar les dades en el firebase
    }
}