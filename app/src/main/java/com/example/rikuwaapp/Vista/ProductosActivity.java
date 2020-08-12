package com.example.rikuwaapp.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rikuwaapp.Adapter.AdapterUnidad;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity implements AdapterUnidad.EventoClick {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<Unidad> unidadList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        setViews();
    }

    private void setViews() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        unidadList = new ArrayList<>();
        listarUnidads();
        recyclerView = findViewById(R.id.recycler_productos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterUnidad(unidadList, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void listarUnidads() {
        databaseReference.child("unidad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unidadList.clear();
                for (DataSnapshot obj : snapshot.getChildren()) {
                    unidadList.add(obj.getValue(Unidad.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterUnidad.ViewHolder holder, int posicion) {
        String nombreUnidad = unidadList.get(posicion).getNombreUnidad();
//        Toast.makeText(this, nombreUnidad, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ProductosActivity.this, ProductosDetalleActivity.class);
        i.putExtra("nombreUnidad", nombreUnidad);
        startActivity(i);
    }
}