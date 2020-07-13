package com.example.lab4_grupo2.EntidadesRuiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fotografia {

    private String usuarioCreador;
    private String fechaSubida;
    private String descripcion;
    private String fotografia;
    private Comentario[] listaComentarios;
    private int cantidadComentarios;


    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public Comentario[] getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(Comentario[] listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public int getCantidadComentarios() {
        return cantidadComentarios;
    }

    public void setCantidadComentarios(int cantidadComentarios) {
        this.cantidadComentarios = cantidadComentarios;
    }
}
