package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Personal;

public interface RegistrarPersonalInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnRegistrarPersonal();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnRegistrarPersonal(Personal obj);
    }

    interface Modelo {
        void mtdOnRegistrarPersonal(Personal obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
