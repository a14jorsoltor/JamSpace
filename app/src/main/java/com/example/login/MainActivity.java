package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    EditText userNamein, passWordin;
    int numUser = 0;
    boolean loginUsername = false, loginPassword = false, loginSuccessfull = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Pantalla Login", Toast.LENGTH_SHORT).show();

        setUp();


    }

    private void setUp() {
        userNamein = findViewById(R.id.etUserNamein);
        passWordin = findViewById(R.id.etContrasenyain);
    }


    public void login(View view) {


        String username = userNamein.getText().toString();
        String password = passWordin.getText().toString();

        if (checkLogin(username, password)) {
            Intent switchActivityIntent = new Intent(this, PantallaPrincipal.class);
            startActivity(switchActivityIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Credencials malament", Toast.LENGTH_SHORT).show();

        }

    }

    private boolean checkLogin(String username, String password) {

        db.collection("Usuaris")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                while (!loginPassword) {
                                    System.out.println(document.getData().get("username"));
                                    numUser++;
                                    if (username.equals(document.getData().get("username"))) {
                                        loginUsername = true;
                                        System.out.println("USERNAME CORRECTO");
                                    }
                                    if (username.equals(document.get("password"))) {
                                        loginPassword = true;
                                        System.out.println("PASSWORD CORRECTO");
                                    }
                                    if (loginPassword && loginUsername) {
                                        loginSuccessfull = true;
                                    } else if (!loginUsername && !loginPassword) {
                                        loginPassword = false;
                                        loginUsername = false;
                                        loginSuccessfull = false;
                                        System.out.println("PASSWORD O USERNAME INCORRECTOS");
                                    }
                                }

                            }
                        } else {
                            loginSuccessfull = false;
                        }
                    }
                });
        return loginSuccessfull;
    }

    public void registre(View view) {
        Intent switchActivityIntent = new Intent(this, PantallaRegistre.class);
        startActivity(switchActivityIntent);
    }


}