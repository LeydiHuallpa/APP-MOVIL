package com.example.rikuwaapp.Model;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.rikuwaapp.Entidad.Usuario;
import com.example.rikuwaapp.Interface.LoginInterface;
import com.example.rikuwaapp.Vista.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginModel implements LoginInterface.Modelo {

    LoginInterface.TaskListener listener;
    FirebaseAuth mAuth;

    public LoginModel(LoginInterface.TaskListener listener) {
        this.listener = listener;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void mtdDoLogin(final Usuario obj) {
        mAuth.signInWithEmailAndPassword(obj.getEmail(), obj.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    final Usuario[] usuarioObj = {new Usuario()};
                    Query query = databaseReference.child("usuario").orderByChild("email").equalTo(obj.getEmail());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot obj : snapshot.getChildren()) {
                                usuarioObj[0] = obj.getValue(Usuario.class);
                            }
                            listener.mtdOnSuccess(usuarioObj[0].getTipoUsuario());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    if (task.getException() != null)
                        listener.mtdOnError(task.getException().getMessage());
                }
            }
        });
    }
}
