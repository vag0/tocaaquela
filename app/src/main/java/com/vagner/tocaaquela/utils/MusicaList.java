package com.vagner.tocaaquela.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Musica;

import java.util.List;

public class MusicaList  extends ArrayAdapter<Musica> {
    private Activity context;
    List<Musica> musicas;

    public MusicaList(Activity context, List<Musica> musicas) {
        super(context, R.layout.musica_lista, musicas);
        this.context = context;
        this.musicas = musicas;
    }






    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.musica_lista,null,false);
        TextView textViewNomeMusica = (TextView) listViewItem.findViewById(R.id.nome_musica_id);
        TextView textViewAutorMusica = (TextView) listViewItem.findViewById(R.id.nome_autor_id);
        TextView textViewGeneroMusica = (TextView) listViewItem.findViewById(R.id.genero_musica_id);

        Musica musica = musicas.get(position);

        textViewNomeMusica.setText(musica.getNome());
        textViewAutorMusica.setText(musica.getAutor());
        textViewGeneroMusica.setText(musica.getGenero());




        return listViewItem;
    }

}
