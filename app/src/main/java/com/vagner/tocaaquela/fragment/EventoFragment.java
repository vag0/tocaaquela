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
import com.vagner.tocaaquela.model.Evento;
import com.vagner.tocaaquela.utils.EventoList;
import com.vagner.tocaaquela.view.EscolhaDeMusicasActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventoFragment extends Fragment {

    ListView listViewEventos;

    List<Evento> eventos;


    DatabaseReference databaseEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_evento, container, false);


        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseEventos = FirebaseDatabase.getInstance().getReference("eventos");


        listViewEventos = (ListView) view.findViewById(R.id.listViewEventos_id);


        eventos = new ArrayList<>();

        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Evento evento = eventos.get(i);
                Intent intencao = new Intent(getActivity(), EscolhaDeMusicasActivity.class);
                startActivity(intencao);
                // showUpdateDeleteDialog(consulta.getIdConsulta(), consulta.getNomeEspecialista());

            }



        });

        return view;
    }


    public void buscaEventos() {
        final ValueEventListener valueEventListener = databaseEventos.addValueEventListener(new ValueEventListener() {
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
    }

    public void onStart() {
        super.onStart();

        buscaEventos();
    }

}
