package com.vagner.tocaaquela.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Artista;
import com.vagner.tocaaquela.utils.Firebase;

public class LoginArtistaActivity extends AppCompatActivity {



    private FirebaseAuth firebaseAuth;

    private CallbackManager callbackManager;

    private EditText editTextEmail;

    private EditText editTextSenha;

    private Button buttonEntrar;

    private TextView textViewCadastrar;


    private FirebaseAuth autenticacao;

    private Artista artista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_artista);
        entrarArtista();

        verificaArtistaLogado();

        inicializarComponentes();
        inicializarFirebaseCallback();
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailArtista = editTextEmail.getText().toString();
                String senhaArtista = editTextSenha.getText().toString();

                if (!emailArtista.isEmpty()){

                    if(!senhaArtista.isEmpty()){


                        artista = new Artista();
                        artista.setEmail(emailArtista);
                        artista.setSenha(senhaArtista);
                        validaArtista(artista);

                    }


                }

            }
        });

    }


    private void firebaseLogin(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginArtistaActivity.this,MenuArtistaActivity.class);
                            startActivity(intent);

                        }else {

                            alert("erro de login");
                        }

                    }
                });
    }

    private void alert(String operação_cancelada) {
        Toast.makeText(this,operação_cancelada,Toast.LENGTH_LONG).show();
    }

    private void inicializarFirebaseCallback() {
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
    }


    public void inicializarComponentes() {

        editTextEmail = findViewById(R.id.edit_email_login_artista);
        editTextSenha = findViewById(R.id.edit_senha_login_artista);
        buttonEntrar = findViewById(R.id.button_login_artista);

        editTextEmail.requestFocus();

    }

    public void verificaArtistaLogado(){
        autenticacao = Firebase.getFirebaseAuthAutenticacao();
        if(autenticacao.getCurrentUser()!=null){
            startActivity(new Intent(this, MenuArtistaActivity.class));
            finish();

        }

    }

    public void validaArtista(Artista artista){

        autenticacao = Firebase.getFirebaseAuthAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                artista.getEmail(),
                artista.getSenha()

        ).addOnCompleteListener(LoginArtistaActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginArtistaActivity.this,MenuArtistaActivity.class);
                startActivity(intent);
                Log.d("SuccessInCreateUser", "No exception");
                if (task.isSuccessful()) {
                    //Intent intent = new Intent(LoginArtistaActivity.this,MenuArtistaActivity.class);

                    finish();
                }
                   // Log.w(TAG, "onComplete: Failed=" + task.getException().getMessage());

                    //Catch specific exception here like this. Below is the example of password less than 6 char - weak password exception catch
                if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                        Toast.makeText(getApplicationContext(), "Login ou Senha Incorretos", Toast.LENGTH_SHORT).show();

                }

            }


        });

    }

    public void iniciaCadastro(){
        Intent intent = new Intent(LoginArtistaActivity.this,CadastroArtistaActivity.class);
        startActivity(intent);

    }


    private void entrarArtista() {
        textViewCadastrar = findViewById(R.id.artista_cadastrar);
        textViewCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciaCadastro();

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

        }





}
