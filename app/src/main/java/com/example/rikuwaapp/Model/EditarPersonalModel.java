package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;

import com.example.rikuwaapp.Entidad.Personal;
import com.example.rikuwaapp.Interface.EditarPersonalInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditarPersonalModel implements EditarPersonalInterface.Modelo{
    EditarPersonalInterface.TaskListener listener;
    DatabaseReference databaseReference;

    public EditarPersonalModel(EditarPersonalInterface.TaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void mtdOnEditarPersonal(final Personal obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        String objtmp = obj.getNombreCompletotmp();
        obj.setNombreCompletotmp(obj.getNombreCompleto());

        Query query = databaseReference.child("Personals").orderByChild("nombreCompletotmp").equalTo(objtmp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = "";
                for (DataSnapshot ds : snapshot.getChildren()) {
                    key = ds.getKey();
                }
                databaseReference.child("Personals").child(key).setValue(obj);
                listener.mtdOnSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.mtdOnError(error.toString());
            }
        });
    }
}
