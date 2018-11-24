package com.vagner.tocaaquela.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonSignIn2;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup2;


    private FirebaseAuth firebaseAuth;


    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){

            finish();

            startActivity(new Intent(getApplicationContext(), MenuArtistaActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn2 = (Button) findViewById(R.id.buttonSignin2);
        textViewSignup2  = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);


        buttonSignIn2.setOnClickListener(this);
        textViewSignup2.setOnClickListener(this);
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();



        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Por favor digite o email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Por favor digite a senha",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registrando Aguarde...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){

                            finish();
                            Intent intent = new Intent(getApplicationContext(), MenuArtistaActivity.class);;
                            startActivity(intent);
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn2){
            userLogin();
        }

        if(view == textViewSignup2){
            finish();
            startActivity(new Intent(this, LoginArtistaActivityTeste.class));
        }
    }
}