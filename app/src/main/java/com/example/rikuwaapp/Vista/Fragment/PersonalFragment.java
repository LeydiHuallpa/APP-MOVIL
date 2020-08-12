package com.example.rikuwaapp.Vista.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rikuwaapp.Adapter.AdapterPersonal;
import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.Entidad.Personal;
import com.example.rikuwaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment implements AdapterPersonal.EventoClick {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<Personal> PersonalList;
    DatabaseReference databaseReference;
    Button btnRegistrarPersonal;

    public PersonalFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnRegistrarPersonal = view.findViewById(R.id.btnRegistrarPersonal);
        PersonalList = new ArrayList<>();
        listarPersonals();
        recyclerView = view.findViewById(R.id.recycler_Personal);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterPersonal(PersonalList, getActivity(),this);
        recyclerView.setAdapter(adapter);
        btnRegistrarPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarPersonalFragment registrarPersonalFragment = new RegistrarPersonalFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Helper.setFragmentManager(registrarPersonalFragment,fragmentManager);
            }
        });
    }

    private void listarPersonals() {
        databaseReference.child("Personals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PersonalList.clear();
                for (DataSnapshot obj : snapshot.getChildren()) {
                    PersonalList.add(obj.getValue(Personal.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterPersonal.ViewHolder holder, int posicion) {
        String nombreCompleto = PersonalList.get(posicion).getNombreCompleto();
        EditarPersonalFragment editarPersonalFragment = new EditarPersonalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("nombreCompleto", nombreCompleto);
        FragmentManager fragmentManager = getFragmentManager();
        editarPersonalFragment.setArguments(bundle);
        Helper.setFragmentManager(editarPersonalFragment,fragmentManager);
    }
}
