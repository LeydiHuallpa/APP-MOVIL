package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.Interface.RegistrarHorarioInterface;
import com.example.rikuwaapp.Model.RegistrarHorarioModel;

public class RegistrarHorarioPresentador implements RegistrarHorarioInterface.Presentador, RegistrarHorarioInterface.TaskListener{

    RegistrarHorarioInterface.Vista vista;
    RegistrarHorarioInterface.Modelo modelo;

    public RegistrarHorarioPresentador(RegistrarHorarioInterface.Vista vista) {
        this.vista = vista;
        modelo = new RegistrarHorarioModel(this);
    }

    @Override
    public void mtdOnRegistrarHorario(Horario obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnRegistrarHorario(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnRegistrarHorario();
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