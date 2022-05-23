package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class PostEditor extends AppCompatActivity {
    public String getRetorn() {
        return retorn;
    }

    public void setRetorn(String retorn) {
        this.retorn = retorn;
    }

    String retorn ="";

    private static final String LOG_TAG ="" ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public EditText nomJoc, descJoc;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public ImageView iconGame;
    long id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_editor);



        iconGame = findViewById(R.id.iconeJoc);
        nomJoc = findViewById(R.id.etNomJoc);
        descJoc = findViewById(R.id.etDescripcioDelJoc);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fetchUserName();
        iconGame.setOnClickListener(v -> choosePicture());

    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            iconGame.setImageURI(imageUri);


            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Pujant Imatge....");
        pd.show();
        StorageReference riversRef = storageReference.child("imagesPost/" + nomJoc.getText().toString() + getRetorn());
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        // Get a URL to the uploaded content
                        Snackbar.make(findViewById(android.R.id.content), "imatge pujada", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Pujada fallida", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progresPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percent: " + (int) progresPercent + "%");
                    }
                });
    }


    private void fetchUserName() {
        db.collection("Usuaris")
                .whereEqualTo("userMail",  mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              setRetorn( document.getData().values().toArray()[2].toString());
                            }
                        } else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });


    }

    public void PujarJoc(View view) {

        Map<String, Object> post = new HashMap<>();
        post.put("id", ++id);
        post.put("nomJoc", nomJoc.getText().toString() );
        post.put("DecJoc", descJoc.getText().toString());
        post.put("NomFoto",  nomJoc.getText().toString() + getRetorn());
        post.put("NomUser",  getRetorn());
    // Add a new document with a generated ID
        db.collection("Posts").document("post " + id)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "DocumentSnapshot successfully written!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Error writing document", e);
                    }
                });

        Intent switchActivityIntent = new Intent(this, PantallaPrincipal.class);
        startActivity(switchActivityIntent);
    }



    public void PujarJocFirebase(View view) {


        CollectionReference lastDoc = db.collection("Posts");

        lastDoc.orderBy("id", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        id = (Long) document.get("id");
                        PujarJoc(view);
                    }
                } else {
                    Log.w(LOG_TAG, "Error getting documents.", task.getException());
                }

            }
        });

    }

}