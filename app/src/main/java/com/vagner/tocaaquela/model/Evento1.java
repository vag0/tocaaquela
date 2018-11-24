package com.vagner.tocaaquela.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
    public class Evento1 {
        private String evento1Id;
        private String evento1Nome;
        private String evento1Genero;
        private String evento1Dia;
        private String evento1Horario;

        public Evento1(){

        }

        public Evento1(String evento1Id, String evento1Nome, String evento1Genero, String evento1Dia, String evento1Horario) {
            this.evento1Id = evento1Id;
            this.evento1Nome = evento1Nome;
            this.evento1Genero = evento1Genero;
            this.evento1Dia = evento1Dia;
            this.evento1Horario = evento1Horario;
        }

        public String getEvento1Id() {
            return evento1Id;
        }

        public String getEvento1Nome() {
            return evento1Nome;
        }

        public String getEvento1Genero() {
            return evento1Genero;
        }


        public String getEvento1Dia() {
            return evento1Dia;
        }

        public String getEvento1Horario() {
            return evento1Horario;
        }
}
