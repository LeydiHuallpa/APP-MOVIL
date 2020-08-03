package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Horario;

public interface EditarHorarioInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnEditarHorario();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnEditarHorario(Horario obj);
    }

    interface Modelo {
        void mtdOnEditarHorario(Horario obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
