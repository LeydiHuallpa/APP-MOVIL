package com.example.rikuwaapp.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.R;
import com.example.rikuwaapp.Vista.Fragment.HorarioFragment;
import com.example.rikuwaapp.Vista.Fragment.PersonalFragment;
import com.example.rikuwaapp.Vista.Fragment.ProductosFragment;
import com.example.rikuwaapp.Vista.Fragment.UnidadFragment;
import com.google.android.material.navigation.NavigationView;

public class AdministradorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    UnidadFragment unidadFragment;
    ProductosFragment productosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        drawerLayout = findViewById(R.id.drawerLayoutAdministrador);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        InicializarFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent i = new Intent();
        switch (menuItem.getItemId()) {
            case R.id.nav_unidades:
                unidadFragment = new UnidadFragment();
                setFragment(unidadFragment);
                return true;
            case R.id.nav_productos_gestionar:
                ProductosFragment productosFragment = new ProductosFragment();
                setFragment(productosFragment);
                return true;
            case R.id.nav_horarios:
                HorarioFragment horarioFragment = new HorarioFragment();
                setFragment(horarioFragment);
                return true;
            case R.id.nav_personal:
                PersonalFragment personalFragment = new PersonalFragment();
                setFragment(personalFragment);
                return true;
            case R.id.nav_cerrarseion:
                Helper.LimpiarSharedPreferences(this);
                i = new Intent(AdministradorActivity.this, LoginActivity.class);
                startActivity(i);
                return true;
        }
        return false;
    }

    private void InicializarFragment() {
        UnidadFragment perfilFragment = new UnidadFragment();
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragment_container, perfilFragment);
        fragmentTransaction2.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}