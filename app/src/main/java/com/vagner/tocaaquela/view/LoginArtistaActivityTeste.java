package com.vagner.tocaaquela.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vagner.tocaaquela.R;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.vagner.tocaaquela.model.Artista;

public class LoginArtistaActivityTeste extends AppCompatActivity implements View.OnClickListener {

        //defining view objects
        private EditText editTextEmail;
        private EditText editTextPassword;
        private EditText editTextName;
        private Button buttonSignup;

        private TextView textViewSignin;

        private ProgressDialog progressDialog;


        //defining firebaseauth object
        private FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_artista_teste);
            databaseReference = FirebaseDatabase.getInstance().getReference("artistas");

            //initializing firebase auth object
            firebaseAuth = FirebaseAuth.getInstance();

            //if getCurrentUser does not returns null
            if(firebaseAuth.getCurrentUser() != null){
                //that means user is already logged in
                //so close this activity
                finish();

                //and open profile activity
                startActivity(new Intent(getApplicationContext(), MenuArtistaActivity.class));
            }

            //initializing views
            editTextName = (EditText)findViewById(R.id.editTextName_login_artista_teste);
            editTextEmail = (EditText) findViewById(R.id.editTextEmail_login_artista_teste);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword_login_artista_teste);
            textViewSignin = (TextView) findViewById(R.id.textViewSignin_login_artista_teste);

            buttonSignup = (Button) findViewById(R.id.buttonSignup_login_artista_teste);


            progressDialog = new ProgressDialog(this);

            //attaching listener to button
            buttonSignup.setOnClickListener(this);
            textViewSignin.setOnClickListener(this);
        }

        private void registerUser(){

            //getting email and password from edit texts
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password  = editTextPassword.getText().toString().trim();


            //checking if email and passwords are empty
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

            //if the email and password are not empty
            //displaying a progress dialog

            progressDialog.setMessage("Aguarde por favor...");
            progressDialog.show();

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){
                                adicionaArtistas();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MenuArtistaActivity.class));
                            }else{
                                //display some message here
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
                //open login activity when user taps on the already registered textview
                startActivity(new Intent(this, LoginActivity.class));
            }

        }

        public void adicionaArtistas(){
            String nomeArtista = editTextName.getText().toString().trim();
            String emailArtista = editTextEmail.getText().toString().trim();
            String senhaArtista = editTextPassword.getText().toString().trim();

            if(!TextUtils.isEmpty(nomeArtista)
                    &&(!TextUtils.isEmpty(emailArtista))
                    && (!TextUtils.isEmpty(senhaArtista))) {

                    String id = databaseReference.push().getKey();
                    Artista artista = new Artista(id,nomeArtista,emailArtista);

                    databaseReference.child(id).setValue(artista);

                    editTextName.setText("");
                    editTextEmail.setText("");
                    editTextPassword.setText("");


            }
            else{

                Toast.makeText(LoginArtistaActivityTeste.this,"Informe os dados corretamente",Toast.LENGTH_LONG).show();



            }



        }

    }



