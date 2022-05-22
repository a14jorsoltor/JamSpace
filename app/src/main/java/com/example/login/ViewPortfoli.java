package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ViewPortfoli extends AppCompatActivity {
    String nomFoto;

    public String getNomFoto() {
        return nomFoto;
    }

    public void setNomFoto(String nomFoto) {
        this.nomFoto = nomFoto;
    }

    ImageView imgProfileEdit;
    TextView tvNomUserView, tvDescripcioView;
    private static final String LOG_TAG ="" ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef =  storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_portfoli);
        
        setUp();
        posarDades();
    }

    private void posarDades() {
        fetchUserName();
    }


    private void fetchUserName() {

        db.collection("Usuaris")
                .whereEqualTo("usermail",  mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tvNomUserView.setText(document.getData().get("username").toString());
                                tvDescripcioView.setText(document.getData().get("descripcio").toString());
                                setNomFoto(document.getData().get("nomFoto").toString());





                            }

                            ficarFoto();




                        } else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });


    }
    private void ficarFoto(){
        String nomFoto = getNomFoto();

        storageRef.child("imagesUser/"+nomFoto).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imgProfileEdit);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });    }


    private void setUp() {
        imgProfileEdit = findViewById(R.id.imgProfileView);
        tvNomUserView = findViewById(R.id.tvNomUserView);
        tvDescripcioView = findViewById(R.id.tvDescripcioView);

    }

    public void cvEditPerfil(View view) {
        Intent switchActivityIntent = new Intent(this, Edit_portfoli.class);
        startActivity(switchActivityIntent);

    }
}