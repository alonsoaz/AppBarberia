package pe.edu.idat.appbarberia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import pe.edu.idat.appbarberia.Adaptadores.ComprasAdapter;
import pe.edu.idat.appbarberia.Modelos.Parcel.ComprasModel;

public class Compras extends AppCompatActivity {

    private Button menu;
    private ComprasAdapter adapter;
    private RecyclerView rvCompras;

    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        rvCompras = findViewById(R.id.rvCompras);
        rvCompras.setLayoutManager(new LinearLayoutManager(Compras.this));
        adapter = new ComprasAdapter(Compras.this);
        rvCompras.setAdapter(adapter);

        if(getIntent().hasExtra("sesion")){
            sesion = getIntent().getParcelableExtra("sesion");
        }

        Link link = new Link(sesion);
        String urlCompras = link.showVentaPagada;
        listarCompras(urlCompras);

        menu=(Button)findViewById(R.id.com_btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(Compras.this, Menu.class);
                menu.putExtra("sesion",sesion);
                startActivity(menu);
                overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
            }
        });

    }

    private void listarCompras(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.GET,url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length()>0){

                        ArrayList<ComprasModel> com = new ArrayList<>();
                        for(int i=0; i<response.length();i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            com.add(new ComprasModel(
                                    jsonObject.getInt("aidVenta"),
                                    jsonObject.getString("bCodVenta"),
                                    jsonObject.getString("cCodCliente"),
                                    jsonObject.getString("dEstado"),
                                    jsonObject.getString("eCliente"),
                                    jsonObject.getDouble("fTotal"),
                                    jsonObject.getString("gFecha")
                            ));
                        }

                        adapter.agregarElemento(com,sesion);

                    }else if(response.length()==0){

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