package com.vagner.tocaaquela.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Belal on 2/26/2017.
 */
@IgnoreExtraProperties
public class Artist implements Serializable {
    private String artistId;
    private String artistLocalEvent;
    private String artistDiaEvent;
    private String artistGenre;
    private String artistNameEmail;
    private String artistHorario;

    public Artist(){
        //this constructor is required
    }

    public Artist(String artistId, String artistLocalEvent,String artistDiaEvent, String artistGenre, String artistNameEmail, String artistHorario) {
        this.artistId = artistId;
        this.artistLocalEvent = artistLocalEvent;
        this.artistDiaEvent = artistDiaEvent;
        this.artistGenre = artistGenre;
        this.artistNameEmail = artistNameEmail;
        this.artistHorario = artistHorario;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistLocalEvent() {
        return artistLocalEvent;
    }

    public String getArtistDiaEvent() {
        return artistDiaEvent;
    }

    public String getArtistGenre() {
        return artistGenre;
    }

    public String getArtistNameEmail() {
        return artistNameEmail;
    }

    public String getArtistHorario() {
        return artistHorario;
    }
}