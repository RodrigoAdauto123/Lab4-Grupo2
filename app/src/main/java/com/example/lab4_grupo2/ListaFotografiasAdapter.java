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

public class ListaFotografiasAdapter extends RecyclerView.Adapter<ListaFotografiasAdapter.FotografiaViewHolder> {

    Fotografia[] listaFotografias;
    private Context contexto;

    public ListaFotografiasAdapter(Fotografia[] lista, Context contexto){
        this.listaFotografias = lista;
        this.contexto = contexto; }

        /// Setear todo lo que se va a repetir
    public static class FotografiaViewHolder extends RecyclerView.ViewHolder {

        public TextView autor;
        public ImageView fotografia;
        public TextView fechaSubida;
        public TextView cantidadComentarios;
        public TextView descripcion;

        public FotografiaViewHolder(@NonNull View itemView) {
            super(itemView);
            this.autor = itemView.findViewById(R.id.textViewAutor);
            this.fotografia = itemView.findViewById(R.id.imageViewFotografia);
            this.fechaSubida = itemView.findViewById(R.id.textViewFecha);
            this.cantidadComentarios = itemView.findViewById(R.id.textViewComentarios);
            this.descripcion = itemView.findViewById(R.id.textViewDescripcion);
        }
    }

    @NonNull
    @Override
    public FotografiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.item_rv, parent, false);
        FotografiaViewHolder fotografiaViewHolder = new FotografiaViewHolder(itemView);
        return fotografiaViewHolder; }

    @Override
    public void onBindViewHolder( FotografiaViewHolder holder, int position) {
        Fotografia fotografia = listaFotografias[position];

        String autor = fotografia.getUsuarioCreador();
        holder.autor.setText(autor);

        String foto = fotografia.getFotografia();
        // holder.fotografia.setImageDrawable(fotografia);

        String fecha = fotografia.getFechaSubida();
        holder.fechaSubida.setText(fecha);

        Comentario[] listaComentarios = fotografia.getListaComentarios();
        int cantidadComentarios = listaComentarios.length;
        holder.cantidadComentarios.setText(cantidadComentarios);

        String descripcion = fotografia.getDescripcion();
        holder.descripcion.setText(descripcion);

    }



    @Override
    public int getItemCount() {
        return listaFotografias.length;
    }
}
