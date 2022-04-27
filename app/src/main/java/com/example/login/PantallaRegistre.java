package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class PantallaRegistre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registre);
        Toast.makeText(getApplicationContext(),"Pantalla registre",Toast.LENGTH_SHORT).show();


    }
}