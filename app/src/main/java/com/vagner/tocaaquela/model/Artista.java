package com.vagner.tocaaquela.model;

public class Artista {

    private String nome;

    private String sobre;

    private String email;


    private int imagem;

    public Artista(String nome, String sobre, String email, int imagem) {
        this.nome = nome;
        this.sobre = sobre;
        this.email = email;
        this.imagem = imagem;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
