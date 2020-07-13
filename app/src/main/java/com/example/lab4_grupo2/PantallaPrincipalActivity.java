package com.example.lab4_grupo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.lab4_grupo2.EntidadesRuiz.Comentario;
import com.example.lab4_grupo2.EntidadesRuiz.Fotografia;
import com.example.lab4_grupo2.EntidadesRuiz.dtoFotografia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PantallaPrincipalActivity extends AppCompatActivity {

    String apikey;
    String key;
    Fotografia[] listaFotografias;
    Comentario[] listaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        final Fotografia fotografia = new Fotografia();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        /// VALIDACION DE UN USUARIO LOGUEADO Y OBTENCION DE SU APIKEY

        databaseReference.child("usuarios").child(apikey).child("fotos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String nombreInstancia = DataSnapshot.child(key).getValue().toString();

                    String autor = DataSnapshot.child("nombre").getValue().toString();
                    fotografia.setUsuarioCreador(autor);
                    //
                    String fechaSubida = DataSnapshot.child("fechaSubida").getValue().toString();
                    fotografia.setFechaSubida(fechaSubida);
                    //
                    String descripcion = DataSnapshot.child("descripcion").getValue().toString();
                    fotografia.setDescripcion(descripcion);
                    //
                    String foto = DataSnapshot.child("fotografia").getValue().toString();
                    fotografia.setFotografia(foto);

                databaseReference.child("usuarios").child(apikey).child("fotos").child(nombreInstancia).child("comentarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            Comentario comentario = DataSnapshot.getValue(Comentario.class);
                            listaComentarios.equals(comentario);
                            fotografia.setListaComentarios(listaComentarios);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                // Se genera la lista de todas las fotografias:
                listaFotografias.add(fotografia); }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}


        }); // FIN DATABASE REFERENCE


        // CONTINUAR ACA EL RECYCLER VIEW
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
                intent.putExtra("nombreFoto", key );
                startActivity(intent);
            }
        });




    }
}
