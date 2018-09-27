package com.vagner.tocaaquela.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Musica;
import com.vagner.tocaaquela.utils.MusicaList;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MinhasMusicasFragment extends Fragment {
    ListView listViewMusicas;

    List<Musica> musicas;

    DatabaseReference databaseMusicas;




    public MinhasMusicasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minhas_musicas, container, false);

        databaseMusicas = FirebaseDatabase.getInstance().getReference("musicas");



        listViewMusicas = (ListView) view.findViewById(R.id.listViewMusicas_id);



        musicas = new ArrayList<>();

        listViewMusicas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Musica musica = musicas.get(i);

                // showUpdateDeleteDialog(consulta.getIdConsulta(), consulta.getNomeEspecialista());
                return true;
            }
        });



        return view;
    }

    public void buscaMusicas(){
        final ValueEventListener valueEventListener = databaseMusicas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    musicas.clear();


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                        Musica musica = postSnapshot.getValue(Musica.class);
                        //musica.setIdConsulta(postSnapshot.getKey());
                        //if(co.getEmailEspecialista().equals(especialista.getEmail())){

                        musicas.add(musica);
                        // }

                    }


                    MusicaList musicaAdapter = new MusicaList(getActivity(),musicas);

                    listViewMusicas.setAdapter(musicaAdapter);
                }catch (Exception e){
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

        buscaMusicas();
    }



}
