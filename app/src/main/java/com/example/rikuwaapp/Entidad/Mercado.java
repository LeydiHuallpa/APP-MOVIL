package com.example.rikuwaapp.Entidad;

public class Mercado {
    String nombrePlaca = "";
    String nombreUnidad = "";
    String imagenReferencial = "";
    String zonaTrabajo = "";
    String estado = "Inactivo";

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombrePlaca() {
        return nombrePlaca;
    }

    public void setNombrePlaca(String nombrePlaca) {
        this.nombrePlaca = nombrePlaca;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getImagenReferencial() {
        return imagenReferencial;
    }

    public void setImagenReferencial(String imagenReferencial) {
        this.imagenReferencial = imagenReferencial;
    }

    public String getZonaTrabajo() {
        return zonaTrabajo;
    }

    public void setZonaTrabajo(String zonaTrabajo) {
        this.zonaTrabajo = zonaTrabajo;
    }
}
