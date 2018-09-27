package com.vagner.tocaaquela.utils;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Evento;

import java.util.List;

import static com.vagner.tocaaquela.R.layout.evento_lista;

public class EventoList extends ArrayAdapter<Evento> {
    private Activity context;
    List<Evento> eventos;

    public EventoList(Activity context, List<Evento> eventos) {
        super(context,R.layout.evento_lista, eventos);
        this.context = context;
        this.eventos = eventos;
    }






    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(evento_lista, null, false);

        TextView textViewLocalEvento = (TextView) listViewItem.findViewById(R.id.local_evento_id);
        TextView textViewDiaEvento = (TextView) listViewItem.findViewById(R.id.dia_evento_id);
        TextView textViewHoraInicio = (TextView) listViewItem.findViewById(R.id.hora_inicio_evento_id);
        TextView textViewHoraTermino = (TextView) listViewItem.findViewById(R.id.hora_termino_evento_id);

        Evento evento = eventos.get(position);

        textViewLocalEvento.setText(evento.getLocal());
        textViewDiaEvento.setText(evento.getDiaEvento());
        textViewHoraInicio.setText(evento.getHorarioInicio());
        textViewHoraTermino.setText(evento.getHorarioTermino());



        return listViewItem;
    }

}
