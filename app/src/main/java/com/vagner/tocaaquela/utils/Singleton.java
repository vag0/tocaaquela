package com.vagner.tocaaquela.utils;

import com.vagner.tocaaquela.model.Event;

public class Singleton {

    private static Singleton instancia;

    private Event event;

    public Singleton(){}

    public static Singleton getInstacia(){

        if(instancia == null){
            instancia = new Singleton();
        }

        return instancia;
    }

    public void salva(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
