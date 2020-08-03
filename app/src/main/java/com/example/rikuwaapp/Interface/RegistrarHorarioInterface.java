package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Horario;

public interface RegistrarHorarioInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnRegistrarHorario();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnRegistrarHorario(Horario obj);
    }

    interface Modelo {
        void mtdOnRegistrarHorario(Horario obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
