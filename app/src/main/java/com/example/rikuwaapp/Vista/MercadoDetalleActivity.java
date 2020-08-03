package com.example.rikuwaapp.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MercadoDetalleActivity extends AppCompatActivity {

    String mercado;
    Unidad objUnidad;
    TextView txtNombrePlaca, txtZonaPertenece;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_detalle);
        setViews();
    }

    private void setViews() {
        Bundle b = getIntent().getExtras();
        mercado = b.getString("mercado");
        imageView = findViewById(R.id.imageView);
        txtNombrePlaca = findViewById(R.id.txtNombrePlaca);
        txtZonaPertenece = findViewById(R.id.txtZonaPertenece);
        cargarMercadoDetalle();
    }

    private void cargarMercadoDetalle() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("unidad").orderByChild("nombreUnidad").equalTo(mercado);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    objUnidad = obj.getValue(Unidad.class);
                }

                Glide.with(MercadoDetalleActivity.this)
                        .load(objUnidad.getFotoReferencial())
                        .into(imageView);

                txtNombrePlaca.setText(objUnidad.getNombrePlaca());
                txtZonaPertenece.setText(objUnidad.getZonaUnidad());

                //txtPrecioProducto.setText(String.valueOf(objProducto.getPrecioProducto()));
                //spinner.setSelection(obtenerPosicionArrayCategoria(objProducto.getCateogoriaProducto()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}