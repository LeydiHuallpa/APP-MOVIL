package com.example.rikuwaapp.Vista.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rikuwaapp.Entidad.Personal;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Interface.EditarPersonalInterface;
import com.example.rikuwaapp.Presentador.EditarPersonalPresentador;
import com.example.rikuwaapp.Presentador.EditarPersonalPresentador;
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

import java.util.ArrayList;
import java.util.List;

public class EditarPersonalFragment extends Fragment implements EditarPersonalInterface.Vista {

    Spinner spinnerUnidad, spinnerCargo;
    EditText txtNombreCompleto, txtDni, txtDomicilio;
    Button btnEditarPersonal;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    EditarPersonalPresentador presentador;
    MaterialDialog dialog;
    String nombreCompleto;
    Personal objPersonal;

    public EditarPersonalFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_personal, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presentador = new EditarPersonalPresentador(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnEditarPersonal = view.findViewById(R.id.btnEditarPersonal);

        txtNombreCompleto = view.findViewById(R.id.txtNombreCompleto);
        txtDni = view.findViewById(R.id.txtDni);
        txtDomicilio = view.findViewById(R.id.txtDomicilio);
        spinnerUnidad = view.findViewById(R.id.spinnerUnidad);
        spinnerCargo = view.findViewById(R.id.spinnerCargo);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("CARGANDO")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true, 0);//true para que sea indeterminado y que no tenga maximo
        dialog = builder.build();
        cargarPersonal();

        cargarSpinnerUnidades();

        btnEditarPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtdHandleRegistro();
            }
        });
    }

    private void cargarPersonal() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreCompleto = bundle.getString("nombreCompleto");
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("Personals").orderByChild("nombreCompletotmp").equalTo(nombreCompleto);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    objPersonal = obj.getValue(Personal.class);
                }
                txtNombreCompleto.setText(objPersonal.getNombreCompleto());
                txtDni.setText(objPersonal.getDni());
                txtDomicilio.setText(objPersonal.getDomicilio());

                int posicionCargo = 0;

                switch (objPersonal.getCargo()) {
                    case "Chofer":
                        posicionCargo = 0;
                        break;
                    case "Copiloto":
                        posicionCargo = 1;
                        break;
                    case "Otro":
                        posicionCargo = 2;
                        break;
                }
                spinnerCargo.setSelection(posicionCargo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    spinnerUnidad.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        Personal objPersona = new Personal();

        if (txtNombreCompleto.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "El campo nombre completo esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtDni.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "El campo dni esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtDomicilio.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "El campo domicilio esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        objPersona.setUnidadNombre(spinnerUnidad.getSelectedItem().toString());
        objPersona.setNombreCompletotmp(objPersonal.getNombreCompletotmp());
        objPersona.setNombreCompleto(txtNombreCompleto.getText().toString());
        objPersona.setDni(txtDni.getText().toString());
        objPersona.setDomicilio(txtDomicilio.getText().toString());
        objPersona.setCargo(spinnerCargo.getSelectedItem().toString());
        presentador.mtdOnEditarPersonal(objPersona);
    }

    @Override
    public void mtdOnEditarPersonal() {
        Toast.makeText(getActivity(), "Se ha editado un personal", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), AdministradorActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {

    }
}
