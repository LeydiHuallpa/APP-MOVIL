package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.Interface.EditarUnidadInterface;
import com.example.rikuwaapp.Model.EditarUnidadModel;

public class EditarUnidadPresentador implements EditarUnidadInterface.Presentador, EditarUnidadInterface.TaskListener {

    EditarUnidadInterface.Vista vista;
    EditarUnidadInterface.Modelo modelo;

    public EditarUnidadPresentador(EditarUnidadInterface.Vista vista) {
        this.vista = vista;
        modelo = new EditarUnidadModel(this);
    }

    @Override
    public void mtdOnEditar(Unidad obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnEditar(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnEditar();
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
