package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Personal;
import com.example.rikuwaapp.Interface.RegistrarPersonalInterface;
import com.example.rikuwaapp.Model.RegistrarPersonalModel;

public class RegistrarPersonalPresentador implements RegistrarPersonalInterface.Presentador, RegistrarPersonalInterface.TaskListener {
    RegistrarPersonalInterface.Vista vista;
    RegistrarPersonalInterface.Modelo modelo;

    public RegistrarPersonalPresentador(RegistrarPersonalInterface.Vista vista) {
        this.vista = vista;
        modelo = new RegistrarPersonalModel(this);
    }

    @Override
    public void mtdOnRegistrarPersonal(Personal obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnRegistrarPersonal(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnRegistrarPersonal();
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
