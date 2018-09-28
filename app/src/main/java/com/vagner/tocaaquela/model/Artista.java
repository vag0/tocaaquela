package com.vagner.tocaaquela.model;

import com.google.firebase.database.DatabaseReference;
import com.vagner.tocaaquela.utils.Firebase;

import java.io.Serializable;

public class Artista implements Serializable{

    private String id;

    private String email;

    private String nome;

    private String sobre;

    private String senha;

    private String confirmaSenha;




    private int imagem;

    public Artista() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void salvaArtista(){

        DatabaseReference reference = Firebase.getFirebase();
        DatabaseReference artistasReference = reference.child("artistas").child(getId());
        artistasReference.setValue(this);


    }
}
