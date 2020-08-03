package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;

import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.Interface.EditarHorarioInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditarHorarioModel implements EditarHorarioInterface.Modelo {

    EditarHorarioInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public EditarHorarioModel(EditarHorarioInterface.TaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void mtdOnEditarHorario(final Horario obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("Horarios").orderByChild("nombreUnidad").equalTo(obj.getNombreUnidad());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = "";
                for (DataSnapshot ds : snapshot.getChildren()) {
                    key = ds.getKey();
                }
                databaseReference.child("Horarios").child(key).setValue(obj);
                listener.mtdOnSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.mtdOnError(error.toString());
            }
        });
    }
}

