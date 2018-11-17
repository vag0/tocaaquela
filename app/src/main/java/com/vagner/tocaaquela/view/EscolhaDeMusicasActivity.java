package com.vagner.tocaaquela.view;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
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
import com.vagner.tocaaquela.model.Evento;
import com.vagner.tocaaquela.model.Musica;
import com.vagner.tocaaquela.model.Track;
import com.vagner.tocaaquela.model.Voto;
import com.vagner.tocaaquela.utils.EventoList;
import com.vagner.tocaaquela.utils.Firebase;
import com.vagner.tocaaquela.utils.FragmentoUtils;
import com.vagner.tocaaquela.utils.MusicaList;
import com.vagner.tocaaquela.utils.TrackList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EscolhaDeMusicasActivity extends AppCompatActivity {
    ListView listViewEventos;
    ListView listViewTracks;

    List<Evento> eventos;
    private Voto voto;
    Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    ListView listViewMusicas;
    List<Musica> musicas;
    DatabaseReference myRef;


    int limiteDeVoto = 10;
    int cont = 0;

    DatabaseReference databaseEventos;
    DatabaseReference databaseMusicas;

    DatabaseReference databaseTracks;

    TextView textViewRating, textViewArtist;

    List<Track> tracks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_de_musicas);

        Intent intent = getIntent();


        // databaseEventos = FirebaseDatabase.getInstance().getReference("eventos");
       // databaseMusicas = FirebaseDatabase.getInstance().getReference("musicas");

        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(getIntent().getStringExtra(EventoFragment.ARTIST_ID));


        listViewEventos = findViewById(R.id.listViewMusicas_id);
        listViewTracks = findViewById(R.id.listViewMusicas_id);

        eventos = new ArrayList<>();
        musicas = new ArrayList<>();

        tracks = new ArrayList<>();

        //textViewArtist.setText(intent.getStringExtra(Main2Activity.ARTIST_NAME));



        escolhaDeMusica();


    }

    private void escolhaDeMusica() {
        myRef =  FirebaseDatabase.getInstance().getReference("musicas");


        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int total = 0;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cont = cont + 1;
                Toast.makeText(getBaseContext(), " Seus votos foram "+cont, Toast.LENGTH_SHORT).show();
                musicas.get(i).setTotalDeVotos(0);

                if (cont >= limiteDeVoto) {
                    Intent intent = new Intent(EscolhaDeMusicasActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                qtdVotoNestaMusica(i,total);

            }


        });

    }

    private void qtdVotoNestaMusica(int i,int totalDeVotos) {
        Musica musica = new Musica();
        musicas.get(i).setTotalDeVotos(totalDeVotos);
        myRef.push().setValue(musica.getTotalDeVotos());
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
                TrackList trackListAdapter = new TrackList(EscolhaDeMusicasActivity.this, tracks);
                listViewTracks.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      /*  final ValueEventListener valueEventListener = databaseMusicas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    musicas.clear();


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        // Usuario usuario = Singleton.getInstacia().getUsuario();


                        Musica musica = postSnapshot.getValue(Musica.class);
                        //  evento(postSnapshot.getKey());

                        // if(consulta.getEmailUsuario().equals(usuario.getEmail())){
                        musicas.add(musica);
                        // }

                    }
                    //Collections.reverse(eventos);

                    MusicaList musicaAdapter = new MusicaList(EscolhaDeMusicasActivity.this, musicas);


                    listViewMusicas.setAdapter((ListAdapter) musicaAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
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


}
