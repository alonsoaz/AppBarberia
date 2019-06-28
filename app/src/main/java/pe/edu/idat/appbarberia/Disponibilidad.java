package pe.edu.idat.appbarberia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import pe.edu.idat.appbarberia.API.Link;
import pe.edu.idat.appbarberia.API.Sesion;
import pe.edu.idat.appbarberia.Modelos.JSON.MensajesBeans;
import pe.edu.idat.appbarberia.Modelos.JSON.MessagenID;
import pe.edu.idat.appbarberia.Modelos.Parcel.DisponibilidadBeans;

public class Disponibilidad extends AppCompatActivity implements View.OnFocusChangeListener{

    private Button menu, btconsulta, btregistra;
    private EditText txtday, txtmensaje;

    private Spinner spMes, spAnio, spHour, spMin;
    private String[] itemsMes, itemsAnio, itemsHour, itemsMin;
    private String itemMes, itemAnio, itemHour, itemMin;

    private ProgressDialog progressDialog;

    private boolean correcto = true;

    Sesion s = new Sesion();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidad);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtday = findViewById(R.id.agend_edt_day);
        txtmensaje = findViewById(R.id.agend_edt_msj);

        txtday.setText("0");

        txtday.setOnFocusChangeListener(this);

        s = getIntent().getParcelableExtra("sesion");
        s.setUrl(s.getUrl());

        final Link link = new Link(s);
        Toast.makeText(Disponibilidad.this,s.getUrl(),Toast.LENGTH_SHORT).show();

        final String urlConsulta = link.consultaDisponibilidad;
        final String urlRegistro = link.registraReserva;

        spMes = (Spinner)findViewById(R.id.agend_spn_mes);
        spAnio = (Spinner)findViewById(R.id.agend_spn_anho);
        spHour = (Spinner)findViewById(R.id.agend_spn_hora);
        spMin = (Spinner)findViewById(R.id.agend_spn_min);

        itemsMes = getResources().getStringArray(R.array.mes);
        itemsAnio = getResources().getStringArray(R.array.anho);
        itemsHour = getResources().getStringArray(R.array.hour);
        itemsMin = getResources().getStringArray(R.array.minute);

        ArrayAdapter<String> lstAdapterMes = new ArrayAdapter<>(
                getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,
                itemsMes
        );
        ArrayAdapter<String> lstAdapterAnio = new ArrayAdapter<>(
                getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,
                itemsAnio
        );
        ArrayAdapter<String> lstAdapterHour = new ArrayAdapter<>(
                getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,
                itemsHour
        );
        ArrayAdapter<String> lstAdapterMin = new ArrayAdapter<>(
                getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,
                itemsMin
        );

        lstAdapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAdapterAnio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAdapterHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAdapterMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMes.setAdapter(lstAdapterMes);
        spAnio.setAdapter(lstAdapterAnio);
        spHour.setAdapter(lstAdapterHour);
        spMin.setAdapter(lstAdapterMin);

        spMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemMes = (parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemMes = "6";
            }
        });

        spAnio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemAnio = (parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemAnio = "2019";
            }
        });

        spHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemHour = (parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemHour = "12";
            }
        });

        spMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemMin = (parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemMin = "0";
            }
        });

        btconsulta = findViewById(R.id.agend_btnAsk);
        btconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().hasExtra("sesion")) {

                    validaTexto(urlConsulta, 2);

                }else{
                    Toast.makeText(Disponibilidad.this,"No se han recibido los parámetros de la sesión!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btregistra = findViewById(R.id.agend_btnReserva);
        btregistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().hasExtra("sesion")) {

                    validaTexto(urlRegistro, 1);

                }else{
                    Toast.makeText(Disponibilidad.this,"No se han recibido los parámetros de la sesión!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        menu=(Button)findViewById(R.id.agend_btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(Disponibilidad.this, Menu.class);
                Sesion s1 = new Sesion();
                s1.setIdCliente(s.getIdCliente());
                s1.setUrl(s.getUrl());
                menu.putExtra("sesion",s1);
                startActivity(menu);
                overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
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
            Intent intInfo = new Intent(Disponibilidad.this, Informacion.class);
            startActivity(intInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void validaTexto(String url, int r){

        final String day = (txtday.getText().toString().trim());
        final String hour = itemHour;
        final String min = itemMin;
        final String mes = itemMes;
        final String anio = itemAnio;

        if(TextUtils.isEmpty(day) || TextUtils.equals(day,"0")){
            Toast.makeText(this,"Se debe ingresar el día en que solicitará la reserva",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }

        if(correcto){

            DisponibilidadBeans d = new DisponibilidadBeans(

                    anio,mes,day,hour,min

            );


            if(r == 1){

                registro(s,url,d);

            }else if(r == 2){

                consulta(s,url,d);

            }

        }


    }

    private void consulta (Sesion session, String url, DisponibilidadBeans d){

        JSONObject obj = new JSONObject();
        try {
            obj.put("bYear", d.getbYear());
            obj.put("cMonth", d.getcMonth());
            obj.put("dDay", d.getdDay());
            obj.put("eHour", d.geteHour());
            obj.put("fMinut", d.getfMinut());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.POST,url
                , obj.toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<MensajesBeans> msg = new ArrayList<>();
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        msg.add(new MensajesBeans(jsonObject.getString("mensaje")));
                    }

                    String mensaje = msg.get(0).getMensaje();

                    if(!mensaje.contains("OCUPADO")){

                        txtmensaje.setText(mensaje);

                    }else if(mensaje.contains("OCUPADO")){

                        txtmensaje.setText(mensaje);

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

        jsonArrayJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonArrayJsonRequest);

    }

    private void registro (final Sesion ses, String url, DisponibilidadBeans d){


        JSONObject obj = new JSONObject();
        try {
            obj.put("bYear", d.getbYear());
            obj.put("cMonth", d.getcMonth());
            obj.put("dDay", d.getdDay());
            obj.put("eHour", d.geteHour());
            obj.put("fMinut", d.getfMinut());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.POST,url
                , obj.toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<MessagenID> msg = new ArrayList<>();
                    int res = 0;
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        msg.add(new MessagenID(jsonObject.getString("message"),
                                jsonObject.getInt("id")
                        ));
                        res = jsonObject.getInt("id");
                    }

                    String mensaje = msg.get(0).getMessage();

                    if(!mensaje.contains("OCUPADO")&&res!=0){

                        txtmensaje.setText(mensaje);
                        ses.setIdReserva(res);
                        cambiar(ses,Reservas.class);

                    }else if(mensaje.contains("OCUPADO")&&res==0){

                        txtmensaje.setText(mensaje);
                        Toast.makeText(getApplicationContext(),"No se ha registrado ninguna reserva", Toast.LENGTH_SHORT).show();

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

        jsonArrayJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonArrayJsonRequest);

    }

    private void cambiar(Sesion sesion, Class aClass){

        Intent intent = new Intent(Disponibilidad.this, aClass);
        intent.putExtra("sesion", sesion);
        startActivity(intent);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(hasFocus){
            txtday.setText("");
        }

        if(!hasFocus&&!txtday.equals("")&&Integer.parseInt(txtday.getText().toString().trim())>31) {

            if (Integer.parseInt(txtday.getText().toString().trim())>31) {
                correcto = false;
                Toast.makeText(Disponibilidad.this, "Proceda a registrar un número de día válido!", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(txtday.getText().toString().trim())<31) {
                correcto = true;
            }

        }

    }
}