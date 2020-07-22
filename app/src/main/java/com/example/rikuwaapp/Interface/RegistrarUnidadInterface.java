package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Unidad;

public interface RegistrarUnidadInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnRegistrarUnidad();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnRegistrarUnidad(Unidad obj);
    }

    interface Modelo {
        void mtdOnRegistrarUnidad(Unidad obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
