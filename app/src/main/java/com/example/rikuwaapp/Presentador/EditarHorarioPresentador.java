package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.Interface.EditarHorarioInterface;
import com.example.rikuwaapp.Model.EditarHorarioModel;

public class EditarHorarioPresentador implements EditarHorarioInterface.Presentador,EditarHorarioInterface.TaskListener {

    EditarHorarioInterface.Vista vista;
    EditarHorarioInterface.Modelo modelo;

    public EditarHorarioPresentador(EditarHorarioInterface.Vista vista) {
        this.vista = vista;
        modelo = new EditarHorarioModel(this);
    }

    @Override
    public void mtdOnEditarHorario(Horario obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnEditarHorario(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnEditarHorario();
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