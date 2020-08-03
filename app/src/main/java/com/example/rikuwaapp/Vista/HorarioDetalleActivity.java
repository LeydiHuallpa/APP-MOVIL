package com.example.rikuwaapp.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HorarioDetalleActivity extends AppCompatActivity {

    String nombreUnidad;
    CheckBox chkLunes, chkMartes, chkMiercoles, chkJueves, chkViernes, chkSabado, chkDomingo;
    TextView txtHorarioConcat;
    Horario objhorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_detalle);
        setViews();
    }

    private void setViews() {
        Bundle b = getIntent().getExtras();
        nombreUnidad = b.getString("nombreUnidad");

        txtHorarioConcat = findViewById(R.id.txtHorarioConcat);
        chkLunes = findViewById(R.id.chkLunes);
        chkMartes = findViewById(R.id.chkMartes);
        chkMiercoles = findViewById(R.id.chkMiercoles);
        chkJueves = findViewById(R.id.chkJueves);
        chkViernes = findViewById(R.id.chkViernes);
        chkSabado = findViewById(R.id.chkSabado);
        chkDomingo = findViewById(R.id.chkDomingo);
        cargarDataHorarioDetalle();
    }

    //    nombreUnidad
    private void CalcularCheckedHorario(String arrayHorarioConcat) {
        String parts[] = arrayHorarioConcat.split(", ");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("Lunes")) {
                chkLunes.setChecked(true);
            }

            if (parts[i].equals("Martes")) {
                chkMartes.setChecked(true);
            }

            if (parts[i].equals("Miercoles")) {
                chkMiercoles.setChecked(true);
            }

            if (parts[i].equals("Jueves")) {
                chkJueves.setChecked(true);
            }

            if (parts[i].equals("Viernes")) {
                chkViernes.setChecked(true);
            }

            if (parts[i].equals("Sabado")) {
                chkSabado.setChecked(true);
            }

            if (parts[i].equals("Domingo")) {
                chkDomingo.setChecked(true);
            }
        }
    }

    private void cargarDataHorarioDetalle() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Horarios").orderByChild("nombreUnidad").equalTo(nombreUnidad);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    objhorario = obj.getValue(Horario.class);
                }
                txtHorarioConcat.setText(objhorario.getHoraInicio() + " - " + objhorario.getHoraFin());
                CalcularCheckedHorario(objhorario.getDiaSemana());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}