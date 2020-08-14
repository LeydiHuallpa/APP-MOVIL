package com.example.rikuwaapp.Vista.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Entidad.Usuario;
import com.example.rikuwaapp.Entidad.Zona;
import com.example.rikuwaapp.Interface.EditarUnidadInterface;
import com.example.rikuwaapp.Presentador.EditarUnidadPresentador;
import com.example.rikuwaapp.Presentador.LoginPresentador;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.AdministradorActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class EditarUnidadFragment extends Fragment implements EditarUnidadInterface.Vista {

    Button button, buttonEditar;
    StorageReference storageReference;
    Uri uri = null;
    EditText txtnombrePlaca, txtNombreUnidad;
    MaterialDialog dialog;
    Spinner spinner;
    private static final int GALLERY_INTENT = 1;
    EditarUnidadInterface.Presentador presentador;
    String nombrePlaca = "";
    Unidad unidadobj;
    Spinner spinnerEstadoUnidad;

    public EditarUnidadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_unidad, container, false);
        setViews(view);
        return view;
    }

    public String nombrePlacaParametroEntrante() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombrePlaca = bundle.getString("nombrePlaca");
        }
        return String.valueOf(nombrePlaca);
    }

    private void setViews(View view) {
        presentador = new EditarUnidadPresentador(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        button = view.findViewById(R.id.btnAbrirImagenes);
        buttonEditar = view.findViewById(R.id.btnEditar);
        txtnombrePlaca = view.findViewById(R.id.txtnombrePlaca);
        txtNombreUnidad = view.findViewById(R.id.txtNombreUnidad);
        spinner = view.findViewById(R.id.spinner);
        spinnerEstadoUnidad = view.findViewById(R.id.spinnerEstadoUnidad);
        nombrePlacaParametroEntrante();

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("CARGANDO")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true, 0);//true para que sea indeterminado y que no tenga maximo
        dialog = builder.build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtdHandleRegistro();
            }
        });
        cargaData(nombrePlaca);
    }

    private int obtenerPosicionArrayZonas(String zona) {
        int posicion = 0;
        String[] listazonas = getResources().getStringArray(R.array.zona);
        for (int i = 0; i < listazonas.length; i++) {
            if (listazonas[i].equals(zona)) {
                posicion = i;
            }
        }
        return posicion;
    }

    private Zona zonaUbicacionDetalle(String zona) {
        Zona obj = new Zona();
        List<Zona> lista = Zona.listaZonaUbicacion();
        for (Zona objzona : lista) {
            if (objzona.getUbicacion().equals(zona)) {
                obj = objzona;
                break;
            }
        }
        return obj;
    }

    private void cargaData(String nombrePlacaEntrante) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("unidad").orderByChild("nombrePlaca").equalTo(nombrePlacaEntrante);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    unidadobj = obj.getValue(Unidad.class);
                }
                txtnombrePlaca.setText(unidadobj.getNombrePlaca());
                txtNombreUnidad.setText(unidadobj.getNombreUnidad());
                spinner.setSelection(obtenerPosicionArrayZonas(unidadobj.getZonaUnidad()));

                switch (unidadobj.getEstadoPersonas()){
                    case "Verde":
                        spinnerEstadoUnidad.setSelection(0);
                        break;
                    case "Amarillo":
                        spinnerEstadoUnidad.setSelection(1);
                        break;
                    case "Rojo":
                        spinnerEstadoUnidad.setSelection(2);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data.getData();
        }
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
        String nombreunidad = txtNombreUnidad.getText().toString();
        String nombreplaca = txtnombrePlaca.getText().toString();
        String zona = spinner.getSelectedItem().toString();
        String urlurl = uri == null ? "" : uri.toString();
        if (nombreplaca.isEmpty()) {
            Toast.makeText(getActivity(), "Ingrese un nombre de placa", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nombreunidad.isEmpty()) {
            Toast.makeText(getActivity(), "Ingrese un nombre de unidad", Toast.LENGTH_SHORT).show();
            return;
        }
        if (zona.isEmpty()) {
            Toast.makeText(getActivity(), "Seleccione una zona", Toast.LENGTH_SHORT).show();
            return;
        }
        if (urlurl.isEmpty()) {
            Toast.makeText(getActivity(), "Seleccione una imagen", Toast.LENGTH_SHORT).show();
            return;
        }
        Zona objzona = zonaUbicacionDetalle(zona);


        final Unidad objunidad = new Unidad();
        objunidad.setNombreUnidad(nombreunidad);
        objunidad.setNombrePlaca(nombreplaca);
        objunidad.setZonaUnidad(zona);
        objunidad.setLatitud(objzona.getLatitud());
        objunidad.setLongitud(objzona.getLongitud());

        final StorageReference filepath = storageReference.child("fotos").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        objunidad.setFotoReferencial(String.valueOf(uri));
                        objunidad.setEstadoPersonas(spinnerEstadoUnidad.getSelectedItem().toString());
                        objunidad.setNombrePlacatmp(unidadobj.getNombrePlacatmp());
                        presentador.mtdOnEditar(objunidad);
                    }
                });
            }
        });
    }


    @Override
    public void mtdOnEditar() {
        Toast.makeText(getActivity(), "Se ha editado exitosamente", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), AdministradorActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}