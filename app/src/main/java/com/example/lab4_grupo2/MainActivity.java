package com.example.lab4_grupo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lab4_grupo2.entidades.Usuario;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG ="FacebookInfo";
    private FirebaseAuth mAuth;

    CallbackManager callbackManager = CallbackManager.Factory.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("infoApp","logueao");
                handleFacebookAccessToken(loginResult.getAccessToken());
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.setLanguageCode("es-419");
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser!=null) {

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    final String uid = currentUser.getUid();
                    final String name = currentUser.getDisplayName();
                    final String email = currentUser.getEmail();
                    Log.d("infoApp", databaseReference.child("usuario/" + uid).getDatabase().toString());


                    databaseReference.child("usuarios").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() == null) {
                                Log.d("infoApp2", "inicio de sesión exitoso!");
                                Usuario usuario = new Usuario();
                                usuario.setNombre(name);
                                usuario.setCorreo(email);
                                databaseReference.child("usuarios").child(uid).setValue(usuario);

                            }
                            if (dataSnapshot.getValue() != null) {

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancel() {
                Log.d("infoApp","cancel");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("infoApp","no loguin");
                // App code
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null){
            startActivity(new Intent(MainActivity.this,SubirFotoActivity.class));

            finish();

        }

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]

        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this,SubirFotoActivity.class));

                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        return true;
    }

    public void irSubirFoto(View view){
        Intent intent = new Intent(this, SubirFotoActivity.class);
        startActivity(intent);

    }

}
