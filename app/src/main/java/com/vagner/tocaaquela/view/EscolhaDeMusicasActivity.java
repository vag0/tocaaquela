package com.vagner.tocaaquela.view;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    ListView listViewEventos;
    ListView listViewTracks;

    List<Evento> eventos;
    private Voto voto;
    Context context;

    private FirebaseAuth firebaseAuth;




    ListView listViewMusicas;
    List<Musica> musicas;
    DatabaseReference myRef;


    int limiteDeVoto = 10;
    int cont = 0;

    DatabaseReference databaseEventos;
    DatabaseReference databaseVotos;

    DatabaseReference databaseTracks;

    DatabaseReference databaseContaVotos;

    TextView textViewRating, textViewArtist;

    List<Track> tracks;
    List<Voto> votos;
    private String idUsuario;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_de_musicas);
        auth = FirebaseAuth.getInstance();





        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(getIntent().getStringExtra(EventoFragment.EVENT_ID));

        databaseVotos = FirebaseDatabase.getInstance().getReference("votos").child(getIntent().getStringExtra(EventoFragment.EVENT_ID));

        databaseContaVotos = FirebaseDatabase.getInstance().getReference("votos");


        listViewMusicas = findViewById(R.id.listViewMusicas_id);


        tracks = new ArrayList<>();

        //textViewArtist.setText(intent.getStringExtra(EventActivity.EVENT_NAME));

      //  verificaAuth();

        escolhaDeMusica();




    }

    private void escolhaDeMusica() {


        listViewMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();


               String emailUser = user.getEmail();

                Track track ;

                track = tracks.get(i);

                String nome = track.getTrackName();



                String nomeEvento = Singleton.getInstacia().getEvent().getArtistLocalEvent();


                String id = databaseVotos.push().getKey();



               Voto voto = new Voto(id,nome, emailUser,nomeEvento);

                databaseVotos.child(id).setValue(voto);



                cont = cont + 1;
                Toast.makeText(getBaseContext(), " Seus votos foram "+cont, Toast.LENGTH_SHORT).show();



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
