package com.example.hockeyclubmaster.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hockeyclubmaster.Adapter.TrendsAdapter;
import com.example.hockeyclubmaster.Domain.TrendsDomain;
import com.example.hockeyclubmaster.R;
import com.google.android.gms.ads.mediation.Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterTrendsList;
    private RecyclerView recyclerViewTrends;
    private LinearLayout profileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<TrendsDomain> items = new ArrayList<>();

        items.add(new TrendsDomain("El primera autonómica asciende a OK Bronce", "Por chpaluche, hace 10 meses", "trends"));
        items.add(new TrendsDomain("Gestas de España, nuevo patrocinador del Club Hockey Patín Aluche", "Por chpaluche, hace 9 meses", "trends2"));
        items.add(new TrendsDomain("Conoce nuestra nueva página web", "Por chpaluche, hace 2 años", "trends3"));

        recyclerViewTrends=findViewById(R.id.view1);
        recyclerViewTrends.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterTrendsList = new TrendsAdapter(items);
        recyclerViewTrends.setAdapter(adapterTrendsList);
    }
}
