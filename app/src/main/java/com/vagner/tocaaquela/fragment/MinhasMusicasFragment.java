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
import com.vagner.tocaaquela.model.Track;
import com.vagner.tocaaquela.utils.MusicaList;
import com.vagner.tocaaquela.utils.Singleton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MinhasMusicasFragment extends Fragment {
    ListView listViewMinhasMusicas;

    List<Musica> musicas;
    List<Track> tracks;
    DatabaseReference databaseVotos;




    public MinhasMusicasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minhas_musicas, container, false);

        databaseVotos = FirebaseDatabase.getInstance().getReference("votos").child(Singleton.getInstacia().getEvent().getArtistLocalEvent());



        listViewMinhasMusicas = (ListView) view.findViewById(R.id.listViewMusicasMinhasMusica_id);



        musicas = new ArrayList<>();
        tracks = new ArrayList<>();

        listViewMinhasMusicas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Musica musica = musicas.get(i);
                Track track = tracks.get(i);

                // showUpdateDeleteDialog(consulta.getIdConsulta(), consulta.getNomeEspecialista());
                return true;
            }
        });



        return view;
    }
//firebase.database().ref('receitas').orderByChild('tipo_nome').startAt('true_').endAt('true_\uf8ff');
public void buscaMusicas() {
    databaseVotos.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tracks.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Track track = postSnapshot.getValue(Track.class);
                tracks.add(track);
            }
            MusicaList musicaListAdapter = new MusicaList(getActivity(), tracks);
            listViewMinhasMusicas.setAdapter(musicaListAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}

    public void onStart() {
        super.onStart();
        if(Singleton.getInstacia()==null){
        }
        buscaMusicas();
    }



}
