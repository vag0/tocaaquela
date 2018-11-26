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
import com.vagner.tocaaquela.model.Evento1;
import com.vagner.tocaaquela.utils.EventoList;
import com.vagner.tocaaquela.utils.FragmentoUtils;
import com.vagner.tocaaquela.utils.Singleton;
import com.vagner.tocaaquela.view.BloqueiaVotoActivity;
import com.vagner.tocaaquela.view.EscolhaDeMusicasActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventoFragment extends Fragment {
    public static final String EVENTO_NAME = "com.vagner.tocaaquela.evento1name";
    public static final String EVENTO_ID = "com.vagner.tocaaquela.evento1id";

    private String idUsuario;
    private FirebaseAuth auth;

    ListView listViewEventos;

    List<Evento1> eventos;

    DatabaseReference databaseEventos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_evento, container, false);

        auth = FirebaseAuth.getInstance();

        databaseEventos = FirebaseDatabase.getInstance().getReference("eventos");



        listViewEventos = (ListView) view.findViewById(R.id.listViewEventos_id);


        eventos = new ArrayList<>();

        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        Evento1 evento = eventos.get(i);
                        Intent intent = new Intent(getContext(), EscolhaDeMusicasActivity.class);
                        intent.putExtra(EVENTO_ID, evento.getEvento1Id());
                        intent.putExtra(EVENTO_NAME, evento.getEvento1Nome());
                        startActivity(intent);
                        verificaAuth();

                         Singleton.getInstacia().salva(evento);


            }



        });

        return view;
    }


    public void buscaEventos() {
        databaseEventos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                eventos.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Evento1 evento = postSnapshot.getValue(Evento1.class);

                    eventos.add(evento);
                }


                if (getActivity()!=null){

                    EventoList   eventoAdapter = new EventoList(getActivity(), eventos);
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
