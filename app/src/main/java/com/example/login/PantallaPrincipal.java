package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Toast.makeText(getApplicationContext(),"Pantalla principal",Toast.LENGTH_SHORT).show();

    }




    public void cvPortfoli(View view) {
        Intent switchActivityIntent = new Intent(this, Portfoli.class);
        startActivity(switchActivityIntent);
    }

    public void cvEditPost(View view) {
        Intent switchActivityIntent = new Intent(this, PostEditor.class);
        startActivity(switchActivityIntent);
    }
}