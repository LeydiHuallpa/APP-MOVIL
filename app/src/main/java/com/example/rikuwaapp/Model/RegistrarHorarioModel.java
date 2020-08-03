package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.Interface.RegistrarHorarioInterface;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarHorarioModel implements RegistrarHorarioInterface.Modelo {
    RegistrarHorarioInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public RegistrarHorarioModel(RegistrarHorarioInterface.TaskListener listener){
        this.listener = listener;
    }

    @Override
    public void mtdOnRegistrarHorario(Horario obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Horarios").push().setValue(obj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                listener.mtdOnSuccess();
            }
        });
    }
}
