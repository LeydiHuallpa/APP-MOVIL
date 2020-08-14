package com.example.rikuwaapp.Model;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Interface.EditarUnidadInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class EditarUnidadModel implements EditarUnidadInterface.Modelo {

    EditarUnidadInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public EditarUnidadModel(EditarUnidadInterface.TaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void mtdOnEditar(final Unidad obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //nombrePlaca
        Query query = databaseReference.child("unidad").orderByChild("nombrePlacatmp").equalTo(obj.getNombrePlacatmp());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = "";
                for (DataSnapshot ds : snapshot.getChildren()) {
                    key = ds.getKey();
                }
                databaseReference.child("unidad").child(key).setValue(obj);
                listener.mtdOnSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.mtdOnError(error.toString());
            }
        });
    }
}
