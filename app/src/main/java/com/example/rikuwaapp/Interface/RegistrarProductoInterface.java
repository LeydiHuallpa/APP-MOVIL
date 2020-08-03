package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Producto;

public interface RegistrarProductoInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnRegistrarProducto();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnRegistrarProducto(Producto obj);
    }

    interface Modelo {
        void mtdOnRegistrarProducto(Producto obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
