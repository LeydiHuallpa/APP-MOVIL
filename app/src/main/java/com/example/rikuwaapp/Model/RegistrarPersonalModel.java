package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rikuwaapp.Entidad.Personal;
import com.example.rikuwaapp.Interface.RegistrarPersonalInterface;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarPersonalModel implements RegistrarPersonalInterface.Modelo {
    RegistrarPersonalInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public RegistrarPersonalModel(RegistrarPersonalInterface.TaskListener listener){
        this.listener = listener;
    }

    @Override
    public void mtdOnRegistrarPersonal(Personal obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Personals").push().setValue(obj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                listener.mtdOnSuccess();
            }
        });
    }
}
