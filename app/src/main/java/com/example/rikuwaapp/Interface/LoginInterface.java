package com.example.rikuwaapp.Interface;

import com.example.rikuwaapp.Entidad.Usuario;

public interface LoginInterface {
    interface Vista {
        void mtdMostrarProgress();///muestra el progreso cuando envia datos al preesentador

        void mtdOcultarProgress();///oculta el progresbar

        void mtdHandleLogin();//empieza a trabajar para validar los datos, se encarga de quetodo funcione Y CUMPLA LOSPARAMETROS

        boolean mtdValidarEmail();///esto lo podria hacer el presentador

        boolean mtdValidarPassword();///esto igual

        void mtdOnLogin(String tipoUsuario);

        void mtdOnError(String error);
    }

    interface Presentador {
        void mtdToLogin(Usuario obj);///enviar estos datos al modelo
    }

    interface Modelo {
        void mtdDoLogin(Usuario obj);
    }

    //si el login ha sido bueno o no, siempre esta escuchando
    interface TaskListener {
        void mtdOnSuccess(String tipoUsuario);

        void mtdOnError(String error);
    }
}
