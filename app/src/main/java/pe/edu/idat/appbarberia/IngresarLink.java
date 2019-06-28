package pe.edu.idat.appbarberia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IngresarLink extends AppCompatActivity {

    EditText edt;
    Button btn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_link);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt = findViewById(R.id.getLink);
        btn = findViewById(R.id.btnNgrok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = edt.getText().toString().trim();
                if(edt.length()<15) {
                    accion(link);
                }else if(edt.length()==0) {
                    Toast.makeText(IngresarLink.this,"Debe ingresar un valor!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if(idItem == R.id.opcionUno){
            Intent intInfo = new Intent(IngresarLink.this, Informacion.class);
            startActivity(intInfo);
            overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
        }
        return super.onOptionsItemSelected(item);
    }

    private void accion(String link){
        Intent intent = new Intent(IngresarLink.this, Login.class);
        intent.putExtra("link",link);
        startActivity(intent);
    }

}
