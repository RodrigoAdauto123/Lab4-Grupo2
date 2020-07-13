package com.example.lab4_grupo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_grupo2.EntidadesRuiz.Comentario;
import com.example.lab4_grupo2.EntidadesRuiz.Fotografia;

import java.util.ArrayList;
import java.util.Date;

public class ListaComentariosAdapter extends RecyclerView.Adapter<ListaComentariosAdapter.FotografiaViewHolder> {

    Comentario[] listaComentario;
    private Context contexto;

    public ListaComentariosAdapter(Comentario[] lista, Context contexto){
        this.listaComentario = lista;
        this.contexto = contexto; }

    /// Setear todo lo que se va a repetir
    public static class FotografiaViewHolder extends RecyclerView.ViewHolder {

        public TextView autor;
        public TextView fechaSubida;
        public TextView horaSubida;
        public TextView descripcion;

        public FotografiaViewHolder(@NonNull View itemView) {
            super(itemView);
            this.autor = itemView.findViewById(R.id.textViewAutor);
            this.fechaSubida = itemView.findViewById(R.id.textViewFecha);
            this.horaSubida = itemView.findViewById(R.id.textViewHora);
            this.descripcion = itemView.findViewById(R.id.textViewDescripcion);
        }
    }


    @NonNull
    @Override
    public FotografiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.item_rv2, parent, false);
        FotografiaViewHolder fotografiaViewHolder = new FotografiaViewHolder(itemView);
        return fotografiaViewHolder; }

    @Override
    public void onBindViewHolder( FotografiaViewHolder holder, int position) {
        Comentario comentario = listaComentario[position];

        String autor = comentario.getAutorComentario();
        holder.autor.setText(autor);

        String fecha = comentario.getFechaComentario();
        holder.fechaSubida.setText(fecha);

        String hora = comentario.getHoraComentario();
        holder.horaSubida.setText(hora);

        String descripcion = comentario.getAutorComentario();
        holder.descripcion.setText(descripcion);

    }

    @Override
    public int getItemCount() {
        return listaComentario.length;
    }
}
