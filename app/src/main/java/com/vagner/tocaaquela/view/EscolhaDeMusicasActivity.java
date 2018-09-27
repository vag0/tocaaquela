package com.vagner.tocaaquela.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class EscolhaDeMusicasActivity extends AppCompatActivity {
    ListView listViewMusicas;

    List<Musica> musicas;

    DatabaseReference databaseMusicas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_de_musicas);

        databaseMusicas = FirebaseDatabase.getInstance().getReference("musicas");



        listViewMusicas = (ListView) findViewById(R.id.listViewMusicas_id);



        musicas = new ArrayList<>();



        listViewMusicas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Musica musica = musicas.get(i);

                // showUpdateDeleteDialog(consulta.getIdConsulta(), consulta.getNomeEspecialista());
                return true;
            }
        });

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


                    MusicaList musicaAdapter = new MusicaList(EscolhaDeMusicasActivity.this,musicas);

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
