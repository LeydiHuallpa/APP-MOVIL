package com.example.rikuwaapp.Vista.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rikuwaapp.Adapter.AdapterUnidad;
import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.LoginActivity;
import com.example.rikuwaapp.Vista.MapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UnidadFragment extends Fragment implements AdapterUnidad.EventoClick {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<Unidad> unidadList;
    DatabaseReference databaseReference;
    Button btnRegistrarUnidad;

    public UnidadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unidad, container, false);
        setViews(view);
        listarUnidads();
        return view;
    }

    private void setViews(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnRegistrarUnidad = view.findViewById(R.id.btnRegistrarUnidad);
        unidadList = new ArrayList<>();
        listarUnidads();
        recyclerView = view.findViewById(R.id.recycler_unidad);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterUnidad(unidadList, getActivity(),this);
        recyclerView.setAdapter(adapter);
        btnRegistrarUnidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarUnidadFragment registrarUnidadFragment = new RegistrarUnidadFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Helper.setFragmentManager(registrarUnidadFragment,fragmentManager);
            }
        });
    }

    public void listarUnidads() {
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

        String nombrePlaca = unidadList.get(posicion).getNombrePlaca();
        EditarUnidadFragment editarUnidadFragment = new EditarUnidadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("nombrePlaca", nombrePlaca);
        FragmentManager fragmentManager = getFragmentManager();
        editarUnidadFragment.setArguments(bundle);
        Helper.setFragmentManager(editarUnidadFragment,fragmentManager);
    }
}