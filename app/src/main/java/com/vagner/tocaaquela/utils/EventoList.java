package com.vagner.tocaaquela.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Event;

import java.util.List;

import static com.vagner.tocaaquela.R.layout.evento_lista;

public class EventoList extends ArrayAdapter<Event> {
    private Activity context;
    List<Event> events;

    public EventoList(Activity context, List<Event> events) {
        super(context,R.layout.evento_lista, events);
        this.context = context;
        this.events = events;
    }






    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(evento_lista, null, false);

        TextView textViewLocalEvento = (TextView) listViewItem.findViewById(R.id.local_evento_id);
        TextView textViewDiaEvento = (TextView) listViewItem.findViewById(R.id.dia_evento_id);
        TextView textViewHoraInicio = (TextView) listViewItem.findViewById(R.id.hora_inicio_evento_id);
       // TextView textViewHoraTermino = (TextView) listViewItem.findViewById(R.id.hora_termino_evento_id);

        Event event = events.get(position);

        textViewLocalEvento.setText(event.getArtistLocalEvent());
        textViewDiaEvento.setText(event.getArtistDiaEvent());
        textViewHoraInicio.setText(event.getArtistHorario());
        //textViewHoraTermino.setText(evento.getHorarioTermino());



        return listViewItem;
    }

}
