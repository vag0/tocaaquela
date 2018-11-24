package com.vagner.tocaaquela.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Evento1;

import java.util.List;

public class ArtistList extends ArrayAdapter<Evento1> {
    private Activity context;
    List<Evento1> artists;

    public ArtistList(Activity context, List<Evento1> artists) {
        super(context, R.layout.layout_artist_list, artists);
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_artist_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Evento1 artist = artists.get(position);
        textViewName.setText(artist.getEvento1Nome());
        textViewGenre.setText(artist.getEvento1Genero());

        return listViewItem;
    }
}