package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText userMailin, passWordin;
    boolean loginSuccessfull = false;
    private FirebaseAuth mAuth;
    private static final String LOG_TAG = null;

    public boolean isLoginSuccessfull() {
        return loginSuccessfull;
    }

    public void setLoginSuccessfull(boolean loginSuccessfull) {
        this.loginSuccessfull = loginSuccessfull;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Pantalla Login", Toast.LENGTH_SHORT).show();

        setUp();
    }

    private void setUp() {
        mAuth = FirebaseAuth.getInstance();
        userMailin = findViewById(R.id.etCorreuLogin);
        passWordin = findViewById(R.id.etContrasenyain);
    }


    public void login(View view) {
        if (!userMailin.getText().toString().equals("") || !passWordin.getText().toString().equals("")) {
            Log.d(LOG_TAG, "CORREU: " + userMailin.getText().toString() + " || " + "PASSWORD: " + passWordin.getText().toString());

            mAuth.signInWithEmailAndPassword(userMailin.getText().toString(), passWordin.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(LOG_TAG, "signInWithEmail:success");


                            Intent switchActivityIntent = new Intent(this, PantallaPrincipal.class);
                            startActivity(switchActivityIntent);

                        } else {
                            // If sign in fails, display a message to the user.
                            setLoginSuccessfull(false);
                            Toast.makeText(getApplicationContext(), "Credencials malament", Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Log.d(LOG_TAG, "NI CORREU NI PWD NO TE RES");
        }
    }



    /**
     * Funcio per verue si les credencials coisidiexen amb les de la base de dades.
     * @param correu correu que pasa l'usuari
     * @param password pase que pasa l'usuari
     * @return retornem si esta be o malament .
     */
    private void checkLogin(String correu, String password) {
        Log.d(LOG_TAG, "CORREU: "+ correu +" || " + "PASSWORD" + password);



    }

    /**
     * Canvi de pantalla a la de registre
     * @param view
     */
    public void registre(View view) {
        Intent switchActivityIntent = new Intent(this, PantallaRegistre.class);
        startActivity(switchActivityIntent);
    }



}