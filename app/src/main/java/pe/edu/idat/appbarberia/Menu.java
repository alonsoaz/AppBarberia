package pe.edu.idat.appbarberia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pe.edu.idat.appbarberia.API.Sesion;

public class Menu extends AppCompatActivity {

    private Button barberos, productos, carrito, compras, reservas, info, salir;

    Sesion s  = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

            if(getIntent().hasExtra("sesion")) {
                s = getIntent().getParcelableExtra("sesion");

                Toast.makeText(this,getIntent().getParcelableExtra("sesion").toString(),Toast.LENGTH_SHORT).show();
            }


        barberos=(Button)findViewById(R.id.btn_barbero);
        barberos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barberos = new Intent(Menu.this, Barberos.class);
                barberos.putExtra("sesion",s);
                startActivity(barberos);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });

        productos=(Button)findViewById(R.id.btn_productos);
        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productos=new Intent(Menu.this, Productos.class);
                productos.putExtra("sesion",s);
                startActivity(productos);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });

        carrito=(Button)findViewById(R.id.btn_carrito);
        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carrito=new Intent(Menu.this, Carrito.class);
                carrito.putExtra("sesion", s);
                startActivity(carrito);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });

        compras=(Button)findViewById(R.id.btn_compras);
        compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compras=new Intent(Menu.this, Compras.class);
                compras.putExtra("sesion", s);
                startActivity(compras);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });

        reservas=(Button)findViewById(R.id.btn_reserva);
        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reservas=new Intent(Menu.this, Reservas.class);
                reservas.putExtra("sesion", s);
                startActivity(reservas);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });

        info=(Button)findViewById(R.id.btn_miperfil);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info= new Intent(Menu.this, Perfil.class);
                info.putExtra("sesion", s);
                startActivity(info);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });

        salir=(Button)findViewById(R.id.btn_logout);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salir= new Intent(Menu.this, Login.class);
                s.setIdCliente(0);
                salir.putExtra("sesion", s);
                startActivity(salir);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });

    }
}
