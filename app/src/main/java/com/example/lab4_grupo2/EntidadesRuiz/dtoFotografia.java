package com.example.lab4_grupo2.EntidadesRuiz;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class dtoFotografia {
    private Fotografia[] listaFotografia;
    private String estado;



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Fotografia[] getListaFotografia() {
        return listaFotografia;
    }

    public void setListaFotografia(Fotografia[] listaFotografia) {
        this.listaFotografia = listaFotografia;
    }
}
