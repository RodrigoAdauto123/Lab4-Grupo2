package com.example.lab4_grupo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_grupo2.EntidadesRuiz.Comentario;
import com.example.lab4_grupo2.EntidadesRuiz.Fotografia;
import com.example.lab4_grupo2.EntidadesRuiz.dtoFotografia;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaPrincipalActivity extends AppCompatActivity {

    String apikey;
    String key;

    Comentario[] listaComentarios;
    Fotografia[] listaFotografias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("fotos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Long longitudFotografias = (dataSnapshot.getChildrenCount());
                    int longitud = longitudFotografias.intValue();
                    listaFotografias = new Fotografia[longitud];
                    int i = 0;
                    for (DataSnapshot children : dataSnapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            final Fotografia fotografia = dataSnapshot.getValue(Fotografia.class);
                            //String autor = dataSnapshot.child("nombre").getValue().toString();
                            //fotografia.setUsuarioCreador(autor);
                            // Revisar lo de la fecha
                            //String fechaSubida = dataSnapshot.child("fechaSubida").getValue().toString();
                            // fotografia.setFechaSubida(fechaSubida);
                            //String descripcion = dataSnapshot.child("descripcion").getValue().toString();
                            //fotografia.setDescripcion(descripcion);
                            String nombreFotografia = dataSnapshot.getKey();
                            fotografia.setNombreFotografia(nombreFotografia);
                            // Falta agregar Fotografia !!!!!!!

                            String nombreFotografiaFiltro = dataSnapshot.getKey(); //nombreDeLaFotografia
                            databaseReference.child("lista").child(nombreFotografiaFiltro).child("comentarios").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Long longitudComentarios = (dataSnapshot.getChildrenCount());
                                        int longitud2 = longitudComentarios.intValue();
                                        listaComentarios = new Comentario[longitud2];

                                        int inicial = 0;
                                        for (DataSnapshot children : dataSnapshot.getChildren()) {
                                            if (dataSnapshot.exists()) {
                                                Comentario comentario = dataSnapshot.getValue(Comentario.class);

                                                // Codigo Porsiacaso
                                                String fechaComentario = dataSnapshot.child("fecha").getValue().toString();
                                                comentario.setFechaComentario(fechaComentario);
                                                String horaComentario = dataSnapshot.child("hora").getValue().toString();
                                                comentario.setHoraComentario(horaComentario);
                                                // String autor = dataSnapshot.child("autor").getValue().toString();
                                                // String descripcion = dataSnapshot.child("descripcion").getValue().toString();

                                                listaComentarios[inicial] = comentario;
                                                inicial++;
                                            } //final del IF
                                        } // final del FOR
                                        fotografia.setListaComentarios(listaComentarios);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(PantallaPrincipalActivity.this, "No existen comentarios en la base de datos.", Toast.LENGTH_LONG).show();
                                }
                            });

                            listaFotografias[i] = fotografia;
                            i++;
                        }

                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Long longitudFotografias = (dataSnapshot.getChildrenCount());
                    int longitud = longitudFotografias.intValue();
                    listaFotografias = new Fotografia[longitud];
                    int i = 0;
                    for (DataSnapshot children : dataSnapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            final Fotografia fotografia = dataSnapshot.getValue(Fotografia.class);
                            //String autor = dataSnapshot.child("nombre").getValue().toString();
                            //fotografia.setUsuarioCreador(autor);
                            // Revisar lo de la fecha
                            //String fechaSubida = dataSnapshot.child("fechaSubida").getValue().toString();
                            // fotografia.setFechaSubida(fechaSubida);
                            //String descripcion = dataSnapshot.child("descripcion").getValue().toString();
                            //fotografia.setDescripcion(descripcion);
                            String nombreFotografia = dataSnapshot.getKey();
                            fotografia.setNombreFotografia(nombreFotografia);
                            // Falta agregar Fotografia !!!!!!!

                            String nombreFotografiaFiltro = dataSnapshot.getKey(); //nombreDeLaFotografia
                            databaseReference.child("lista").child(nombreFotografiaFiltro).child("comentarios").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Long longitudComentarios = (dataSnapshot.getChildrenCount());
                                        int longitud2 = longitudComentarios.intValue();
                                        listaComentarios = new Comentario[longitud2];

                                        int inicial = 0;
                                        for (DataSnapshot children : dataSnapshot.getChildren()) {
                                            if (dataSnapshot.exists()) {
                                                Comentario comentario = dataSnapshot.getValue(Comentario.class);

                                                // Codigo Porsiacaso
                                                String fechaComentario = dataSnapshot.child("fecha").getValue().toString();
                                                comentario.setFechaComentario(fechaComentario);
                                                String horaComentario = dataSnapshot.child("hora").getValue().toString();
                                                comentario.setHoraComentario(horaComentario);
                                                // String autor = dataSnapshot.child("autor").getValue().toString();
                                                // String descripcion = dataSnapshot.child("descripcion").getValue().toString();

                                                listaComentarios[inicial] = comentario;
                                                inicial++;
                                            } //final del IF
                                        } // final del FOR
                                        fotografia.setListaComentarios(listaComentarios);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(PantallaPrincipalActivity.this, "No existen comentarios en la base de datos.", Toast.LENGTH_LONG).show();
                                }
                            });

                            listaFotografias[i] = fotografia;
                            i++;
                        }

                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        // RECYCLER VIEW
        dtoFotografia dtoFotografia = new dtoFotografia();
        dtoFotografia.setListaFotografia(listaFotografias);
        ListaFotografiasAdapter fotografiasAdapter = new ListaFotografiasAdapter(listaFotografias,PantallaPrincipalActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(fotografiasAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PantallaPrincipalActivity.this));

        // BUTTON DETALLES
        Button botonDetalles = (Button) findViewById(R.id.buttonDetalles);
        botonDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                TextView nombre = findViewById(R.id.textViewNombre);
                String nombreFotografia = nombre.getText().toString();
                intent.putExtra("nombreFotografia", nombreFotografia );
                startActivity(intent);
            }
        });




    }
}
