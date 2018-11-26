package com.vagner.tocaaquela.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vagner.tocaaquela.R;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginArtistaActivityTeste extends AppCompatActivity implements View.OnClickListener {

        private EditText editTextEmail;
        private EditText editTextPassword;
        private EditText editTextName;
        private Button buttonSignup;

        private TextView textViewSignin;

        private ProgressDialog progressDialog;

        DatabaseReference databaseArtists;



        private FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_artista_teste);
            databaseArtists = FirebaseDatabase.getInstance().getReference("Artistas");


            firebaseAuth = FirebaseAuth.getInstance();

            if(firebaseAuth.getCurrentUser() != null){

                finish();


                startActivity(new Intent(getApplicationContext(), MenuArtistaActivity.class));
            }


            editTextName = (EditText)findViewById(R.id.editTextName_login_artista_teste);
            editTextEmail = (EditText) findViewById(R.id.editTextEmail_login_artista_teste);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword_login_artista_teste);
            textViewSignin = (TextView) findViewById(R.id.textViewSignin_login_artista_teste);

            buttonSignup = (Button) findViewById(R.id.buttonSignup_login_artista_teste);


            progressDialog = new ProgressDialog(this);


            buttonSignup.setOnClickListener(this);
            textViewSignin.setOnClickListener(this);
        }

        private void registerUser(){


            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password  = editTextPassword.getText().toString().trim();



            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"Por favor digite seu nome",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Por favor digite o email",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Por favor digite a senha",Toast.LENGTH_LONG).show();
                return;
            }



            progressDialog.setMessage("Registrando...");
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), MenuArtistaActivity.class));
                            }else{
                                Toast.makeText(LoginArtistaActivityTeste.this,"Deu errado",Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }

        @Override
        public void onClick(View view) {

            if(view == buttonSignup){
                registerUser();
            }

            if(view == textViewSignin){
                startActivity(new Intent(this, LoginActivity.class));
            }

        }




}



