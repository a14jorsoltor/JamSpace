package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;


public class PantallaRegistre extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private boolean goodCredentials = false;



    private static final String LOG_TAG = null;
    long id = 0;
    TextView msgError;
    EditText nomUsuari, correuUsuari, pswUsuari;

    public boolean isGoodCredentials() {
        return goodCredentials;
    }

    public void setGoodCredentials(boolean goodCredentials) {
        this.goodCredentials = goodCredentials;
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registre);
        Toast.makeText(getApplicationContext(), "Pantalla registre", Toast.LENGTH_SHORT).show();
        setUp();

    }

    private void setUp() {

        mAuth = FirebaseAuth.getInstance();
        msgError = findViewById(R.id.tvMsgError);
        nomUsuari = findViewById(R.id.etNomReg);
        correuUsuari = findViewById(R.id.etCorreuReg);
        pswUsuari = findViewById(R.id.etContrasenyaReg);
    }

    /**
     * Funcio per retornar a la pantalla login
     * @param view
     */
    public void returnLogin(View view) {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }


    /**
     * Funcio per fe el registre de la persona
     * @param view
     */
    public void Register(View view) {

        if (nomUsuari.getText().toString().equals("") || correuUsuari.getText().toString().equals("") || pswUsuari.getText().toString().equals("")) {
            msgError.setText("");
            if (nomUsuari.getText().toString().equals("")) {
                msgError.setText("Has de ficiar un nom d'usuari\n");
            }
            if (correuUsuari.getText().toString().equals("")) {
                msgError.setText(msgError.getText() + "Has de ficiar un correu\n");
            }
            if (pswUsuari.getText().toString().equals("")) {
                msgError.setText(msgError.getText() + "Has de ficiar una contrasenya\n");
            }

        } else {

            authenticarUsuari(correuUsuari.getText().toString(), pswUsuari.getText().toString());


            getIdDoc(db, "id");


        }

    }

    /**
     * Aquesta funcio mira si s'han ficat be les credencials, i si es poden ficar be al firebase
     * @param email email que li pasem a partir del que fica  l'usuari
     * @param password password que li passa a partir de que fica l'usuari
     */
    private void authenticarUsuari(String email, String password) {


        if (password.length() < 6 && (email.equals("") || !email.contains("@"))) {
            Toast.makeText(PantallaRegistre.this, "Credencials fallidas",
                    Toast.LENGTH_SHORT).show();
        } else {


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(LOG_TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                setGoodCredentials(true);
                                canviPantallaLogin(isGoodCredentials());
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(PantallaRegistre.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                setGoodCredentials(false);

                            }


                        }
                    });
        }
    }


    private void canviPantallaLogin(boolean goodCredentials) {
        if(goodCredentials){
            Intent switchActivityIntent = new Intent(this, PantallaPrincipal.class);
            startActivity(switchActivityIntent);
        }
        else {
                Toast.makeText(PantallaRegistre.this, "Credencials errones",
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Funcio per posar una id al seguent fitxer que creem en la base de dades.
     * @param db
     * @param idNom
     */
    public void getIdDoc(FirebaseFirestore db, String idNom) {


        CollectionReference lastDoc = db.collection("Usuaris");



        lastDoc.orderBy("id", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        id = (Long) document.get("id");

                        Map<String, Object> usuari = new HashMap<>();
                        usuari.put("descripcio", "");
                        usuari.put("nomFoto", "");
                        usuari.put("id", ++id);
                        usuari.put("username", nomUsuari.getText().toString());
                        usuari.put("userMail", correuUsuari.getText().toString());
                        // Add a new document with a generated ID
                        db.collection("Usuaris").document("usuari " + id)
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
                    }
                } else {
                    Log.w(LOG_TAG, "Error getting documents.", task.getException());
                }

            }
        });

    }


}