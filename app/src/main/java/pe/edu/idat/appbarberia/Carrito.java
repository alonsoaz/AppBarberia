package pe.edu.idat.appbarberia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import pe.edu.idat.appbarberia.Modelos.JSON.MensajesBeans;

public class Carrito extends AppCompatActivity {

    private Button btnPay, btnDel, btnDetalle, menu;
    private EditText txtCodVenta, txtCodCliente, txtFecha, txtEstado, txtCliente, txtMonto, txtMensaje;

    ArrayList<Integer> listaDVxPagar = new ArrayList<>();

    Sesion sesion = new Sesion();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_carrito);
        txtCodVenta = findViewById(R.id.car_edt_codventa);
        txtCodCliente = findViewById(R.id.car_edt_codcliente);
        txtFecha = findViewById(R.id.car_edt_fecha);
        txtEstado = findViewById(R.id.car_edt_status);
        txtCliente = findViewById(R.id.car_edt_cliente);
        txtMonto = findViewById(R.id.car_edt_monto);
        txtMensaje = findViewById(R.id.car_edt_msj);
        btnPay = findViewById(R.id.car_btnPay);
        btnDel = findViewById(R.id.car_btnErase);

        if(getIntent().hasExtra("sesion")){
            sesion = getIntent().getParcelableExtra("sesion");
        }
        final Link link = new Link(sesion);

        String urlshowVentaXpagar = link.showVentaXpagar;
        final String urldelVenta = link.delVenta;
        final String urlPayVenta = link.payVenta;
        String urlshowDVxPagar = link.showDVxPagar;
        final String urlPayDetVenta = link.payDetVenta;

        showVentaXpagar(urlshowVentaXpagar);
        listarDVxPagar(urlshowDVxPagar, sesion);

        btnDetalle=(Button)findViewById(R.id.car_btnDetail);
        btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detalle = new Intent(Carrito.this, DetalleVentaxp.class);
                detalle.putExtra("sesion",sesion);
                startActivity(detalle);
                overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
            }
        });

        menu=(Button)findViewById(R.id.car_btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(Carrito.this, Menu.class);
                menu.putExtra("sesion",sesion);
                startActivity(menu);
                overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delVenta(urldelVenta);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payVenta(urlPayVenta);

                for(int i=0; i<listaDVxPagar.size();i++){
                    int id = listaDVxPagar.get(i);
                    payDetVenta(urlPayDetVenta+id);
                }

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
            Intent intInfo = new Intent(Carrito.this, Informacion.class);
            startActivity(intInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showVentaXpagar(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.GET,url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length()>0){

                        btnDel.setEnabled(true);
                        btnPay.setEnabled(true);
                        btnDetalle.setEnabled(true);

                        for(int i=0; i<response.length();i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            txtCodVenta.setText(jsonObject.getString("bcodVenta"));
                            txtCodCliente.setText(jsonObject.getString("ccodCliente"));
                            txtEstado.setText(jsonObject.getString("dEstado"));
                            txtCliente.setText(jsonObject.getString("eCliente"));
                            txtMonto.setText( String.format("%.2f", jsonObject.getDouble("fTotal")));
                            txtFecha.setText(jsonObject.getString("gFecha"));
                        }

                    }else{

                        Toast.makeText(getApplicationContext(),"No se han encontrado compras por pagar!",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Intent recargar = new Intent(Carrito.this,Carrito.class);
                    recargar.putExtra("sesion",sesion);
                    startActivity(recargar);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Intent recargar = new Intent(Carrito.this,Carrito.class);
                recargar.putExtra("sesion",sesion);
                startActivity(recargar);
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

        jsonArrayJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonArrayJsonRequest);

    }

    private void payVenta(final String url){

            final RequestQueue mQueue = Volley.newRequestQueue(this);
            JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                    Request.Method.POST
                    , url
                    , "[]"
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        if(response.length()>0){

                            ArrayList<MensajesBeans> msg = new ArrayList<>();

                            for(int i=0; i<response.length();i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                msg.add(new MensajesBeans(jsonObject.getString("mensaje")));
                            }

                            String mensaje = msg.get(0).getMensaje();

                            btnDel.setEnabled(false);
                            btnPay.setEnabled(false);
                            btnDetalle.setEnabled(false);

                            txtMensaje.setText(mensaje);

                        }else if(response.length()==0){

                            Toast.makeText(getApplicationContext(),"El servicio no ha podido conceder la consulta.",Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(getApplicationContext(),"El servicio no ha podido conceder la consulta.",Toast.LENGTH_LONG).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        payVenta(url);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    payVenta(url);
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
            mQueue.add(jsonArrayJsonRequest);

    }

    private void delVenta(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.POST
                , url
                , "[]"
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<MensajesBeans> msg = new ArrayList<>();
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        msg.add(new MensajesBeans(jsonObject.getString("mensaje")
                        ));
                    }

                    String mensaje = msg.get(0).getMensaje();

                    if(response.length()>0){

                        btnDel.setEnabled(false);
                        btnPay.setEnabled(false);
                        btnDetalle.setEnabled(false);

                        txtMensaje.setText(mensaje);

                    }else if(response.length()==0){

                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(getApplicationContext(),"El servicio no ha podido conceder la consulta.",Toast.LENGTH_LONG).show();

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
        mQueue.add(jsonArrayJsonRequest);

    }

    private void listarDVxPagar(String url, final Sesion sesion){

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.GET,
                url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length()>0){

                        for(int i=0; i<response.length();i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            listaDVxPagar.add(jsonObject.getInt("aidDetalleventa"));
                        }

                    }else{

                        Toast.makeText(getApplicationContext(),"No se encontraron elementos dentro del carro de compras!",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Intent recargar = new Intent(Carrito.this,Carrito.class);
                    recargar.putExtra("sesion",sesion);
                    startActivity(recargar);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Intent recargar = new Intent(Carrito.this,Carrito.class);
                recargar.putExtra("sesion",sesion);
                startActivity(recargar);            }
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

        jsonArrayJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonArrayJsonRequest);

    }

    private void payDetVenta(final String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.POST
                , url
                , "[]"
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<MensajesBeans> msg = new ArrayList<>();
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        msg.add(new MensajesBeans(jsonObject.getString("mensaje")
                        ));
                    }

                    String mensaje = msg.get(0).getMensaje();

                    if(response.length()>0){

                        Toast.makeText(getApplicationContext(),"Los detalles de la venta han sido pagados!",Toast.LENGTH_SHORT).show();

                    }else if(response.length()==0){

                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(getApplicationContext(),"El servicio no ha podido conceder la consulta.",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    payDetVenta(url);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                payDetVenta(url);
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
        mQueue.add(jsonArrayJsonRequest);

    }

}