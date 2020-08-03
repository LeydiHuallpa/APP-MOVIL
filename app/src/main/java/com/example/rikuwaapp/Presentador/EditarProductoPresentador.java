package com.example.rikuwaapp.Presentador;

import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.Interface.EditarProductoInterface;
import com.example.rikuwaapp.Model.EditarProductoModel;

public class EditarProductoPresentador implements EditarProductoInterface.Presentador,EditarProductoInterface.TaskListener {

    EditarProductoInterface.Vista vista;
    EditarProductoInterface.Modelo modelo;

    public EditarProductoPresentador(EditarProductoInterface.Vista vista) {
        this.vista = vista;
        modelo = new EditarProductoModel(this);
    }

    @Override
    public void mtdOnEditarProducto(Producto obj) {
        if (vista != null) {
            vista.mtdMostrarProgress();
        }
        modelo.mtdOnEditarProducto(obj);
    }

    @Override
    public void mtdOnSuccess() {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnEditarProducto();
        }
    }

    @Override
    public void mtdOnError(String errror) {
        if (vista != null) {
            vista.mtdOcultarProgress();
            vista.mtdOnError(errror);
        }
    }
}
