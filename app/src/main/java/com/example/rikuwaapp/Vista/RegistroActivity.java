package com.example.rikuwaapp.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.Entidad.Usuario;
import com.example.rikuwaapp.Interface.LoginInterface;
import com.example.rikuwaapp.Interface.RegistroInterface;
import com.example.rikuwaapp.Presentador.RegistroPresentador;
import com.example.rikuwaapp.R;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener, RegistroInterface.Vista {
    EditText et_correo, et_password;
    Button btn_ir_crearCuenta, btnSendToLogin;
    MaterialDialog dialog;
    RegistroInterface.Presentador presentador;
    Spinner spinner_tipousuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setViews();
        btn_ir_crearCuenta.setOnClickListener(this);
        btnSendToLogin.setOnClickListener(this);
    }

    public void setViews() {
        presentador = new RegistroPresentador(this);
        et_correo = findViewById(R.id.et_correo);
        et_password = findViewById(R.id.et_password);
        btn_ir_crearCuenta = findViewById(R.id.btn_ir_crearCuenta);
        btnSendToLogin = findViewById(R.id.btnSendToLogin);
        spinner_tipousuario = findViewById(R.id.spinner_tipousuario);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("CARGANDO")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true, 0);//true para que sea indeterminado y que no tenga maximo
        dialog = builder.build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ir_crearCuenta:
                mtdHandleRegistro();
                break;
            case R.id.btnSendToLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
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
    public void mtdHandleRegistro() {
        if (!mtdValidarEmail()) {
            Toast.makeText(RegistroActivity.this, "No es un email valido", Toast.LENGTH_SHORT).show();
        } else if (!mtdValidarPassword()) {
            Toast.makeText(RegistroActivity.this, "No es un password valido", Toast.LENGTH_SHORT).show();
        } else {
            //trim: para que no haya espacios
            Usuario obj = new Usuario();
            obj.setEmail(et_correo.getText().toString().trim());
            obj.setPassword(et_password.getText().toString().trim());
            obj.setTipoUsuario(spinner_tipousuario.getSelectedItem().toString());
            presentador.mtdOnRegistro(obj);
        }
    }

    @Override
    public boolean mtdValidarEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(et_correo.getText().toString()).matches();
    }

    @Override
    public boolean mtdValidarPassword() {
        //TextUtils.isEmpty: si esta vacio y es menor que 4, no es valido
        if (TextUtils.isEmpty(et_password.getText().toString()) && et_password.getText().toString().length() < 4) {
            Toast.makeText(RegistroActivity.this, "No es una contraseña valida", Toast.LENGTH_SHORT).show();
            et_password.setError("No es una contraseña valida");
            return false; // devuelve false
        } else {
            return true;
        }
    }

    @Override
    public void mtdOnRegistro() {
        //Toast.makeText(this, "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();
        Usuario obj = new Usuario();
        obj.setEmail(et_correo.getText().toString().trim());
        Helper.CapturarDataUsuarioSesion(this, obj);
        Intent i = new Intent(RegistroActivity.this, MapActivity.class);
        startActivity(i);
    }

    @Override
    public void mtdOnError(String error) {
        Toast.makeText(RegistroActivity.this, error, Toast.LENGTH_SHORT).show();//muestra el error que envia el tasklistener
    }
}