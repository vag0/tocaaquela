package com.vagner.tocaaquela.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Event;
import com.vagner.tocaaquela.utils.EventoList;
import com.vagner.tocaaquela.utils.FragmentoUtils;
import com.vagner.tocaaquela.utils.Singleton;
import com.vagner.tocaaquela.view.EscolhaDeMusicasActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventoFragment extends Fragment {
    public static final String EVENT_NAME = "com.vagner.tocaaquela.artistid";
    public static final String EVENT_ID = "com.vagner.tocaaquela.artistid";
    private String idUsuario;
    private FirebaseAuth auth;

    ListView listViewEventos;

    List<Event> events;

    DatabaseReference databaseArtists;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_evento, container, false);

        auth = FirebaseAuth.getInstance();

        databaseArtists = FirebaseDatabase.getInstance().getReference("events");



        listViewEventos = (ListView) view.findViewById(R.id.listViewEventos_id);


        events = new ArrayList<>();

        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        Event event = events.get(i);
                        Intent intent = new Intent(getContext(), EscolhaDeMusicasActivity.class);
                        intent.putExtra(EVENT_ID, event.getArtistId());
                        intent.putExtra(EVENT_NAME, event.getArtistLocalEvent());
                        startActivity(intent);
                        verificaAuth();

                         Singleton.getInstacia().salva(event);


            }



        });

        return view;
    }


    public void buscaEventos() {
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                events.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Event event = postSnapshot.getValue(Event.class);

                    events.add(event);
                }


                if (getActivity()!=null){

                    EventoList   eventoAdapter = new EventoList(getActivity(), events);
                    listViewEventos.setAdapter(eventoAdapter);

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void verificaAuth() {

        if (auth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                idUsuario = user.getUid();
            }

        }

        if (auth.getCurrentUser() == null) {
            FragmentoUtils.replace(getActivity(), new PerfilUserFragment());
        }

    }


    public void onStart() {
        super.onStart();

        buscaEventos();
    }

}
