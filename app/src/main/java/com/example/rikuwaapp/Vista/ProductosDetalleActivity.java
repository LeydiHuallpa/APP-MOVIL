package com.example.rikuwaapp.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rikuwaapp.Adapter.AdapterProductoDetalle;
import com.example.rikuwaapp.Adapter.AdapterUnidad;
import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductosDetalleActivity extends AppCompatActivity {

    String nombreUnidad;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<Producto> productoList;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_detalle);
        setViews();
    }

    private void setViews() {
        Bundle b = getIntent().getExtras();
        nombreUnidad = b.getString("nombreUnidad");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        productoList = new ArrayList<>();
        cargarProductos();
        recyclerView = findViewById(R.id.recycler_productos_detalle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterProductoDetalle(productoList, this);
        recyclerView.setAdapter(adapter);
    }

    private void cargarProductos(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("productos").orderByChild("nombreUnidad").equalTo(nombreUnidad);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    productoList.add(obj.getValue(Producto.class));
                    Toast.makeText(ProductosDetalleActivity.this, obj.getValue(Producto.class).getImagenReferencial(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ProductosDetalleActivity.this, String.valueOf(productoList.size()), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}