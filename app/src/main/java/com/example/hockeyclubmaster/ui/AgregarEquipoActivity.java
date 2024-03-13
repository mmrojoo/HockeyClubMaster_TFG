package com.example.hockeyclubmaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hockeyclubmaster.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AgregarEquipoActivity extends AppCompatActivity {

    private EditText editTextTeamName;
    private EditText editTextCoachUID;
    private Button buttonAddTeam;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo);

        editTextTeamName = findViewById(R.id.editTextTeamName);
        editTextCoachUID = findViewById(R.id.editTextTeamName2);
        buttonAddTeam = findViewById(R.id.buttonAddTeam);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        buttonAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEquipo();
            }
        });
    }

    private void agregarEquipo() {
        String categoria = editTextTeamName.getText().toString().trim();
        String UID_entrenador = editTextCoachUID.getText().toString().trim();

        if (categoria.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo mapa para almacenar los datos del equipo
        Map<String, Object> equipo = new HashMap<>();
        equipo.put("categoria", categoria);
        equipo.put("UID_entrenador", UID_entrenador);

        // Agregar el equipo a Firestore con el nombre del documento como la categoría
        db.collection("equipos")
                .document(categoria) // Utiliza la categoría como nombre del documento
                .set(equipo)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Equipo agregado correctamente", Toast.LENGTH_SHORT).show();
                    editTextTeamName.setText("");
                    editTextCoachUID.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al agregar equipo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
