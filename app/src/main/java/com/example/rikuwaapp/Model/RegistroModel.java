package com.example.rikuwaapp.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rikuwaapp.Entidad.Mercado;
import com.example.rikuwaapp.Entidad.Usuario;
import com.example.rikuwaapp.Interface.RegistroInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroModel implements RegistroInterface.Modelo {

    RegistroInterface.TaskListener listener;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    public RegistroModel(RegistroInterface.TaskListener listener) {
        this.listener = listener;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void mtdOnRegistro(final Usuario obj) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth.createUserWithEmailAndPassword(obj.getEmail(), obj.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(obj.getEmail())
                            .build();
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    obj.setPassword("");
                                    databaseReference.child("usuario").push().setValue(obj, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            listener.mtdOnSuccess();
                                        }
                                    });
                                } else if (task.getException() != null) {
                                    listener.mtdOnError(task.getException().getMessage());
                                }
                            }
                        });
                    }

                } else if (task.getException() != null) {
                    listener.mtdOnError(task.getException().getMessage());
                }
            }
        });
    }
}
