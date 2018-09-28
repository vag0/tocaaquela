package com.vagner.tocaaquela.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.view.LoginArtistaActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    private Button buttonSouMusico;
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
        setHasOptionsMenu(true);
        View view;

        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        linearLayoutSectionPerfil = (LinearLayout)view.findViewById(R.id.section_perfil);
        buttonSingIn = (SignInButton) view.findViewById(R.id.botao_login_perfil);
        //buttonSouMusico = (Button)view.findViewById(R.id.botao_sou_musico);
        buttonSingOut =(Button)view.findViewById(R.id.botao_logout_google);
        imageViewPerfil =(ImageView) view.findViewById(R.id.imagem_perfil);
        textViewEmailUser = (TextView)view.findViewById(R.id.email_perfil);
        textViewNomeUser =(TextView)view.findViewById(R.id.nome_perfil);

        buttonSingIn.setOnClickListener(this);
        buttonSingOut.setOnClickListener(this);

        linearLayoutSectionPerfil.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(getActivity()).enableAutoManage(getActivity(),this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        entrarArtista(view);
        return view;
    }
    @Override
    public void onPause() {  // adicionado por último, resolveu erro do id 0
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
       // googleApiClient.disconnect();

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
    private void entrarArtista(View view) {
        buttonSouMusico = (Button) view.findViewById(R.id.botao_sou_musico);
        buttonSouMusico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intencao = new Intent(getActivity(), LoginEspecialista.class);
                Intent intencao = new Intent(getActivity(),LoginArtistaActivity.class);

                startActivity(intencao);

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_artista_login, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sou_musico) {
            Intent intent = new Intent(getActivity(),LoginArtistaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
