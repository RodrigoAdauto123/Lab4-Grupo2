package com.example.lab4_grupo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_grupo2.EntidadesRuiz.Comentario;
import com.example.lab4_grupo2.EntidadesRuiz.Fotografia;
import com.example.lab4_grupo2.EntidadesRuiz.dtoFotografia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    Fotografia fotografia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("fotos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Long longitudFotografias = (dataSnapshot.getChildrenCount());
                    int longitud = longitudFotografias.intValue();
                    listaFotografias = new Fotografia[longitud];
                    int i =0;
                    for (DataSnapshot children:dataSnapshot.getChildren() ){
                        if (dataSnapshot.exists()) {


                            fotografia=children.getValue(Fotografia.class);
                 /*           String autor = dataSnapshot.child("nombre").getValue().toString(); fotografia.setUsuarioCreador(autor);
                            // Revisar lo de la fecha
                            String fechaSubida = dataSnapshot.child("fechaSubida").getValue().toString(); fotografia.setFechaSubida(fechaSubida);
                            String descripcion = dataSnapshot.child("descripcion").getValue().toString(); fotografia.setDescripcion(descripcion);
                            String nombreFotografia = dataSnapshot.getKey(); fotografia.setDescripcion(descripcion); fotografia.setNombreFotografia(nombreFotografia);
                            // Falta agregar Fotografia !!!!!!!
*/
                            String nombreFotografiaFiltro =  dataSnapshot.getKey(); //nombreDeLaFotografia
                            databaseReference.child("fotos").child(nombreFotografiaFiltro).child("comentarios").addValueEventListener(new ValueEventListener() {
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

                                                // Codigo Porsiacaso
                                            //    String fechaComentario = dataSnapshot.child("fecha").getValue().toString(); comentario.setFechaComentario(fechaComentario);
                                              //  String horaComentario = dataSnapshot.child("hora").getValue().toString(); comentario.setHoraComentario(horaComentario);
                                                // String autor = dataSnapshot.child("autor").getValue().toString();
                                                // String descripcion = dataSnapshot.child("descripcion").getValue().toString();

                                                listaComentarios[inicial] = comentario;
                                                inicial++;
                                            } //final del IF
                                        } // final del FOR
                                        fotografia.setListaComentarios(listaComentarios);
                                    } }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(PantallaPrincipalActivity.this,"No existen comentarios en la base de datos.",Toast.LENGTH_LONG).show();
                                } });

                            listaFotografias[i] = fotografia;
                            i++; }

                    } // final del FOR
                } // final del IF EXSIST
            } // final del ONDATA

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PantallaPrincipalActivity.this,"No existe fotos en la base de datos.",Toast.LENGTH_LONG).show();
            }
        }); // FINAL DE TODA LA REFERENCIA FIREBASE

        // RECYCLER VIEW
        dtoFotografia dtoFotografia = new dtoFotografia();
        dtoFotografia.setListaFotografia(listaFotografias);
        ListaFotografiasAdapter fotografiasAdapter = new ListaFotografiasAdapter(listaFotografias,PantallaPrincipalActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(fotografiasAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PantallaPrincipalActivity.this));

        // BUTTON DETALLES
 /*       Button botonDetalles = (Button) findViewById(R.id.buttonDetalles);
        botonDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                TextView nombre = findViewById(R.id.textViewNombre);
                String nombreFotografia = nombre.getText().toString();
                intent.putExtra("nombreFotografia", nombreFotografia );
                startActivity(intent);
            }
        });*/




    }
    ///Menuuuuuuuu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        menu.findItem(R.id.nombreUsuario).setTitle(currentUser.getDisplayName());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nombreUsuario:
                View menuitem = findViewById(R.id.nombreUsuario);
                PopupMenu popupMenu=new PopupMenu(this, menuitem);
                popupMenu.getMenuInflater().inflate(R.menu.topmenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.cerrarcesion:
                                Log.d("infoapp","cerrando sesion");

                                FirebaseAuth.getInstance().signOut();
                                finish();
                                startActivity( new Intent(PantallaPrincipalActivity.this,MainActivity.class));
                                return true;
                            default:
                                Log.d("infoapp","cerrando sesion");

                                FirebaseAuth.getInstance().signOut();
                                finish();
                                startActivity( new Intent(PantallaPrincipalActivity.this,MainActivity.class));
                                return true;

                        }
                    }
                });
                popupMenu.show();
            default:

        }


        return super.onOptionsItemSelected(item);
    }



}
