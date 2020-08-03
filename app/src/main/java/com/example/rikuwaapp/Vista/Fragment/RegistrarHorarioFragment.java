package com.example.rikuwaapp.Vista.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.example.rikuwaapp.Interface.RegistrarHorarioInterface;
import com.example.rikuwaapp.Presentador.RegistrarHorarioPresentador;
import com.example.rikuwaapp.Presentador.RegistrarHorarioPresentador;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.AdministradorActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RegistrarHorarioFragment extends Fragment implements RegistrarHorarioInterface.Vista {

    StorageReference storageReference;
    DatabaseReference databaseReference;
    Spinner spinner;

    EditText txtHoraInicio, txtHoraFin;
    Button btnRegistrarHorario;
    MaterialDialog dialog;
    RegistrarHorarioPresentador presentador;

    CheckBox chkLunes, chkMartes, chkMiercoles, chkJueves, chkViernes, chkSabado, chkDomingo;

    public RegistrarHorarioFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_horario, container, false);
        setViews(view);
        cargarSpinnerUnidades();
        return view;
    }

    private void setViews(View view) {
        presentador = new RegistrarHorarioPresentador(this);
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

        spinner = view.findViewById(R.id.spinner2);


        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("CARGANDO")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true, 0);//true para que sea indeterminado y que no tenga maximo
        dialog = builder.build();

        btnRegistrarHorario = view.findViewById(R.id.btnRegistrarHorario);

        btnRegistrarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtdHandleRegistro();
            }
        });
    }

    private void cargarSpinnerUnidades() {
        final List<String> unidades = new ArrayList<>();
        databaseReference.child("unidad").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String id = ds.getKey();
                        String nombre = ds.child("nombreUnidad").getValue().toString();
                        unidades.add(nombre);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, unidades);
                    spinner.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    @Override
    public void mtdMostrarProgress() {
        dialog.show();
    }

    @Override
    public void mtdOcultarProgress() {
        dialog.dismiss();
    }

    @Override
    public void mtdHandleRegistro() {
        Horario objHorario = new Horario();

        if (txtHoraInicio.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "El campo hora inicio esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtHoraFin.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "El campo hora fin esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (validarDiasAtencion().isEmpty()){
            Toast.makeText(getActivity(), "No ha seleccionado ningun día de atención", Toast.LENGTH_SHORT).show();
            return;
        }

        objHorario.setNombreUnidad(spinner.getSelectedItem().toString());
        objHorario.setHoraInicio(txtHoraInicio.getText().toString());
        objHorario.setHoraFin(txtHoraFin.getText().toString());
        objHorario.setDiaSemana(validarDiasAtencion());
        presentador.mtdOnRegistrarHorario(objHorario);
    }

    @Override
    public void mtdOnRegistrarHorario() {
        Toast.makeText(getActivity(), "Se ha registrado un horario", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), AdministradorActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {

    }
}
