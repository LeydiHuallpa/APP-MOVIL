package com.example.rikuwaapp.Vista.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rikuwaapp.Adapter.AdapterHorario;
import com.example.rikuwaapp.Adapter.AdapterUnidad;
import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HorarioFragment extends Fragment implements AdapterHorario.EventoClick {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<Unidad> unidadList;
    List<Horario> horarioList;
    DatabaseReference databaseReference;
    Button btnRegistrarHorario;

    public HorarioFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horario, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnRegistrarHorario = view.findViewById(R.id.btnRegistrarHorario);
        horarioList = new ArrayList<>();
//        listarUnidads();
        listarHorarios();
        recyclerView = view.findViewById(R.id.recycler_horario);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterHorario(horarioList, getActivity(),this);
        recyclerView.setAdapter(adapter);
        btnRegistrarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarHorarioFragment registrarHorarioFragment = new RegistrarHorarioFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Helper.setFragmentManager(registrarHorarioFragment,fragmentManager);
            }
        });
    }

//    private void listarUnidads() {
//        databaseReference.child("unidad").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                unidadList.clear();
//                for (DataSnapshot obj : snapshot.getChildren()) {
//                    unidadList.add(obj.getValue(Unidad.class));
//                }
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void listarHorarios(){
        databaseReference.child("Horarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                horarioList.clear();
                for (DataSnapshot obj : snapshot.getChildren()) {
                    horarioList.add(obj.getValue(Horario.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    @Override
//    public void onItemClick(AdapterUnidad.ViewHolder holder, int posicion) {
//        String nombreUnidad = unidadList.get(posicion).getNombreUnidad();
//        EditarHorarioFragment editarHorarioFragment = new EditarHorarioFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("nombreUnidad", nombreUnidad);
//        FragmentManager fragmentManager = getFragmentManager();
//        editarHorarioFragment.setArguments(bundle);
//        Helper.setFragmentManager(editarHorarioFragment,fragmentManager);
//    }

    @Override
    public void onItemClick(AdapterHorario.ViewHolder holder, int posicion) {
        String nombreUnidad = horarioList.get(posicion).getNombreUnidad();
        EditarHorarioFragment editarHorarioFragment = new EditarHorarioFragment();
        Bundle bundle = new Bundle();
        bundle.putString("nombreUnidad", nombreUnidad);
        FragmentManager fragmentManager = getFragmentManager();
        editarHorarioFragment.setArguments(bundle);
        Helper.setFragmentManager(editarHorarioFragment,fragmentManager);
    }
}
