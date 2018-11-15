package com.vagner.tocaaquela.model;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class Sugestao implements Serializable {

    private String mensagem;
    private String idDoMusico;
    private String idDoUsuario;



    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdDoMusico() {
        return idDoMusico;
    }

    public void setIdDoMusico(String idDoMusico) {
        this.idDoMusico = idDoMusico;
    }

    public String getIdDoUsuario() {
        return idDoUsuario;
    }

    public void setIdDoUsuario(String idDoUsuario) {
        this.idDoUsuario = idDoUsuario;
    }
}
