package com.example.lab4_grupo2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_grupo2.entidades.Foto;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class SubirFotoActivity extends AppCompatActivity {

    ImageView imagen;
    Uri path;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);
        imagen = findViewById(R.id.imageViewFotoIncidencia);
    }


    public void cargarFoto(View view){

       Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
       intent.setType("image/");
       startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
        path =data.getData();
        imagen.setImageURI(path);

        }
    }

    public void guardarFoto(View view){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        String fileName ="img-" + id;
        StorageReference storageReference = firebaseStorage.getReference().child("Fotos-Usuario/"+ fileName);
        UploadTask uploadTask =storageReference.putFile(path);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("fotos").child("listafotos");
        String autor = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        EditText editText = (EditText) findViewById(R.id.editTextDescripcionIncidencia);
        String comentario = editText.getText().toString();
        Date fecha = new Date();
        String fechaString = fecha.toString();
        Foto foto = new Foto();
        foto.setNombre(fileName);
        foto.setAutor(autor);
        foto.setDescripcion(comentario);
        foto.setFecha(fechaString);
        databaseReference.setValue(foto);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SubirFotoActivity.this, "Subida Exitosa", Toast.LENGTH_SHORT).show();
            }
        });

      //  Intent intent = new Intent(this, );
      //  this.startActivity(intent);





    }




}
