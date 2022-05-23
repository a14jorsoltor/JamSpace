package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {
    TextView nomUser;
    ImageView fotoUser;
    private RecyclerView mRecyclerView;
    private static WordListAdapter mAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef =  storage.getReference();

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

    private void cargarPortfoli() {
        fetchUserName();
    }

    private void fetchUserName() {

        nomUser = findViewById(R.id.nomCurrUserPPsdf);
        fotoUser = findViewById(R.id.fotoPerfilPP);
        db.collection("Usuaris")
                .whereEqualTo("usermail",  mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                nomUser.setText(document.getData().get("username").toString());
                                ficarFoto(document.getData().get("nomFoto").toString());
                            }
                        }

                    }
                });


    }


    private void ficarFoto(String nomFoto){

        storageRef.child("imagesUser/"+nomFoto).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(fotoUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void cvPortfoli(View view) {
        Intent switchActivityIntent = new Intent(this, ViewPortfoli.class);
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


                            cargarPortfoli();
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