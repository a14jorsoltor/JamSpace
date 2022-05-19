package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private static WordListAdapter mAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int numPosts =0 ;
    String DecJoc, NomFoto, NomUser , nomJoc;
    int id;
    ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantalla_principal);
        Toast.makeText(getApplicationContext(),"Pantalla principal",Toast.LENGTH_SHORT).show();

        GeneratePosts(this);


    }



    public void cvPortfoli(View view) {
        Intent switchActivityIntent = new Intent(this, Portfoli.class);
        startActivity(switchActivityIntent);
    }

    public void cvEditPost(View view) {
        Intent switchActivityIntent = new Intent(this, PostEditor.class);
        startActivity(switchActivityIntent);
    }

    public void GeneratePosts(Context context){
        numPosts = 0;
        posts.clear();
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DecJoc = document.get("DecJoc").toString() ;
                                NomFoto =document.get("NomFoto").toString() ;
                                NomUser =document.get("NomUser").toString() ;
                                id = Integer.parseInt(document.get("id").toString());
                                nomJoc=document.get("nomJoc").toString() ;
                                Post post = new Post(DecJoc, NomFoto,NomUser,id,nomJoc );
                                posts.add(post);
                                numPosts++;
                            }
                            // Get a handle to the RecyclerView.
                            mRecyclerView = findViewById(R.id.recyclerview);
                            // Create an adapter and supply the data to be displayed.
                            mAdapter = new WordListAdapter(context, posts);
                            // Connect the adapter with the RecyclerView.
                            mRecyclerView.setAdapter(mAdapter);
                            // Give the RecyclerView a default layout manager.
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {

                        }
                    }
                });

    }




}