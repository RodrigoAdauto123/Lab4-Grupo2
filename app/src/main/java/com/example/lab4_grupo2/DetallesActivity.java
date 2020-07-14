package com.example.lab4_grupo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_grupo2.EntidadesRuiz.Comentario;
import com.example.lab4_grupo2.EntidadesRuiz.Fotografia;
import com.example.lab4_grupo2.EntidadesRuiz.dtoComentario;
import com.example.lab4_grupo2.EntidadesRuiz.dtoFotografia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetallesActivity extends AppCompatActivity {

    
    Fotografia[] listaFotografias;
    Comentario[] listaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        final Fotografia fotografia = new Fotografia();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        
        // Obtencio del parametro enviado: NombreFotografia
        final String nombreFotografia = getIntent().getStringExtra("nombreFotografia");

        databaseReference.child("fotos").child(nombreFotografia).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String autor = dataSnapshot.child("nombre").getValue().toString(); fotografia.setUsuarioCreador(autor);
                    String fechaSubida = dataSnapshot.child("fechaSubida").getValue().toString(); fotografia.setFechaSubida(fechaSubida);
                    String descripcion = dataSnapshot.child("descripcion").getValue().toString(); fotografia.setDescripcion(descripcion);
                    String nombreFotografia = dataSnapshot.getKey();; fotografia.setDescripcion(descripcion); fotografia.setNombreFotografia(nombreFotografia);
                    // Falta agregar Fotografia !!!!!!!
                } }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {  Toast.makeText(DetallesActivity.this,"No existe fotos en la base de datos.",Toast.LENGTH_LONG).show();
            } });


        databaseReference.child("lista").child(nombreFotografia).child("comentarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Long longitudComentarios = (dataSnapshot.getChildrenCount());
                    int longitud2 = longitudComentarios.intValue();
                    listaComentarios = new Comentario[longitud2];

                    int inicial = 0;
                    for (DataSnapshot children:dataSnapshot.getChildren()){
                        if (dataSnapshot.exists()) {
                            Comentario comentario = dataSnapshot.getValue(Comentario.class);
                            String fechaComentario = dataSnapshot.child("fecha").getValue().toString(); comentario.setFechaComentario(fechaComentario);
                            String horaComentario = dataSnapshot.child("hora").getValue().toString(); comentario.setHoraComentario(horaComentario);
                            listaComentarios[inicial] = comentario;
                            inicial++;
                        } //final del IF
                    } // final del FOR
                    fotografia.setListaComentarios(listaComentarios);
                } }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetallesActivity.this,"No existen comentarios en la base de datos.",Toast.LENGTH_LONG).show();
            } });



        // EL RECYCLER VIEW
        dtoComentario dtoComentario = new dtoComentario();
        dtoComentario.setListaComentario(listaComentarios);
        ListaComentariosAdapter comentariosAdapter = new ListaComentariosAdapter(dtoComentario.getListaComentario(),DetallesActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(comentariosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetallesActivity.this));

        TextView autor = findViewById(R.id.textViewAutor); autor.setText(fotografia.getUsuarioCreador());
        // ImageView foto = findViewById(R.id.imageViewFotografia); foto.setText(fotografia.getUsuarioCreador()); // AGREGAR FOTOGRAFIA!!!!!
        TextView descripcion = findViewById(R.id.textViewDescripcion); descripcion.setText(fotografia.getDescripcion());
        TextView fecha = findViewById(R.id.textViewFecha); fecha.setText(fotografia.getFechaSubida());
        TextView cantidadComentarios = findViewById(R.id.textViewComentarios); cantidadComentarios.setText(listaComentarios.length);

    }


    public void ingresarComentario(View view){
        Intent intent = new Intent(this,ComentActivity.class);
        startActivity(intent);

    }

}

