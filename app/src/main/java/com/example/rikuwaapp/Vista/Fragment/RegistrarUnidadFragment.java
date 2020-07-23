package com.example.rikuwaapp.Vista.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Entidad.Zona;
import com.example.rikuwaapp.Interface.EditarUnidadInterface;
import com.example.rikuwaapp.Interface.RegistrarUnidadInterface;
import com.example.rikuwaapp.Presentador.EditarUnidadPresentador;
import com.example.rikuwaapp.Presentador.RegistrarUnidadPresentador;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.AdministradorActivity;
import com.example.rikuwaapp.Vista.MapActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RegistrarUnidadFragment extends Fragment implements RegistrarUnidadInterface.Vista {

    Button button, buttonEditar;
    StorageReference storageReference;
    Uri uri = null;
    EditText txtnombrePlaca, txtNombreUnidad;
    MaterialDialog dialog;
    Spinner spinner;
    private static final int GALLERY_INTENT = 1;
    RegistrarUnidadPresentador presentador;

    public RegistrarUnidadFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_unidad, container, false);
        setViews(view);
        return view;
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

    private void setViews(View view) {
        presentador = new RegistrarUnidadPresentador(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        button = view.findViewById(R.id.btnAbrirImagenes);
        buttonEditar = view.findViewById(R.id.btnEditar);
        txtnombrePlaca = view.findViewById(R.id.txtnombrePlaca);
        txtNombreUnidad = view.findViewById(R.id.txtNombreUnidad);
        spinner = view.findViewById(R.id.spinner);

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
        objunidad.setNombrePlacatmp(nombreplaca);
        objunidad.setZonaUnidad(zona);
        objunidad.setLatitud(objzona.getLatitud());
        objunidad.setLongitud(objzona.getLongitud());

        final StorageReference filepath = storageReference.child("fotos").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Se ha subido", Toast.LENGTH_SHORT).show();
                //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        objunidad.setEstadoPersonas("Rojo");
                        objunidad.setFotoReferencial(String.valueOf(uri));
                        presentador.mtdOnRegistrarUnidad(objunidad);
                    }
                });

            }
        });
    }

    @Override
    public void mtdOnRegistrarUnidad() {
        Toast.makeText(getActivity(), "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), AdministradorActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data.getData();
        }
    }
}
