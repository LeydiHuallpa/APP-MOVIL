package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Interface.RegistrarProductoInterface;
import com.example.rikuwaapp.Interface.RegistrarProductoInterface;
import com.example.rikuwaapp.Model.RegistrarProductoModel;

public class RegistrarProductoPresentador implements RegistrarProductoInterface.Presentador, RegistrarProductoInterface.TaskListener{

    RegistrarProductoInterface.Vista vista;
    RegistrarProductoInterface.Modelo modelo;

    public RegistrarProductoPresentador(RegistrarProductoInterface.Vista vista) {
        this.vista = vista;
        modelo = new RegistrarProductoModel(this);
    }

    @Override
    public void mtdOnRegistrarProducto(Producto obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnRegistrarProducto(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnRegistrarProducto();
        }
    }

    @Override
    public void mtdOnError(String errror) {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnError(errror);
        }
    }
}
