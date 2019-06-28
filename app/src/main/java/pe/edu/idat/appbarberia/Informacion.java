package pe.edu.idat.appbarberia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import pe.edu.idat.appbarberia.API.Sesion;

public class Informacion extends AppCompatActivity {

    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        if(getIntent().hasExtra("sesion")){
            sesion = getIntent().getParcelableExtra("sesion");
        }

    }
}
