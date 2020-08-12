package com.example.rikuwaapp.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    GoogleMap map;
    List<Unidad> unidadList;
    DatabaseReference databaseReference;
    FusedLocationProviderClient client;
    Double latitud_locacion, longitud_locacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        unidadList = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        client = LocationServices.getFusedLocationProviderClient(this);
        obtenerLocalizacion();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void obtenerLocalizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitud_locacion = location.getLatitude();
                    longitud_locacion = location.getLongitude();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(final GoogleMap googleMap) {
                            map = googleMap;
                            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapActivity.this, R.raw.stylemap));
                            databaseReference.child("unidad").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    unidadList.clear();
                                    map.clear();
                                    Marker mymarker = null;
                                    LatLng myposition = new LatLng(latitud_locacion, longitud_locacion);
                                    mymarker = googleMap.addMarker(new MarkerOptions().position(myposition).title("Mi posición"));
                                    CameraPosition camPos = new CameraPosition(myposition, 16, 0, 0);
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, 18));
                                    mymarker.showInfoWindow();
                                    for (DataSnapshot obj : snapshot.getChildren()) {
                                        Unidad objunidad = new Unidad();
                                        objunidad = obj.getValue(Unidad.class);
                                        Marker marker = null;
                                        LatLng position = new LatLng(objunidad.getLatitud(), objunidad.getLongitud());
                                        marker = googleMap.addMarker(new MarkerOptions().position(position).title(objunidad.getNombreUnidad()));
                                    }

                                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {
                                            String titulo = marker.getTitle();
                                            if (!titulo.equals("Mi Posición")){
                                                Intent intent = new Intent(MapActivity.this, MercadoDetalleActivity.class);
                                                intent.putExtra("mercado", titulo);
                                                startActivity(intent);
                                            }
                                            return false;
                                        }
                                    });
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent i = new Intent();
        switch (menuItem.getItemId()) {
            case R.id.nav_unidades:
                i = new Intent(MapActivity.this, MapActivity.class);
                startActivity(i);
                return false;
            case R.id.nav_productos:
                i = new Intent(MapActivity.this,ProductosActivity.class);
                startActivity(i);
                return false;
            case R.id.nav_horarios_detalle:
                i = new Intent(MapActivity.this, HorariosActivity.class);
                startActivity(i);
                return false;
            case R.id.nav_cerrarseion:
                Helper.LimpiarSharedPreferences(this);
                i = new Intent(MapActivity.this, LoginActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }
}