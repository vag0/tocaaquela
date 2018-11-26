package com.vagner.tocaaquela.view;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.fragment.EventoFragment;
import com.vagner.tocaaquela.fragment.PerfilUserFragment;
import com.vagner.tocaaquela.model.Evento;
import com.vagner.tocaaquela.model.Musica;
import com.vagner.tocaaquela.model.Track;
import com.vagner.tocaaquela.model.Voto;
import com.vagner.tocaaquela.utils.FragmentoUtils;
import com.vagner.tocaaquela.utils.MusicaList;
import com.vagner.tocaaquela.utils.Singleton;

import java.util.ArrayList;
import java.util.List;

public class EscolhaDeMusicasActivity extends AppCompatActivity {



    private FirebaseAuth firebaseAuth;




    ListView listViewMusicas;



    DatabaseReference databaseVotos;

    DatabaseReference databaseTracks;


    TextView textViewCodigo;
    Button buttonEnviaCodigo;

    String codigoDigitado;

    List<Track> tracks;

    private String idUsuario;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_de_musicas);
        auth = FirebaseAuth.getInstance();





        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(getIntent().getStringExtra(EventoFragment.EVENTO_ID));

        databaseVotos = FirebaseDatabase.getInstance().getReference("votos").child(getIntent().getStringExtra(EventoFragment.EVENTO_ID));



        listViewMusicas = findViewById(R.id.listViewMusicas_id);
        textViewCodigo = findViewById(R.id.editTextCodigoUser);
        buttonEnviaCodigo = findViewById(R.id.buttonEnviaCodigo);
        buttonEnviaCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               codigoDigitado  = textViewCodigo.getText().toString().trim();
               textViewCodigo.setText("");

            }
        });


        tracks = new ArrayList<>();

        //textViewArtist.setText(intent.getStringExtra(EventActivity.EVENT_NAME));

      //  verificaAuth();

        escolhaDeMusica();




    }


    private void escolhaDeMusica() {



       /* AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_alert_codigo, null);
        dialogBuilder.setView(dialogView);

        EditText editText = (EditText) dialogView.findViewById(R.id.editTextInformationName);
        editText.setText("test label");
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();*/

        listViewMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               firebaseAuth = FirebaseAuth.getInstance();
               FirebaseUser user = firebaseAuth.getCurrentUser();


               String emailUser = user.getEmail();

                Track track ;

                track = tracks.get(i);

                String nome = track.getTrackName();
                int cont = 0;



                String nomeEvento = Singleton.getInstacia().getEvento().getEvento1Nome();

                String codigoEvento = Singleton.getInstacia().getEvento().getEvento1Codigo();



                String id = databaseVotos.push().getKey();



               Voto voto = new Voto(id,nome, emailUser,nomeEvento);

               if(codigoDigitado.equals(codigoEvento)){
                   cont++;
                   databaseVotos.child(id).setValue(voto);
                   Toast.makeText(getBaseContext(), " Voto numero "+cont, Toast.LENGTH_SHORT).show();


               }
               else {

                   finish();
                   Toast.makeText(getBaseContext(), " codigo digitado "+codigoDigitado, Toast.LENGTH_SHORT).show();
               }



            }


        });

    }




    public void buscaMusicas() {
        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Track track = postSnapshot.getValue(Track.class);
                    tracks.add(track);
                }
                MusicaList musicaListAdapter = new MusicaList(EscolhaDeMusicasActivity.this, tracks);
                listViewMusicas.setAdapter(musicaListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onStart() {
        super.onStart();

        buscaMusicas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_avalia_artista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_avaliar) {
            Intent intent = new Intent(this,AvaliaArtistaActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_sugestao) {
            Intent intent = new Intent(this,SugestaoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void verificaAuth() {

        if (auth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                idUsuario = user.getUid();
            }

        }

        if (auth.getCurrentUser() == null) {
            FragmentoUtils.replace(this, new PerfilUserFragment());
        }

    }





}
