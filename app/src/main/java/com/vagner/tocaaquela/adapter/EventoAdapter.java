package com.vagner.tocaaquela.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Evento;
import com.vagner.tocaaquela.model.Musica;
import com.vagner.tocaaquela.view.EscolhaDeMusicasActivity;

import java.util.ArrayList;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<Evento> listaDeEvento;

    public EventoAdapter(Context context, ArrayList<Evento> listaDeEvento) {
        this.context = context;
        this.listaDeEvento = listaDeEvento;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_item_evento,parent,false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.local_evento.setText(listaDeEvento.get(position).getLocal());
        holder.dia_evento.setText(listaDeEvento.get(position).getDiaEvento());
        holder.horario_inicio.setText(listaDeEvento.get(position).getHorarioInicio());
        holder.horario_termino.setText(listaDeEvento.get(position).getHorarioTermino());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intencao = new Intent(context,EscolhaDeMusicasActivity.class);

                intencao.putExtra("Evento",holder.getAdapterPosition());

                context.startActivity(intencao);

            }
        });


    }


    @Override
    public int getItemCount() {
        return listaDeEvento.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView local_evento;
        TextView dia_evento;
        TextView horario_inicio;
        TextView horario_termino;
        CardView cardView_evento;


        public MyViewHolder(View itemView){

            super(itemView);

            local_evento = (TextView ) itemView.findViewById(R.id.local_evento_id);
            dia_evento = (TextView ) itemView.findViewById(R.id.dia_evento_id);
            horario_inicio = (TextView ) itemView.findViewById(R.id.hora_inicio_evento_id);
            horario_termino = (TextView ) itemView.findViewById(R.id.hora_termino_evento_id);


            cardView_evento = (CardView) itemView.findViewById(R.id.cardview_evento_id);

        }
    }
}