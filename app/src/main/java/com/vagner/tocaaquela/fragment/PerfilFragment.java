package com.vagner.tocaaquela.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vagner.tocaaquela.MenuArtistaActivity;
import com.vagner.tocaaquela.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    private Button buttonSingOut;
    private SignInButton buttonSingIn;
    private LinearLayout linearLayoutSectionPerfil;
    private TextView textViewNomeUser;
    private TextView textViewEmailUser;
    private ImageView imageViewPerfil;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 1234;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        linearLayoutSectionPerfil = (LinearLayout)view.findViewById(R.id.section_perfil);
        buttonSingIn = (SignInButton) view.findViewById(R.id.botao_login_perfil);
        buttonSingOut =(Button)view.findViewById(R.id.botao_logout_google);
        imageViewPerfil =(ImageView) view.findViewById(R.id.imagem_perfil);
        textViewEmailUser = (TextView)view.findViewById(R.id.email_perfil);
        textViewNomeUser =(TextView)view.findViewById(R.id.nome_perfil);

        buttonSingIn.setOnClickListener(this);
        buttonSingOut.setOnClickListener(this);

        linearLayoutSectionPerfil.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(getActivity()).enableAutoManage(getActivity(),this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


        return view;
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

            Intent intent = new Intent(getActivity(),MenuArtistaActivity.class);
            startActivity(intent);

        }else{
            linearLayoutSectionPerfil.setVisibility(View.GONE);
            buttonSingIn.setVisibility(View.VISIBLE);

        }


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQ_CODE){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }



}
