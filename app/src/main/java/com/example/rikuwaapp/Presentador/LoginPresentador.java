package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Interface.LoginInterface;
import com.example.rikuwaapp.Entidad.Usuario;
import com.example.rikuwaapp.Model.LoginModel;

public class LoginPresentador implements LoginInterface.Presentador, LoginInterface.TaskListener {

    private LoginInterface.Vista view;
    private LoginInterface.Modelo model;

    public LoginPresentador(LoginInterface.Vista view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void mtdToLogin(Usuario obj) {
        if(view!=null){
            view.mtdMostrarProgress();
        }
        model.mtdDoLogin(obj);
    }

    @Override
    public void mtdOnSuccess(String tipoUsuario) {
        if (view != null) {
            view.mtdOcultarProgress();
            view.mtdOnLogin(tipoUsuario);
        }
    }

    @Override
    public void mtdOnError(String error) {
        if (view != null) {
            view.mtdOcultarProgress();
            view.mtdOnError(error);
        }
    }
}
