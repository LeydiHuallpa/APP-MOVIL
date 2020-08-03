package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Interface.RegistrarProductoInterface;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarProductoModel implements RegistrarProductoInterface.Modelo {

    RegistrarProductoInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public RegistrarProductoModel(RegistrarProductoInterface.TaskListener listener){
        this.listener = listener;
    }

    @Override
    public void mtdOnRegistrarProducto(Producto obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("productos").push().setValue(obj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                listener.mtdOnSuccess();
            }
        });
    }
}
