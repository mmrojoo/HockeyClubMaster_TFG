package com.example.hockeyclubmaster.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.hockeyclubmaster.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ConstraintLayout logOut;
    private ConstraintLayout editProfile;
    private ConstraintLayout backHome;
    private TextView textNombreUsuario;
    private TextView textCorreo;


    private ImageView fotoPerfil;
    private static final int PICK_IMAGE_REQUEST = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();



        fotoPerfil = findViewById(R.id.fotoPerfil);
        textNombreUsuario = findViewById(R.id.textNombreUsuario);
        textCorreo = findViewById(R.id.textCorreo);
        editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        backHome = findViewById(R.id.backHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //Metodo para cerrar sesion
    private void logout() {
        mAuth.signOut();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    //Metodo para abrir la galeria
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap resizedBitmap = resizeBitmap(bitmap, fotoPerfil.getWidth(), fotoPerfil.getHeight());

                // Aquí puedes establecer la imagen en tu ImageView
                fotoPerfil.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        // Escalar la imagen para que sea del mismo tamaño que el ImageView
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        // Crear un Bitmap circular
        Bitmap circularBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Crear un Canvas con el Bitmap circular
        android.graphics.Canvas canvas = new android.graphics.Canvas(circularBitmap);
        // Dibujar un círculo con el Bitmap redimensionado
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawCircle(width / 2f, height / 2f, Math.min(width, height) / 2f, paint);
        // Establecer la opción de pintura de destino
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        // Dibujar el Bitmap redimensionado en el Bitmap circular
        canvas.drawBitmap(resizedBitmap, 0f, 0f, paint);
        return circularBitmap;
    }
}