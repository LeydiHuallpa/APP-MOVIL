package com.example.rikuwaapp.Entidad;

import com.example.rikuwaapp.R;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    String ubicacion;
    Double latitud;
    Double longitud;

    public Zona(String ubicacion, Double latitud, Double longitud) {
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Zona() {

    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public static List<Zona> listaZonaUbicacion() {
        List<Zona> lista = new ArrayList<>();
        lista.clear();
        lista.add(new Zona("Alto de la Alianza",-17.9940774,-70.2449133));
        lista.add(new Zona("Calana",-17.9195697,-70.1864373));
        lista.add(new Zona("Ciudad Nueva",-17.9804064,-70.2394079));
        lista.add(new Zona("Coronel Gregorio Albarracín Lanchipa",-18.0429935,-70.2538736));
        lista.add(new Zona("Inclan",-17.7939862,-70.494593));
        lista.add(new Zona("La Yarada-Los Palos",-18.2138807,-70.5207424));
        lista.add(new Zona("Palca",-17.7794223,-69.9615604));
        lista.add(new Zona("Pocollay",-17.9979811,-70.225159));
        lista.add(new Zona("Sama",-17.9630573,-70.7296808));
        lista.add(new Zona("Tacna",-18.0163022,-70.2608163));
        return lista;
    }
}
