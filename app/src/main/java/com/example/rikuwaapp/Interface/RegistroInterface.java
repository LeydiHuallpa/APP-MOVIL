package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Usuario;

public interface RegistroInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        boolean mtdValidarEmail();

        boolean mtdValidarPassword();

        void mtdOnRegistro();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnRegistro(Usuario obj);
    }

    interface Modelo {
        void mtdOnRegistro(Usuario obj);
    }

    interface TaskListener {
        void mtdOnSuccess();

        void mtdOnError(String errror);
    }

}
