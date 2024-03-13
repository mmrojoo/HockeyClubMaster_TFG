package com.example.hockeyclubmaster.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hockeyclubmaster.R;
import com.example.hockeyclubmaster.model.Equipo;

import java.util.List;

public class EquiposAdapter extends RecyclerView.Adapter<EquiposAdapter.ViewHolder> {

        private Context context;
        private List<Equipo> equiposList;

        public EquiposAdapter(Context context, List<Equipo> equiposList) {
                this.context = context;
                this.equiposList = equiposList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_equipo, parent, false);
                return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Equipo equipo = equiposList.get(position);
                holder.bind(equipo);
        }

        @Override
        public int getItemCount() {
                return equiposList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

                private TextView textViewCategoria;
                private TextView textViewUIDEntrenador;

                public ViewHolder(@NonNull View itemView) {
                        super(itemView);
                        textViewCategoria = itemView.findViewById(R.id.textViewCategoria);
                        textViewUIDEntrenador = itemView.findViewById(R.id.textViewUIDEntrenador);
                }

                public void bind(Equipo equipo) {
                        textViewCategoria.setText("Categoría: " + equipo.getCategoria());
                        textViewUIDEntrenador.setText("UID del Entrenador: " + equipo.getUID_entrenador());
                }
        }
}
