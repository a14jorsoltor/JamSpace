package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.conexioLabs.dadesControl;
import com.example.login.conexioLabs.dadesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Edit_portfoli extends AppCompatActivity {
    private static final String LOG_TAG = "";
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    ImageView imgProfileEdit;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String nomUser, nomFoto = "", IDuser, nomJocFile="";
    dadesControl controlDades;



    public String getIDuser() {
        return IDuser;
    }

    public void setIDuser(String IDuser) {
        this.IDuser = IDuser;
    }

    EditText etNomUser, etDescripcioPortfoli;

    public String getNomFoto() {
        return nomFoto;
    }


    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_portfoli);
        fetchUserName();

        fetchID();
        Log.d(LOG_TAG,"------------------ID" + getIDuser());


        etNomUser = findViewById(R.id.etNomUser);
        etDescripcioPortfoli = findViewById(R.id.etDescripcioPortfoli);
        imgProfileEdit = findViewById(R.id.imgProfileEdit);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fetchUserName();
        imgProfileEdit.setOnClickListener(v -> choosePicture());



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
            imgProfileEdit.setImageURI(imageUri);


            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Pujant Imatge....");
        pd.show();
        StorageReference riversRef = storageReference.child("imagesUser/" + mAuth.getCurrentUser().getEmail().replace("@", "2") + "image");
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


    public void cvViewPerfil(View view) throws SQLException {
        Log.d(LOG_TAG, etNomUser.getText().toString() + " " +  etDescripcioPortfoli.getText().toString()+" " +getNomFoto().equals("") );
        if (etNomUser.getText().toString().equals(null) && etDescripcioPortfoli.getText().toString().equals(null) && getNomFoto().equals("")) {
            Toast.makeText(getApplicationContext(), "Omple els camps", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cambis guardat", Toast.LENGTH_SHORT).show();

            guardarInfo();

            Intent switchActivityIntent = new Intent(this, ViewPortfoli.class);
            startActivity(switchActivityIntent);
        }

    }

    private void guardarInfo() throws SQLException {

        Map<String, Object> usuari = new HashMap<>();
        usuari.put("descripcio", etDescripcioPortfoli.getText().toString());
        usuari.put("nomFoto", mAuth.getCurrentUser().getEmail().replace("@", "2") + "image");
        usuari.put("id", getIDuser());
        usuari.put("username", etNomUser.getText().toString());
        usuari.put("userMail", mAuth.getCurrentUser().getEmail());
        usuari.put("nomJocFile", getNomJocFile());

        // Add a new document with a generated ID
        db.collection("Usuaris" ).document("usuari " + getIDuser())
                .set(usuari)
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
        dadesModel newModel = new dadesModel(mAuth.getCurrentUser().getEmail().replace("@", "2") + "image", etDescripcioPortfoli.getText().toString(), etNomUser.getText().toString(), mAuth.getCurrentUser().getEmail(),Integer.parseInt(getIDuser()));
        //Update taules labs

        controlDades.dadesControlUp(newModel, mAuth.getCurrentUser().getEmail());
    }

    private String getNomJocFile() {
        return nomJocFile;
    }


    private void fetchUserName() {
        db.collection("Usuaris")
                .whereEqualTo("userMail", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            setNomUser(document.getData().get("username").toString());
                        }
                    } else {
                        Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                    }

                });


    }

    private void setNomJocFile(String nomJocFile) {
        this.nomJocFile = nomJocFile;
    }

    private void fetchID() {

        db.collection("Usuaris")
                .whereEqualTo("userMail", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(LOG_TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ID: "  +document.getData().get("id"));
                            Log.d(LOG_TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>descripcio: "  +document.getData().get("descripcio"));
                            Log.d(LOG_TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>nomfoto: "  +document.getData().get("nomFoto"));
                            Log.d(LOG_TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>usermail: "  +document.getData().get("usermail"));
                            Log.d(LOG_TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>username: "  +document.getData().get("username"));
                            setIDuser(document.getData().get("id").toString());
                        }
                    } else {
                        Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                    }

                });


    }




}

