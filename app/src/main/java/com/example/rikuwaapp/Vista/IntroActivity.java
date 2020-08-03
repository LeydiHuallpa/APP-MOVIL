package com.example.rikuwaapp.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rikuwaapp.Config.Helper;
import com.example.rikuwaapp.R;

public class IntroActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    //Hooks
    View primero, segundo, tercero, cuarto, quinto, sexto;
    TextView titulo;
    ImageView imagen;
    //Animations
    Animation topAnimantion, bottomAnimation, middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        topAnimantion = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);


        primero = findViewById(R.id.linea1);
        segundo = findViewById(R.id.linea2);
        tercero = findViewById(R.id.linea3);
        cuarto = findViewById(R.id.linea4);
        quinto = findViewById(R.id.linea5);
        sexto = findViewById(R.id.linea6);
        imagen = findViewById(R.id.logo);
        titulo = findViewById(R.id.titulo);


        primero.setAnimation(topAnimantion);
        segundo.setAnimation(topAnimantion);
        tercero.setAnimation(topAnimantion);
        cuarto.setAnimation(topAnimantion);
        quinto.setAnimation(topAnimantion);
        sexto.setAnimation(topAnimantion);
        imagen.setAnimation(middleAnimation);
        titulo.setAnimation(bottomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Helper.VerificarSesionActiva(IntroActivity.this)) {
                    String tipoUsuario = Helper.ObtenerValorAtributoSharedPreferences(IntroActivity.this, "tipoUsuario");
                    Intent intent;
                    switch (tipoUsuario) {
                        case "Administrador":
                            intent = new Intent(IntroActivity.this, AdministradorActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            intent = new Intent(IntroActivity.this, MapActivity.class);
                            startActivity(intent);
                            break;
                    }
                } else {
                    Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}