package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;

import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Interface.EditarProductoInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditarProductoModel implements EditarProductoInterface.Modelo {

    EditarProductoInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public EditarProductoModel(EditarProductoInterface.TaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void mtdOnEditarProducto(final Producto obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("productos").orderByChild("nombreProductotmp").equalTo(obj.getNombreProductotmp());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = "";
                for (DataSnapshot ds : snapshot.getChildren()) {
                    key = ds.getKey();
                }
                databaseReference.child("productos").child(key).setValue(obj);
                listener.mtdOnSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.mtdOnError(error.toString());
            }
        });
    }
}
