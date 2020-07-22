package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Interface.RegistrarUnidadInterface;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarUnidadModel implements RegistrarUnidadInterface.Modelo {

    RegistrarUnidadInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public RegistrarUnidadModel(RegistrarUnidadInterface.TaskListener listener){
        this.listener = listener;
    }

    @Override
    public void mtdOnRegistrarUnidad(Unidad obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("unidad").push().setValue(obj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                listener.mtdOnSuccess();
            }
        });
    }
}
