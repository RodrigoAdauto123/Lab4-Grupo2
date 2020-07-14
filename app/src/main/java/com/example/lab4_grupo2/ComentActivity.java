package com.example.lab4_grupo2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_grupo2.entidades.Comentario;
import com.example.lab4_grupo2.entidades.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);
       mAuth=FirebaseAuth.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarComentario (View view){
        TextView comentarioView= findViewById(R.id.comentario);
        String comentario=  comentarioView.getText().toString();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        Comentario comentarioClass = new Comentario();
        comentarioClass.setComentario(comentario);
        comentarioClass.setFecha(dtf.format(now));
        //a qué foto hizo ell comentario ,cantidad de comentarios, uid del usuario al que comenta su foto
       FirebaseUser usuario=FirebaseAuth.getInstance().getCurrentUser();
        comentarioClass.setAutor(usuario.getDisplayName());
        FirebaseDatabase.getInstance().getReference()
                .child("usuarios/"+usuario.getUid()+"/fotos/foto1/comentarios/comentario"+"2")
        .setValue(comentarioClass);
    }
    //menuuuuuuuu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
                        Toast.makeText(ComentActivity.this,"va bien",Toast.LENGTH_SHORT);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity( new Intent(ComentActivity.this,MainActivity.class));
                        return true;
                    default:
                        Log.d("infoapp","cerrando sesion");
                        Toast.makeText(ComentActivity.this,"va bien",Toast.LENGTH_SHORT);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity( new Intent(ComentActivity.this,MainActivity.class));
                        return true;

                }
            }
        });
        popupMenu.show();
    default:
        Toast.makeText(ComentActivity.this,"tampoco  va bien",Toast.LENGTH_SHORT);

}


        return super.onOptionsItemSelected(item);
    }






}