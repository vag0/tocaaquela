package com.vagner.tocaaquela.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Artist;
import com.vagner.tocaaquela.model.Evento;
import com.vagner.tocaaquela.utils.ArtistList;
import com.vagner.tocaaquela.utils.EventoList;
import com.vagner.tocaaquela.view.ArtistActivity;
import com.vagner.tocaaquela.view.EscolhaDeMusicasActivity;
import com.vagner.tocaaquela.view.Main2Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventoFragment extends Fragment {
    public static final String ARTIST_NAME = "com.vagner.tocaaquela.artistid";
    public static final String ARTIST_ID = "com.vagner.tocaaquela.artistid";


    ListView listViewEventos;

    //List<Evento> eventos;
    List<Artist> artists;



   // DatabaseReference databaseEventos;
    DatabaseReference databaseArtists;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_evento, container, false);


        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //databaseEventos = FirebaseDatabase.getInstance().getReference("eventos");
        databaseArtists = FirebaseDatabase.getInstance().getReference("events");



        listViewEventos = (ListView) view.findViewById(R.id.listViewEventos_id);


        artists = new ArrayList<>();

        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artists.get(i);

                //creating an intent
                Intent intent = new Intent(getContext(), EscolhaDeMusicasActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistLocalEvent());


                // Evento evento = eventos.get(i);
               // Intent intencao = new Intent(getActivity(), EscolhaDeMusicasActivity.class);
                startActivity(intent);
                // showUpdateDeleteDialog(consulta.getIdConsulta(), consulta.getNomeEspecialista());

            }



        });

        return view;
    }


    public void buscaEventos() {
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    artists.add(artist);
                }

                //creating adapter
                ArtistList artistAdapter = new ArtistList(getActivity(), artists);
                //attaching adapter to the listview
                listViewEventos.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        /*final ValueEventListener valueEventListener = databaseEventos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    eventos.clear();


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        // Usuario usuario = Singleton.getInstacia().getUsuario();


                        Evento evento = postSnapshot.getValue(Evento.class);
                        //  evento(postSnapshot.getKey());

                        // if(consulta.getEmailUsuario().equals(usuario.getEmail())){
                        eventos.add(evento);
                        // }

                    }
                    Collections.reverse(eventos);

                    EventoList eventoAdapter = new EventoList(getActivity(), eventos);


                    listViewEventos.setAdapter((ListAdapter) eventoAdapter);


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

        buscaEventos();
    }

}
