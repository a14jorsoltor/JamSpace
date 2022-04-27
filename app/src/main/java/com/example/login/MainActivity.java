package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }


    public void login(View view) {
        Intent switchActivityIntent = new Intent(this, PantallaPrincipal.class);
        startActivity(switchActivityIntent);
    }
    public void registre(View view) {
        Intent switchActivityIntent = new Intent(this, PantallaRegistre.class);
        startActivity(switchActivityIntent);
    }


}