package com.vagner.tocaaquela.utils;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Artist;
import com.vagner.tocaaquela.model.Evento;

import java.util.List;

import static com.vagner.tocaaquela.R.layout.evento_lista;

public class EventoList extends ArrayAdapter<Artist> {
    private Activity context;
    List<Artist> artists;

    public EventoList(Activity context, List<Artist> artists) {
        super(context,R.layout.evento_lista, artists);
        this.context = context;
        this.artists = artists;
    }






    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(evento_lista, null, false);

        TextView textViewLocalEvento = (TextView) listViewItem.findViewById(R.id.local_evento_id);
        TextView textViewDiaEvento = (TextView) listViewItem.findViewById(R.id.dia_evento_id);
        TextView textViewHoraInicio = (TextView) listViewItem.findViewById(R.id.hora_inicio_evento_id);
       // TextView textViewHoraTermino = (TextView) listViewItem.findViewById(R.id.hora_termino_evento_id);

        Artist artist = artists.get(position);

        textViewLocalEvento.setText(artist.getArtistLocalEvent());
        textViewDiaEvento.setText(artist.getArtistDiaEvent());
        textViewHoraInicio.setText(artist.getArtistHorario());
        //textViewHoraTermino.setText(evento.getHorarioTermino());



        return listViewItem;
    }

}
