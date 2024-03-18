package com.example.hockeyclubmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hockeyclubmaster.Adapter.TrendsAdapter;
import com.example.hockeyclubmaster.Domain.TrendsDomain;
import com.example.hockeyclubmaster.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterTrendsList;
    private RecyclerView recyclerViewTrends;
    private LinearLayout profileBtn;
    private LinearLayout equipoLinearLayout;
    private TextView textoNombreUsuario;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Inicializar las vistas
        textoNombreUsuario = findViewById(R.id.textoNombreUsuario);
        profileBtn = findViewById(R.id.profileBtn);
        equipoLinearLayout = findViewById(R.id.equipoLinearLayout);

        // Configurar listeners de botones
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        equipoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarEquipoActivity.class);
                startActivity(intent);
            }
        });

        // Inicializar RecyclerView
        initRecyclerView();

        // Obtener y mostrar el nombre del usuario
        obtenerNombreUsuario();
    }

    private void initRecyclerView() {
        ArrayList<TrendsDomain> items = new ArrayList<>();

        items.add(new TrendsDomain("El primera autonómica asciende a OK Bronce", "Por chpaluche, hace 10 meses", "trends"));
        items.add(new TrendsDomain("Gestas de España, nuevo patrocinador del Club Hockey Patín Aluche", "Por chpaluche, hace 9 meses", "trends2"));
        items.add(new TrendsDomain("Conoce nuestra nueva página web", "Por chpaluche, hace 2 años", "trends3"));

        recyclerViewTrends = findViewById(R.id.view1);
        recyclerViewTrends.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterTrendsList = new TrendsAdapter(items);
        recyclerViewTrends.setAdapter(adapterTrendsList);
    }
    private void obtenerNombreUsuario() {
        if (user != null) {
            String userId = user.getUid();
            db.collection("usuarios").document(userId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String nombre = documentSnapshot.getString("nombre");
                                if (nombre != null && !nombre.isEmpty()) {
                                    textoNombreUsuario.setText(nombre);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar errores de acceso a Firestore
                        }
                    });
        }
    }
}
