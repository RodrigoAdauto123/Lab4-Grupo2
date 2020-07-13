package com.example.lab4_grupo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4_grupo2.EntidadesRuiz.Comentario;
import com.example.lab4_grupo2.EntidadesRuiz.Fotografia;
import com.example.lab4_grupo2.EntidadesRuiz.dtoComentario;
import com.example.lab4_grupo2.EntidadesRuiz.dtoFotografia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetallesActivity extends AppCompatActivity {

    String apikey;
    Fotografia[] listaFotografias;
    Comentario[] listaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        final Fotografia fotografia = new Fotografia();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        /// VALIDACION DE UN USUARIO LOGUEADO Y OBTENCION DE SU APIKEY





        databaseReference.child("usuarios").child(apikey).child("fotos").child(nombreInstancia).child("comentarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Comentario comentario = DataSnapshot.getValue(Comentario.class);
                    listaComentarios.add(comentario);
                    fotografia.setListaComentarios(listaComentarios);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        // CONTINUAR ACA EL RECYCLER VIEW
        dtoComentario dtoComentario = new dtoComentario();
        dtoComentario.setListaComentario(listaComentarios);
        ListaComentariosAdapter comentariosAdapter = new ListaComentariosAdapter(dtoComentario.getListaComentario(),DetallesActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(comentariosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetallesActivity.this));

        TextView autor = findViewById(R.id.textViewAutor); autor.setText(fotografia.getUsuarioCreador());
        // ImageView foto = findViewById(R.id.imageViewFotografia); foto.setText(fotografia.getUsuarioCreador());
        TextView descripcion = findViewById(R.id.textViewDescripcion); descripcion.setText(fotografia.getDescripcion());
        TextView fecha = findViewById(R.id.textViewFecha); fecha.setText(fotografia.getFechaSubida());
        TextView cantidadComentarios = findViewById(R.id.textViewComentarios); cantidadComentarios.setText(listaComentarios.length);






    }
}
