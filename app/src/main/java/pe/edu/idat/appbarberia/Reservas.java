package pe.edu.idat.appbarberia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import pe.edu.idat.appbarberia.API.Link;
import pe.edu.idat.appbarberia.API.Sesion;
import pe.edu.idat.appbarberia.Adaptadores.ReservaAdapter;
import pe.edu.idat.appbarberia.Modelos.Parcel.ReservaModel;

public class Reservas extends AppCompatActivity {

    private Button btnMenu;
    private ReservaAdapter adapter;
    private RecyclerView rvReserva;

    private ProgressDialog progressDialog;

    Sesion sAdd = new Sesion();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvReserva = findViewById(R.id.rvReservas);
        rvReserva.setLayoutManager(new LinearLayoutManager(Reservas.this));
        adapter = new ReservaAdapter(Reservas.this);
        rvReserva.setAdapter(adapter);

        Sesion s=getIntent().getParcelableExtra("sesion");
        sAdd.setIdCliente(s.getIdCliente());
        sAdd.setUrl(s.getUrl());

        final Link link = new Link(s);
        Toast.makeText(Reservas.this,s.toString(),Toast.LENGTH_SHORT).show();

        final String urlReservas = link.listarReservas;

        if(getIntent().hasExtra("sesion")){

            listarReservas(urlReservas);

        }else if(!getIntent().hasExtra("sesion")){

            Toast.makeText(Reservas.this,"No se ha recibido la lista de los barberos!",Toast.LENGTH_SHORT).show();

        }

        btnMenu=(Button)findViewById(R.id.res_btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(Reservas.this, Menu.class);
                menu.putExtra("sesion",sAdd);
                startActivity(menu);
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
            Intent intInfo = new Intent(Reservas.this, Informacion.class);
            startActivity(intInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void listarReservas(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.GET,url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<ReservaModel> rsrv = new ArrayList<>();
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        rsrv.add(new ReservaModel(
                                jsonObject.getInt("aidReserva"),
                                jsonObject.getString("bCLiente"),
                                jsonObject.getString("cServicio"),
                                jsonObject.getString("fBarbero"),
                                jsonObject.getString("gInicio"),
                                jsonObject.getString("hFin"),
                                jsonObject.getString("iEstado")
                        ));
                    }

                    if(rsrv.size()>0){

                        adapter.agregarElemento(rsrv,sAdd);

                    }else if(rsrv.size()==0){

                        Toast.makeText(getApplicationContext(),"El servicio no ha encontrado datos para listar!",Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(getApplicationContext(),"No se ha dispuesto de un servicio válido!",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser
                                    .parseCharset(response.headers));
                    return Response.success(new JSONArray(jsonString),
                            HttpHeaderParser
                                    .parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        jsonArrayJsonRequest.setRetryPolicy(new DefaultRetryPolicy
                (15000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonArrayJsonRequest);

    }

}