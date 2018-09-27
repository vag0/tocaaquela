package com.vagner.tocaaquela.model;

import java.util.Date;

public class Evento {
    private String local;

    private String diaEvento;

    private String horarioInicio;

    private String horarioTermino;


    public Evento() {

    }

    public Evento(String local,String diaEvento, String horarioInicio, String horarioTermino){
        this.local = local;
        this.diaEvento = diaEvento;
        this.horarioInicio = horarioInicio;
        this.horarioTermino = horarioTermino;

    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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

    public String getHorarioTermino() {
        return horarioTermino;
    }

    public void setHorarioTermino(String horarioTermino) {
        this.horarioTermino = horarioTermino;
    }
}
