package com.example.lab4_grupo2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
        String id = "FirebaseAuth.getInstance().getCurrentUser().getUid()";
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        String fileName ="img-" + id;
        StorageReference storageReference = firebaseStorage.getReference().child("Fotos-Usuario/"+ fileName);
        UploadTask uploadTask =storageReference.putFile(path);

      //  Intent intent = new Intent(this, );
      //  this.startActivity(intent);





    }




}
