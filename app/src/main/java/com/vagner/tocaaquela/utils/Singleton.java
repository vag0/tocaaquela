package com.vagner.tocaaquela.utils;

import com.vagner.tocaaquela.model.Artist;

public class Singleton {

    private static Singleton instancia;

    private Artist artist;

    public Singleton(){}

    public static Singleton getInstacia(){

        if(instancia == null){
            instancia = new Singleton();
        }

        return instancia;
    }

    public void salva(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
