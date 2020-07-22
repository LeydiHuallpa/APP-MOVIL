package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Interface.RegistrarUnidadInterface;
import com.example.rikuwaapp.Model.RegistrarUnidadModel;

public class RegistrarUnidadPresentador implements RegistrarUnidadInterface.Presentador, RegistrarUnidadInterface.TaskListener {

    RegistrarUnidadInterface.Vista vista;
    RegistrarUnidadInterface.Modelo modelo;

    public RegistrarUnidadPresentador(RegistrarUnidadInterface.Vista vista) {
        this.vista = vista;
        modelo = new RegistrarUnidadModel(this);
    }

    @Override
    public void mtdOnRegistrarUnidad(Unidad obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnRegistrarUnidad(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnRegistrarUnidad();
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
