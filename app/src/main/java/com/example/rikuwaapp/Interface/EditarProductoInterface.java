package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Producto;

public interface EditarProductoInterface {
    interface Vista {
        void mtdMostrarProgress();

        void mtdOcultarProgress();

        void mtdHandleRegistro();

        void mtdOnEditarProducto();

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdOnEditarProducto(Producto obj);
    }

    interface Modelo {
        void mtdOnEditarProducto(Producto obj);
    }

    interface TaskListener {
        void mtdOnSuccess();
        void mtdOnError(String errror);
    }
}
