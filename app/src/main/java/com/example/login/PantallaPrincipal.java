package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private static WordListAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         String[] posts = new String[20];

        setContentView(R.layout.activity_pantalla_principal);
        Toast.makeText(getApplicationContext(),"Pantalla principal",Toast.LENGTH_SHORT).show();

        for (int i = 0 ;i <20; i++){
            posts[i] = ("Post: " + i);
        }
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, posts);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



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