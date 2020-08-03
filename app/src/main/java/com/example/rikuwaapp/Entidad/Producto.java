package com.example.rikuwaapp.Entidad;

public class Producto {
    String nombreUnidad;
    String nombreProducto;
    String imagenReferencial;
    double precioProducto;
    String cateogoriaProducto;
    String nombreProductotmp;

    public String getNombreProductotmp() {
        return nombreProductotmp;
    }

    public void setNombreProductotmp(String nombreProductotmp) {
        this.nombreProductotmp = nombreProductotmp;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getCateogoriaProducto() {
        return cateogoriaProducto;
    }

    public void setCateogoriaProducto(String cateogoriaProducto) {
        this.cateogoriaProducto = cateogoriaProducto;
    }
}
