package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Usuario;
import com.example.rikuwaapp.Interface.RegistroInterface;
import com.example.rikuwaapp.Model.RegistroModel;

public class RegistroPresentador implements RegistroInterface.Presentador, RegistroInterface.TaskListener {

    RegistroInterface.Vista vista;
    RegistroInterface.Modelo modelo;

    public RegistroPresentador(RegistroInterface.Vista vista) {
        this.vista = vista;
        modelo = new RegistroModel(this);
    }

    @Override
    public void mtdOnRegistro(Usuario obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnRegistro(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnRegistro();
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
