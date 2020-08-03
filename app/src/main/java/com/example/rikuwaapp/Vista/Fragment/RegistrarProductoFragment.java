package com.example.rikuwaapp.Vista.Fragment;

import android.content.Intent;
import android.net.Uri;
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
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Interface.RegistrarProductoInterface;
import com.example.rikuwaapp.Presentador.RegistrarProductoPresentador;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.AdministradorActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RegistrarProductoFragment extends Fragment implements RegistrarProductoInterface.Vista {

    EditText txtnombreProducto, txtPrecioProducto;
    Button btnAbrirImagenes, btnRegistrarproducto;
    Uri uri = null;
    private static final int GALLERY_INTENT = 1;
    MaterialDialog dialog;
    Spinner spinner, spinner2;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    RegistrarProductoPresentador presentador;

    public RegistrarProductoFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_producto, container, false);
        setViews(view);
        cargarSpinnerUnidades();
        return view;
    }

    private void setViews(View view) {
        presentador = new RegistrarProductoPresentador(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        txtnombreProducto = view.findViewById(R.id.txtnombreProducto);
        txtPrecioProducto = view.findViewById(R.id.txtPrecioProducto);
        btnAbrirImagenes = view.findViewById(R.id.btnAbrirImagenes);
        spinner = view.findViewById(R.id.spinner);
        spinner2 = view.findViewById(R.id.spinner2);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("CARGANDO")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true, 0);//true para que sea indeterminado y que no tenga maximo
        dialog = builder.build();

        btnRegistrarproducto = view.findViewById(R.id.btnRegistrarproducto);

        btnAbrirImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        btnRegistrarproducto.setOnClickListener(new View.OnClickListener() {
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
                    spinner2.setAdapter(arrayAdapter);
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
        String nombreProducto = txtnombreProducto.getText().toString();
        double precioProducto = Double.parseDouble(txtPrecioProducto.getText().toString());
        String categoriaProducto = spinner.getSelectedItem().toString();
        String nombreUnidad = spinner2.getSelectedItem().toString();
        final String urlurl = uri == null ? "" : uri.toString();

        if (nombreProducto.isEmpty()) {
            Toast.makeText(getActivity(), "Ingrese un nombre de producto", Toast.LENGTH_SHORT).show();
            return;
        }
        if (String.valueOf(precioProducto).isEmpty()) {
            Toast.makeText(getActivity(), "Ingrese un precio de producto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (categoriaProducto.isEmpty()) {
            Toast.makeText(getActivity(), "Ingrese una categoria", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nombreUnidad.isEmpty()) {
            Toast.makeText(getActivity(), "Seleccione una unidad", Toast.LENGTH_SHORT).show();
            return;
        }
        if (urlurl.isEmpty()) {
            Toast.makeText(getActivity(), "Seleccione una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        final Producto producto = new Producto();
        producto.setNombreUnidad(nombreUnidad);
        producto.setNombreProducto(nombreProducto);
        producto.setPrecioProducto(precioProducto);
        producto.setCateogoriaProducto(categoriaProducto);
        producto.setNombreProductotmp(nombreProducto);

        final StorageReference filepath = storageReference.child("productos").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        producto.setImagenReferencial(urlurl);
                        presentador.mtdOnRegistrarProducto(producto);
                    }
                });
            }
        });

    }

    @Override
    public void mtdOnRegistrarProducto() {
        Toast.makeText(getActivity(), "Se ha registrado un producto", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), AdministradorActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {

    }
}
