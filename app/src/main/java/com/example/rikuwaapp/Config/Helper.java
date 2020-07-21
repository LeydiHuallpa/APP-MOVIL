package com.example.rikuwaapp.Config;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.rikuwaapp.Entidad.Usuario;

public class Helper {
    public static boolean CapturarDataUsuarioSesion(Activity activity, Usuario usuario) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("InicioSesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Logeado", true);
        editor.putString("email", usuario.getEmail());
        editor.putString("tipoUsuario", usuario.getTipoUsuario());
        editor.apply();
        return true;
    }
    public static boolean VerificarSesionActiva(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("InicioSesion", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Logeado", false);
    }

    public static void LimpiarSharedPreferences(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("InicioSesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Logeado", false);
        editor.putString("email", "");
        editor.putString("tipoUsuario", "");
        editor.apply();
    }

    public static String ObtenerValorAtributoSharedPreferences(Activity activity,String atributo){
        String valor = "";
        SharedPreferences sharedPreferences = activity.getSharedPreferences("InicioSesion", Context.MODE_PRIVATE);
        valor = sharedPreferences.getString(atributo, null);
        return valor;
    }
}
