package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Personal;
import com.example.rikuwaapp.Interface.EditarPersonalInterface;
import com.example.rikuwaapp.Model.EditarPersonalModel;

public class EditarPersonalPresentador implements EditarPersonalInterface.Presentador,EditarPersonalInterface.TaskListener {

    EditarPersonalInterface.Vista vista;
    EditarPersonalInterface.Modelo modelo;

    public EditarPersonalPresentador(EditarPersonalInterface.Vista vista) {
        this.vista = vista;
        modelo = new EditarPersonalModel(this);
    }

    @Override
    public void mtdOnEditarPersonal(Personal obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnEditarPersonal(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnEditarPersonal();
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