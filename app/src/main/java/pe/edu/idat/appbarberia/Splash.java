package pe.edu.idat.appbarberia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private ImageView ivImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ivImagen = findViewById(R.id.splash);
        Animation animation = AnimationUtils.loadAnimation(
                this, R.anim.transicion);

        ivImagen.startAnimation(animation);

        final Intent intLogin = new Intent(
                Splash.this,
                IngresarLink.class);


        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    startActivity(intLogin);
                    finish();
                }
            }
        };
        timer.start();



    }

}
