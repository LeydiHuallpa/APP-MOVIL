package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Personal;

public interface EditarPersonalInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnEditarPersonal();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnEditarPersonal(Personal obj);
    }

    interface Modelo {
        void mtdOnEditarPersonal(Personal obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
