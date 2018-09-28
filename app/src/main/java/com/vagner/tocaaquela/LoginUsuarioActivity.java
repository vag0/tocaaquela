package com.vagner.tocaaquela;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vagner.tocaaquela.view.MenuArtistaActivity;

public class LoginUsuarioActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    private Button buttonSingOut;
    private SignInButton buttonSingIn;
    private LinearLayout linearLayoutSectionPerfil;
    private TextView textViewNomeUser;
    private TextView textViewEmailUser;
    private ImageView imageViewPerfil;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 1234;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        linearLayoutSectionPerfil = (LinearLayout) findViewById(R.id.section_perfil);
        buttonSingIn = (SignInButton) findViewById(R.id.botao_login_perfil);
        buttonSingOut =(Button)findViewById(R.id.botao_logout_google);
        imageViewPerfil =(ImageView) findViewById(R.id.imagem_perfil);
        textViewEmailUser = (TextView)findViewById(R.id.email_perfil);
        textViewNomeUser =(TextView)findViewById(R.id.nome_perfil);

        buttonSingIn.setOnClickListener(this);
        buttonSingOut.setOnClickListener(this);

        linearLayoutSectionPerfil.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.botao_login_perfil:
                signIn();
                break;


            case R.id.botao_logout_google:
                signOut();
                break;


        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);


    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUi(false);
            }
        });

    }

    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount googleSignInAccount = result.getSignInAccount();

            String nomePerfil = googleSignInAccount.getDisplayName();
            String emailPerfil = googleSignInAccount.getEmail();
            String imagemPerfil = googleSignInAccount.getPhotoUrl().toString();

            textViewNomeUser.setText(nomePerfil);
            textViewEmailUser.setText(emailPerfil);
            Glide.with(this).load(imagemPerfil).into(imageViewPerfil);
            updateUi(true);


        }
        else{
            updateUi(false);

        }

    }

    private void updateUi(boolean isLogin){

        if(isLogin){

            linearLayoutSectionPerfil.setVisibility(View.VISIBLE);
            buttonSingIn.setVisibility(View.GONE);

            Intent intent = new Intent(this,MenuArtistaActivity.class);
            startActivity(intent);

        }else{
            linearLayoutSectionPerfil.setVisibility(View.GONE);
            buttonSingIn.setVisibility(View.VISIBLE);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQ_CODE){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }
}
