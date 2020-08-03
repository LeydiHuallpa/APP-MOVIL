package com.example.rikuwaapp.Vista.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Interface.EditarHorarioInterface;
import com.example.rikuwaapp.Interface.EditarProductoInterface;
import com.example.rikuwaapp.Presentador.EditarHorarioPresentador;
import com.example.rikuwaapp.Presentador.EditarProductoPresentador;
import com.example.rikuwaapp.Presentador.RegistrarHorarioPresentador;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.AdministradorActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditarHorarioFragment extends Fragment implements EditarHorarioInterface.Vista {


    StorageReference storageReference;
    DatabaseReference databaseReference;
    Spinner spinner;
    String nombreUnidad;

    EditText txtHoraInicio, txtHoraFin;
    Button btnEditarHorario;
    MaterialDialog dialog;
    EditarHorarioPresentador presentador;
    Horario objhorario;

    CheckBox chkLunes, chkMartes, chkMiercoles, chkJueves, chkViernes, chkSabado, chkDomingo;

    public EditarHorarioFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_horario, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presentador = new EditarHorarioPresentador(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        txtHoraInicio = view.findViewById(R.id.txtHoraInicio);
        txtHoraFin = view.findViewById(R.id.txtHoraFin);

        chkLunes = view.findViewById(R.id.chkLunes);
        chkMartes = view.findViewById(R.id.chkMartes);
        chkMiercoles = view.findViewById(R.id.chkMiercoles);
        chkJueves = view.findViewById(R.id.chkJueves);
        chkViernes = view.findViewById(R.id.chkViernes);
        chkSabado = view.findViewById(R.id.chkSabado);
        chkDomingo = view.findViewById(R.id.chkDomingo);

        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreUnidad = bundle.getString("nombreUnidad");
        }
        //Toast.makeText(getActivity(), nombreUnidad, Toast.LENGTH_SHORT).show();
        btnEditarHorario = view.findViewById(R.id.btnEditarHorario);

        btnEditarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtdHandleRegistro();
            }
        });
        CargarHorario();
    }

    public String validarDiasAtencion() {
        String horario = "";
        if (chkLunes.isChecked() == true) {
            horario += "Lunes, ";
        }
        if (chkMartes.isChecked() == true) {
            horario += "Martes, ";
        }
        if (chkMiercoles.isChecked() == true) {
            horario += "Miercoles, ";
        }
        if (chkJueves.isChecked() == true) {
            horario += "Jueves, ";
        }
        if (chkViernes.isChecked() == true) {
            horario += "Viernes, ";
        }
        if (chkSabado.isChecked() == true) {
            horario += "Sabado, ";
        }
        if (chkDomingo.isChecked() == true) {
            horario += "Domingo, ";
        }
        return horario;
    }

    private void CalcularCheckedHorario(String arrayHorarioConcat) {
        String parts[] = arrayHorarioConcat.split(", ");
        for(int i = 0;i<parts.length;i++){
            if(parts[i].equals("Lunes")){
                chkLunes.setChecked(true);
            }

            if(parts[i].equals("Martes")){
                chkMartes.setChecked(true);
            }

            if(parts[i].equals("Miercoles")){
                chkMiercoles.setChecked(true);
            }

            if(parts[i].equals("Jueves")){
                chkJueves.setChecked(true);
            }

            if(parts[i].equals("Viernes")){
                chkViernes.setChecked(true);
            }

            if(parts[i].equals("Sabado")){
                chkSabado.setChecked(true);
            }

            if(parts[i].equals("Domingo")){
                chkDomingo.setChecked(true);
            }
        }
    }

    private void CargarHorario() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Horarios").orderByChild("nombreUnidad").equalTo(nombreUnidad);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    objhorario = obj.getValue(Horario.class);
                    //Toast.makeText(getActivity(), objhorario.getDiaSemana(), Toast.LENGTH_SHORT).show();
                }
                txtHoraInicio.setText(objhorario.getHoraInicio());
                txtHoraFin.setText(objhorario.getHoraFin());
                CalcularCheckedHorario(objhorario.getDiaSemana());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void mtdMostrarProgress() {

    }

    @Override
    public void mtdOcultarProgress() {

    }

    @Override
    public void mtdHandleRegistro() {
        Horario obj = new Horario();

        if (txtHoraInicio.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "El campo hora inicio esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtHoraFin.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "El campo hora fin esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (validarDiasAtencion().isEmpty()) {
            Toast.makeText(getActivity(), "No ha seleccionado ningun día de atención", Toast.LENGTH_SHORT).show();
            return;
        }

        obj.setNombreUnidad(objhorario.getNombreUnidad());
        obj.setHoraInicio(txtHoraInicio.getText().toString());
        obj.setHoraFin(txtHoraFin.getText().toString());
        obj.setDiaSemana(validarDiasAtencion());
        presentador.mtdOnEditarHorario(obj);
    }

    @Override
    public void mtdOnEditarHorario() {
        Toast.makeText(getActivity(), "Se ha editado un horario", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), AdministradorActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {

    }
}
