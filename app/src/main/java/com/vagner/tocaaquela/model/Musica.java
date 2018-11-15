package com.vagner.tocaaquela.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de musica
 */
public class Musica {

    private String Id;
    private String nome;
    private String autor;
    private String genero;
    private int totalDeVotos;
    private boolean escolhida;
    private Voto voto;
    DatabaseReference databaseMusicas;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getTotalDeVotos() {
        return totalDeVotos;
    }

    public void setTotalDeVotos(int totalDeVotos) {
        this.totalDeVotos = totalDeVotos;
    }

    public boolean isEscolhida() {
        return escolhida;
    }

    public void setEscolhida(boolean escolhida) {
        this.escolhida = escolhida;
    }

    public Voto getVoto() {
        return voto;
    }

    public void setVoto(Voto voto) {
        this.voto = voto;
    }
    public Musica salvaVoto(){
        databaseMusicas = FirebaseDatabase.getInstance().getReference("musicas");
        Context context;

       Musica musica = new Musica();









        final ValueEventListener valueEventListener = databaseMusicas.addValueEventListener(new ValueEventListener() {
            List<Musica> musicasVotadas = new ArrayList<>();
            Musica musica;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{




                    for (DataSnapshot snapshot : dataSnapshot.child(dataSnapshot.getKey()).getChildren() ) {
                        Musica musica = snapshot.getValue( Musica.class );
                        musica.setTotalDeVotos(musica.getTotalDeVotos());

                    }
                    musicasVotadas.add(musica);


                    //ConsultaList consultaAdapter = new ConsultaList(getActivity(), consultas);


                    // listViewConsultas.setAdapter(consultaAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return musica;
    }


}
