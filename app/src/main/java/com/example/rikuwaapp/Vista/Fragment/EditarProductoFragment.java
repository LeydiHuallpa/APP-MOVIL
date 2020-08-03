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
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Entidad.Zona;
import com.example.rikuwaapp.Interface.EditarProductoInterface;
import com.example.rikuwaapp.Presentador.EditarProductoPresentador;
import com.example.rikuwaapp.Presentador.RegistrarProductoPresentador;
import com.example.rikuwaapp.Presentador.RegistrarUnidadPresentador;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.AdministradorActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EditarProductoFragment extends Fragment implements EditarProductoInterface.Vista {

    String nombreProducto;

    EditText txtnombreProducto, txtPrecioProducto;
    Button btnAbrirImagenes, btnEditarProducto;
    Uri uri = null;
    private static final int GALLERY_INTENT = 1;
    MaterialDialog dialog;
    Spinner spinner;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    EditarProductoPresentador presentador;
    Producto objProducto;

    public EditarProductoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_producto, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presentador = new EditarProductoPresentador(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        txtPrecioProducto = view.findViewById(R.id.txtPrecioProducto);
        btnAbrirImagenes = view.findViewById(R.id.btnAbrirImagenes);
        btnEditarProducto = view.findViewById(R.id.btnEditarProducto);
        spinner = view.findViewById(R.id.spinner);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("CARGANDO")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true, 0);//true para que sea indeterminado y que no tenga maximo
        dialog = builder.build();


        btnAbrirImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        btnEditarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtdHandleRegistro();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreProducto = bundle.getString("nombreProducto");
        }


        CargarDataProducto();
    }

    private int obtenerPosicionArrayCategoria(String categoria){
        int posicion = 0;
        String[] listazonas = getResources().getStringArray(R.array.categoriaproducto);
        for (int i = 0; i < listazonas.length; i++) {
            if (listazonas[i].equals(categoria)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public void CargarDataProducto() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("productos").orderByChild("nombreProducto").equalTo(nombreProducto);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    objProducto = obj.getValue(Producto.class);
                }
                txtPrecioProducto.setText(String.valueOf(objProducto.getPrecioProducto()));
                spinner.setSelection(obtenerPosicionArrayCategoria(objProducto.getCateogoriaProducto()));
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

    }

    @Override
    public void mtdOcultarProgress() {

    }

    @Override
    public void mtdHandleRegistro() {

        double precioProducto = Double.parseDouble(txtPrecioProducto.getText().toString());
        String categoriaProducto = spinner.getSelectedItem().toString();
        final String urlurl = uri == null ? "" : uri.toString();

        if (String.valueOf(precioProducto).isEmpty()) {
            Toast.makeText(getActivity(), "Ingrese un precio de producto", Toast.LENGTH_SHORT).show();
            return;
        }
        if (categoriaProducto.isEmpty()) {
            Toast.makeText(getActivity(), "Ingrese una categoria", Toast.LENGTH_SHORT).show();
            return;
        }
        if (urlurl.isEmpty()) {
            Toast.makeText(getActivity(), "Seleccione una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        final Producto producto = new Producto();
        producto.setNombreUnidad(objProducto.getNombreUnidad());
        producto.setNombreProducto(objProducto.getNombreProducto());
        producto.setPrecioProducto(precioProducto);
        producto.setCateogoriaProducto(categoriaProducto);
        producto.setNombreProductotmp(objProducto.getNombreProductotmp());

        final StorageReference filepath = storageReference.child("productos").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        producto.setImagenReferencial(urlurl);
                        presentador.mtdOnEditarProducto(producto);
                    }
                });
            }
        });

    }

    @Override
    public void mtdOnEditarProducto() {
        Toast.makeText(getActivity(), "Se ha editado exitosamente el producto", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), AdministradorActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {

    }
}
