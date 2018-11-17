package com.vagner.tocaaquela.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Sugestao;
import com.vagner.tocaaquela.utils.Firebase;
import com.vagner.tocaaquela.view.AvaliaArtistaActivity;
import com.vagner.tocaaquela.view.CadastroArtistaActivity;
import com.vagner.tocaaquela.view.EscolhaDeMusicasActivity;
import com.vagner.tocaaquela.view.LoginActivity;
import com.vagner.tocaaquela.view.LoginArtistaActivity;
import com.vagner.tocaaquela.view.LoginArtistaActivityTeste;
import com.vagner.tocaaquela.view.Main2Activity;
import com.vagner.tocaaquela.view.MenuArtistaActivity;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class TesteFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static int RC_SIGN_IN = 0;
    private static String TAG = "MAIN_ACTIVITY";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button buttonSingOut;
    private SignInButton buttonSingIn;
    private TextView textViewNomeUser;
    private TextView textViewEmailUser;
    private ImageView imagemViewPerfil;
    private Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view;

        view = inflater.inflate(R.layout.fragment_teste, container, false);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                context = null;
                if(user != null) {
                    Sugestao sugestao = new Sugestao();
                    sugestao.setMensagem("Evidências");

                    // sugestao.setIdDoMusico(idDoMusico);
                    sugestao.setIdDoUsuario(user.getUid());
                    //enviaSugestao
                }else {

                    Log.d("AUTH", "user logged out.");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        buttonSingIn = (SignInButton) view.findViewById(R.id.sign_in_button);
        buttonSingOut = (Button)view.findViewById(R.id.sign_out_button);
        imagemViewPerfil =(ImageView) view.findViewById(R.id.imagem_perfil);
        textViewEmailUser = (TextView)view.findViewById(R.id.email_perfil);
        textViewNomeUser =(TextView)view.findViewById(R.id.nome_perfil);


        buttonSingIn.setOnClickListener(this);
        buttonSingOut.setOnClickListener(this);
        return view;
    }

   /* private void salvaPerfil(FirebaseUser user) {

        String nomePerfil = user.getDisplayName();
        String emailPerfil = user.getEmail();
        String imagemPerfil = user.getPhotoUrl().toString();

        textViewNomeUser.setText(nomePerfil);
        textViewEmailUser.setText(emailPerfil);
        Glide.with(getActivity()).load(imagemPerfil).into(imagemViewPerfil);
    }
*/
    @Override
    public void onStart() {
        super.onStart();
        buttonSingOut.setVisibility(View.GONE);
        mAuth.addAuthStateListener(mAuthListener);
        if (mGoogleApiClient != null){
            mGoogleApiClient.connect();

        }

    }

   /* @Override
    public void onStop() {
        super.onStop();
      //  if(mAuthListener != null){
        //    mAuth.removeAuthStateListener(mAuthListener);
     //   }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage((FragmentActivity) context);
            mGoogleApiClient.disconnect();
        }

    }*/

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                logado(account);


                // Intent intencao = new Intent(getActivity(),EscolhaDeMusicasActivity.class);
               // startActivity(intencao);
            }
            else {
                buttonSingOut.setVisibility(View.VISIBLE);

            }
            Log.d(TAG, "Google Login Failed");

        }
    }



    private void logado(GoogleSignInAccount account) {
        String nomePerfil = account.getDisplayName();
        String emailPerfil = account.getEmail();
        String imagemPerfil = account.getPhotoUrl().toString();

        textViewNomeUser.setText(nomePerfil);
        textViewEmailUser.setText(emailPerfil);
        Glide.with(getActivity()).load(imagemPerfil).into(imagemViewPerfil);
        buttonSingIn.setVisibility(View.GONE);
        buttonSingOut.setVisibility(View.VISIBLE);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AUTH", "signInWithCredential:oncomplete: " + task.isSuccessful());
                    }
                });
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed.");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sou_artista, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        if (id == R.id.action_sou_artista) {
           // if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("ti.vagner@gmail.com")){
               Intent intent = new Intent(getActivity(),MenuArtistaActivity.class);
               startActivity(intent);
           // }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}