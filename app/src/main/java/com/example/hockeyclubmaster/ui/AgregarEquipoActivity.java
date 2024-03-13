package com.example.hockeyclubmaster.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hockeyclubmaster.Adapter.EquiposAdapter;
import com.example.hockeyclubmaster.R;
import com.example.hockeyclubmaster.model.Equipo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AgregarEquipoActivity extends AppCompatActivity {

    private EditText editTextTeamName;
    private EditText editTextCoachUID;
    private Button buttonAddTeam;
    private ConstraintLayout backHome;
    private FirebaseFirestore db;
    private RecyclerView recyclerViewTeams;
    private EquiposAdapter equiposAdapter;
    private List<Equipo> equiposList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo);


        backHome = findViewById(R.id.backHome);
        editTextTeamName = findViewById(R.id.editTextTeamName);
        editTextCoachUID = findViewById(R.id.editTextTeamName2);
        buttonAddTeam = findViewById(R.id.buttonAddTeam);
        recyclerViewTeams = findViewById(R.id.recyclerViewTeams);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Inicializar RecyclerView y su adaptador
        equiposList = new ArrayList<>();
        equiposAdapter = new EquiposAdapter(this, equiposList);
        recyclerViewTeams.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTeams.setAdapter(equiposAdapter);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarEquipoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        buttonAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEquipo();
            }
        });

        // Mostrar los equipos al inicio
        mostrarEquipos();
    }

    private void agregarEquipo() {
        String categoria = editTextTeamName.getText().toString().trim();
        String UID_entrenador = editTextCoachUID.getText().toString().trim();

        if (categoria.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo objeto Equipo
        Equipo equipo = new Equipo(categoria, UID_entrenador);

        // Agregar el equipo a Firestore con el nombre del documento como la categoría
        db.collection("equipos")
                .document(categoria) // Utiliza la categoría como nombre del documento
                .set(equipo)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Equipo agregado correctamente", Toast.LENGTH_SHORT).show();
                    editTextTeamName.setText("");
                    editTextCoachUID.setText("");
                    // Mostrar los equipos después de agregar uno nuevo
                    mostrarEquipos();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al agregar equipo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void mostrarEquipos() {
        db.collection("equipos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    equiposList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Equipo equipo = documentSnapshot.toObject(Equipo.class);
                        equiposList.add(equipo);
                    }
                    equiposAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al obtener equipos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
