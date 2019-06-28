package pe.edu.idat.appbarberia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pe.edu.idat.appbarberia.API.Sesion;

public class Perfil extends AppCompatActivity {

    private Sesion sesion = new Sesion();
    private Button menu, exit, password, update;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        menu = findViewById(R.id.prf_btnMenu);
        exit = findViewById(R.id.prf_btnClose);
        password = findViewById(R.id.prf_btn_password);
        update  = findViewById(R.id.prf_btn_update);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("sesion")){
            sesion = getIntent().getParcelableExtra("sesion");
        }else{
            Toast.makeText(Perfil.this,"No se ha conseguido un parámetro de conexión!", Toast.LENGTH_LONG).show();
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar (sesion,Menu.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sesion.setIdCliente(0);
                cambiar(sesion,Login.class);
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar (sesion,Password.class);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar(sesion,UpdateClient.class);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if(idItem == R.id.opcionUno){
            Intent intInfo = new Intent(Perfil.this, Informacion.class);
            startActivity(intInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void cambiar(Sesion sesion, Class aClass){

        Intent intent = new Intent(Perfil.this, aClass);
        intent.putExtra("sesion", sesion);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);

    }

}
