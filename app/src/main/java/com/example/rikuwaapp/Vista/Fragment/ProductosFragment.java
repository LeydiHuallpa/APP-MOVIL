package com.example.rikuwaapp.Vista.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rikuwaapp.Adapter.AdapterProducto;
import com.example.rikuwaapp.Adapter.AdapterUnidad;
import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductosFragment extends Fragment implements AdapterProducto.EventoClick {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<Producto> productoList;
    DatabaseReference databaseReference;
    Button btnRegistrarProducto;

    public ProductosFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        productoList = new ArrayList<>();
        listarProductos();
        recyclerView = view.findViewById(R.id.recycler_productos);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterProducto(productoList, getActivity(),this);
        recyclerView.setAdapter(adapter);

        btnRegistrarProducto = view.findViewById(R.id.btnRegistrarProducto);

        btnRegistrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarProductoFragment registrarUnidadFragment = new RegistrarProductoFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Helper.setFragmentManager(registrarUnidadFragment,fragmentManager);
            }
        });
    }

    private void listarProductos() {
        databaseReference.child("productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoList.clear();
                for (DataSnapshot obj : snapshot.getChildren()) {
                    productoList.add(obj.getValue(Producto.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterProducto.ViewHolder holder, int posicion) {
        String nombreProducto = productoList.get(posicion).getNombreProducto();
        EditarProductoFragment editarProductoFragment = new EditarProductoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("nombreProducto", nombreProducto);
        FragmentManager fragmentManager = getFragmentManager();
        editarProductoFragment.setArguments(bundle);
        Helper.setFragmentManager(editarProductoFragment,fragmentManager);
    }
}
