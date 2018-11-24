package com.vagner.tocaaquela.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Event;

import java.util.List;

public class EventList {
    private Activity context;
    List<Event> events;

    public EventList(Activity context, List<Event> events) {
      //  super(context,R.layout.layout_event_list, events);
        this.context = context;
        this.events = events;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_artist_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Event event = events.get(position);
        textViewName.setText(event.getArtistLocalEvent());
        textViewGenre.setText(event.getArtistGenre());

        return listViewItem;
    }
}
