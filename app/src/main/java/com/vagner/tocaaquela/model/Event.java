package com.vagner.tocaaquela.model;

public class Event {
    private String local;

    private String id;

    private String diaEvento;

    private String horarioInicio;

    private String genero;

    private int rating;

    public Event(){}

    public Event( String id, String local,String diaEvento,String genero, int rating,String horarioInicio) {

        this.local = local;
        this.id = id;
        this.diaEvento = diaEvento;
        this.genero = genero;
        this.rating = rating;
        this.horarioInicio = horarioInicio;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiaEvento() {
        return diaEvento;
    }

    public void setDiaEvento(String diaEvento) {
        this.diaEvento = diaEvento;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
}
