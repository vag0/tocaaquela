package com.vagner.tocaaquela.utils;

import com.vagner.tocaaquela.model.Event;
import com.vagner.tocaaquela.model.Evento1;

public class Singleton {

    private static Singleton instancia;

    private Evento1 evento;

    private String codigo;

    public Singleton(){}

    public static Singleton getInstacia(){

        if(instancia == null){
            instancia = new Singleton();
        }

        return instancia;
    }

    public void salva(Evento1 evento) {
        this.evento = evento;
    }

    public void salvaCodigo(String codigo){

        this.codigo = codigo;
    }

    public String getCodigo(){
       return codigo; }

    public Evento1 getEvento() {
        return evento;
    }

    public void setEvento1(Evento1 evento) {
        this.evento = evento;
    }
}
