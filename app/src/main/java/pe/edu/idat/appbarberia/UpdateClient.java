package pe.edu.idat.appbarberia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import pe.edu.idat.appbarberia.Modelos.JSON.ClienteBeans;
import pe.edu.idat.appbarberia.Modelos.JSON.MensajesBeans;

public class UpdateClient extends AppCompatActivity implements View.OnFocusChangeListener{

    private Button updt, menu;

    private EditText textName, textApel, textEmail, textDni, textCel;

    private ProgressDialog progressDialog;

    private boolean correcto = true;

    Sesion s  = new Sesion();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textName = (EditText) findViewById(R.id.upd_edt_name);
        textApel = (EditText) findViewById(R.id.upd_edt_apellido);
        textEmail = (EditText) findViewById(R.id.upd_edt_mail);
        textDni = (EditText) findViewById(R.id.upd_edt_dni);
        textCel = (EditText) findViewById(R.id.upd_edt_cel);

        textName.setOnFocusChangeListener(this);
        textApel.setOnFocusChangeListener(this);
        textEmail.setOnFocusChangeListener(this);
        textDni.setOnFocusChangeListener(this);
        textCel.setOnFocusChangeListener(this);

        s = getIntent().getParcelableExtra("sesion");
        s.setUrl(s.getUrl());

        final Link link = new Link(s);
        Toast.makeText(UpdateClient.this,s.getUrl(),Toast.LENGTH_SHORT).show();

        final String urlActualizar = link.actualizar;
        final String urlGetCliente = link.getCliente;

        getCliente(urlGetCliente);

        progressDialog = new ProgressDialog(this);

        menu=(Button)findViewById(R.id.upd_btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(UpdateClient.this, Menu.class);
                menu.putExtra("sesion",s);
                startActivity(menu);
            }
        });

        updt=(Button)findViewById(R.id.upd_btnRegistro);
        updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().hasExtra("sesion")) {

                    submitClient(urlActualizar);

                }else{
                    Toast.makeText(UpdateClient.this,"No se han recibido los parámetros de la sesión!", Toast.LENGTH_SHORT).show();
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
            Intent intInfo = new Intent(UpdateClient.this, Informacion.class);
            startActivity(intInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitClient(String url){

        final String dni = textDni.getText().toString().trim();
        final String nombre = textName.getText().toString().trim();
        final String apellido = textApel.getText().toString().trim();
        final String correo = textEmail.getText().toString().trim();
        final String cel = textCel.getText().toString().trim();

        if(TextUtils.isEmpty(correo)){
            Toast.makeText(this,"Se debe ingresar un email!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }
        if(TextUtils.isEmpty(cel)){
            Toast.makeText(this,"Falta ingresar la contraseña!", Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }
        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Se debe ingresar un nombre!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }
        if(TextUtils.isEmpty(apellido)){
            Toast.makeText(this,"Falta ingresar su apellido!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }
        if(TextUtils.isEmpty(dni)){
            Toast.makeText(this,"Se debe ingresar un número de DNI!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }

        if(correcto){
            ClienteBeans cliente = new ClienteBeans(
                    nombre,
                    apellido,
                    cel,
                    dni,
                    correo,
                    "",
                    ""
            );
            Registro(url, cliente);
        }

    }

    private void getCliente(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.GET,url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<ClienteBeans> clt = new ArrayList<>();
                    int id = 0;
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        clt.add(new ClienteBeans(
                                jsonObject.getString("bnombre"),
                                jsonObject.getString("capellido"),
                                jsonObject.getString("dtelefono"),
                                jsonObject.getString("eDni"),
                                jsonObject.getString("fcorreo"),
                                "",
                                ""
                        ));
                        id = jsonObject.getInt("idEstado");
                    }

                    if(id>0){

                        textName.setText(clt.get(0).getANombre());
                        textApel.setText(clt.get(0).getBApellido());
                        textEmail.setText(clt.get(0).getEEmail());
                        textDni.setText(clt.get(0).getDDni());
                        textCel.setText(clt.get(0).getCTelefono());

                    }else if(id==0){

                        Toast.makeText(getApplicationContext(),"El servicio no ha encontrado datos para actualizar!",Toast.LENGTH_LONG).show();

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

        jsonArrayJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonArrayJsonRequest);

    }

    private void Registro(String url, ClienteBeans cliente){

        JSONObject obj = new JSONObject();
        try {
            obj.put("bnombre", cliente.getANombre());
            obj.put("capellido", cliente.getBApellido());
            obj.put("dtelefono", cliente.getCTelefono());
            obj.put("eDni", cliente.getDDni());
            obj.put("fcorreo", cliente.getEEmail());
            obj.put("gdireccion", cliente.getFDirecion());
            obj.put("idEstado", 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.PUT,url
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

                    if(mensaje.contains("X")){

                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
                        progressDialog.setMessage("Efectuando la actualización de los datos del cliente...");
                        progressDialog.show();
                        cambiar(s,Perfil.class);

                    }else if(!mensaje.contains("X")){

                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(getApplicationContext(),"El servicio no ha podido conceder la actualización de los datos.",Toast.LENGTH_LONG).show();

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

        Intent intent = new Intent(UpdateClient.this, aClass);
        intent.putExtra("sesion", sesion);
        startActivity(intent);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(!hasFocus){
            if(textName.length()>40) {
                correcto = false;
                Toast.makeText(UpdateClient.this, "Proceda a registrar un nombre más corto!", Toast.LENGTH_SHORT).show();
            }else if(textName.length()==0){
                correcto = false;
                Toast.makeText(UpdateClient.this, "Proceda a registrar un nombre!", Toast.LENGTH_SHORT).show();
            }else if(textName.length()<40||textName.length()>0){
                Toast.makeText(UpdateClient.this,s.getUrl(),Toast.LENGTH_SHORT).show();
                correcto=true;
            }
            if(textApel.length()>40) {
                correcto = false;
                Toast.makeText(UpdateClient.this, "Proceda a registrar un apellido más corto!", Toast.LENGTH_SHORT).show();
            }else if(textApel.length()==0){
                correcto = false;
                Toast.makeText(UpdateClient.this, "Proceda a registrar un apellido!", Toast.LENGTH_SHORT).show();
            }else if(textApel.length()<40||textApel.length()>0){
                correcto = true;
            }
            if(textEmail.length()>45) {
                correcto = false;
                Toast.makeText(UpdateClient.this, "Proceda a registrar un correo electrónico más corto!", Toast.LENGTH_SHORT).show();
            }else if(textEmail.length()==0){
                correcto = false;
                Toast.makeText(UpdateClient.this, "Proceda a registrar un correo electrónico!", Toast.LENGTH_SHORT).show();
            }else if(textEmail.length()<45||textEmail.length()>0){
                correcto = true;
            }
            if(textDni.length()<8&&textDni.length()==0) {
                correcto = false;
                Toast.makeText(UpdateClient.this, "Debe ingresar un número de DNI válido!", Toast.LENGTH_SHORT).show();
            }else if(textDni.length()==8){
                correcto = true;
            }
            if(textCel.length()>9||textCel.length()==0) {
                correcto = false;
                Toast.makeText(UpdateClient.this, "Debe ingresar número de teléfono celular válido!", Toast.LENGTH_SHORT).show();
            }else if(textCel.length()==9){
                correcto = true;
            }

        }

    }
}
