package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Unidad;

public interface EditarUnidadInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnEditar();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnEditar(Unidad obj);
    }

    interface Modelo {
        void mtdOnEditar(Unidad obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
