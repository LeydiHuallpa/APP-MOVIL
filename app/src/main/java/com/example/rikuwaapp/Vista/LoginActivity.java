package com.example.rikuwaapp.Vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.Interface.LoginInterface;
import com.example.rikuwaapp.Entidad.Usuario;
import com.example.rikuwaapp.Presentador.LoginPresentador;
import com.example.rikuwaapp.R;

public class LoginActivity extends AppCompatActivity implements LoginInterface.Vista, View.OnClickListener {

    EditText miEditTextEmail;
    EditText miEditTextPassword;
    Button miButtonLogin, btn_registro;
    MaterialDialog dialog;
    LoginInterface.Presentador presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
        miButtonLogin.setOnClickListener(this);
        btn_registro.setOnClickListener(this);
    }

    private void setViews() {
        presenter = new LoginPresentador(this);
        miEditTextEmail = findViewById(R.id.et_correo);
        miEditTextPassword = findViewById(R.id.et_password);
        miButtonLogin = findViewById(R.id.btn_login);
        btn_registro = findViewById(R.id.btn_registro);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("CARGANDO")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true, 0);//true para que sea indeterminado y que no tenga maximo
        dialog = builder.build();
    }

    @Override
    public void mtdMostrarProgress() {
        dialog.show();
    }

    @Override
    public void mtdOcultarProgress() {
        dialog.dismiss();
    }

    @Override
    public void mtdHandleLogin() {
        if (!mtdValidarEmail()) {
            Toast.makeText(LoginActivity.this, "No es un email valido", Toast.LENGTH_SHORT).show();
        } else if (!mtdValidarPassword()) {
            Toast.makeText(LoginActivity.this, "No es un password valido", Toast.LENGTH_SHORT).show();
        } else {
            //trim: para que no haya espacios
            Usuario obj = new Usuario();
            obj.setEmail(miEditTextEmail.getText().toString().trim());
            obj.setPassword(miEditTextPassword.getText().toString().trim());
            presenter.mtdToLogin(obj);
        }
    }

    @Override
    public boolean mtdValidarEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(miEditTextEmail.getText().toString()).matches();
    }

    @Override
    public boolean mtdValidarPassword() {
        //TextUtils.isEmpty: si esta vacio y es menor que 4, no es valido
        if (TextUtils.isEmpty(miEditTextPassword.getText().toString()) && miEditTextPassword.getText().toString().length() < 4) {
            Toast.makeText(LoginActivity.this, "No es una contraseña valida", Toast.LENGTH_SHORT).show();
            miEditTextPassword.setError("No es una contraseña valida");
            return false; // devuelve false
        } else {
            return true;
        }
    }

    @Override
    public void mtdOnLogin(String tipoUsuario) {
        Usuario obj = new Usuario();
        obj.setEmail(miEditTextEmail.getText().toString().trim());
        obj.setTipoUsuario(tipoUsuario);
        Helper.CapturarDataUsuarioSesion(this, obj);
        Intent i;
        switch (tipoUsuario){
            case "Administrador":
                i = new Intent(LoginActivity.this, AdministradorActivity.class);
                startActivity(i);
                break;
            default:
                i = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void mtdOnError(String error) {
        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();//muestra el error que envia el tasklistener
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mtdHandleLogin();
                break;
            case R.id.btn_registro:
                startActivity(new Intent(this, RegistroActivity.class));
                break;
        }
    }
}