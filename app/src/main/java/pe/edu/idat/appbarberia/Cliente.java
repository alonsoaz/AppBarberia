package pe.edu.idat.appbarberia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Cliente extends AppCompatActivity implements View.OnFocusChangeListener{

    private Button info, menu, logout;

    private EditText textName, textApel, textEmail, textDni, textCel, textPass, textcPass;
    private TextView  Cliente, Name, Apel, Mail, Dni, Cel;

    private Spinner spCorreo;
    private String [] itemsCorreo;
    private String itemCorreo;

    private ProgressDialog progressDialog;

    private boolean correcto = true;

    Sesion s  = new Sesion();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cliente = (TextView) findViewById(R.id.txtCliente);
        Name = (TextView) findViewById(R.id.clt_txt_name);
        Apel = (TextView) findViewById(R.id.clt_txt_apellido);
        Mail = (TextView) findViewById(R.id.clt_txt_mail);
        Dni = (TextView) findViewById(R.id.clt_txt_dni);
        Cel = (TextView) findViewById(R.id.clt_txt_cel);

        textName = (EditText) findViewById(R.id.clt_edt_name);
        textApel = (EditText) findViewById(R.id.clt_edt_apellido);
        textEmail = (EditText) findViewById(R.id.clt_edt_mail);
        textDni = (EditText) findViewById(R.id.clt_edt_dni);
        textCel = (EditText) findViewById(R.id.clt_edt_cel);
        textPass = (EditText) findViewById(R.id.clt_edt_pw);
        textcPass = (EditText) findViewById(R.id.clt_edt_cpw);

        textName.setOnFocusChangeListener(this);
        textApel.setOnFocusChangeListener(this);
        textEmail.setOnFocusChangeListener(this);
        textDni.setOnFocusChangeListener(this);
        textCel.setOnFocusChangeListener(this);
        textPass.setOnFocusChangeListener(this);
        textcPass.setOnFocusChangeListener(this);

        s = getIntent().getParcelableExtra("sesion");
        s.setUrl(s.getUrl());

        final Link link = new Link(s);
        Toast.makeText(Cliente.this,s.getUrl(),Toast.LENGTH_SHORT).show();

        final String urlRegistro = link.registro;

        spCorreo = (Spinner)findViewById(R.id.clt_sp_correo);
        itemsCorreo = getResources().getStringArray(R.array.lstCorreos);

        ArrayAdapter<String> lstAdapter = new ArrayAdapter<>(
                getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,
                itemsCorreo
        );

        lstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCorreo.setAdapter(lstAdapter);

        spCorreo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemCorreo = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemCorreo = "@gmail.com";
            }
        });

        progressDialog = new ProgressDialog(this);

        info=(Button)findViewById(R.id.clt_btnMenu);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(pe.edu.idat.appbarberia.Cliente.this, Informacion.class);
                info.putExtra("sesion",s);
                startActivity(info);
                overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
            }
        });

        menu=(Button)findViewById(R.id.clt_btnRegistro);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().hasExtra("sesion")) {

                    submitClient(urlRegistro);

                }else{
                    Toast.makeText(Cliente.this,"No se han recibido los parámetros de la sesión!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logout=(Button)findViewById(R.id.clt_btnClose);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(pe.edu.idat.appbarberia.Cliente.this, Login.class);
                startActivity(logout);
                cambiar(s);
                overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
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
            Intent intInfo = new Intent(Cliente.this, Informacion.class);
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
        final String korreo = correo+itemCorreo;
        final String password = textPass.getText().toString().trim();
        final String cpassword = textcPass.getText().toString().trim();

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
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Se debe ingresar una contraseña!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }
        if(TextUtils.isEmpty(cpassword)){
            Toast.makeText(this,"Se debe ingresar la confirmación de la contraseña!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }

        if(!password.equals(cpassword)){
            Toast.makeText(this,"Las contraseñas igresadas no coinciden!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else if(password.equals(cpassword)){
            correcto = true;
        }

        if(correcto){
            ClienteBeans cliente = new ClienteBeans(
                    nombre,
                    apellido,
                    cel,
                    dni,
                    korreo,
                    "",
                    password
                );
            Registro(url, cliente);
        }
    }

    private void Registro(String url, ClienteBeans cliente){

        JSONObject obj = new JSONObject();
        try {
            obj.put("aNombre", cliente.getANombre());
            obj.put("bApellido", cliente.getBApellido());
            obj.put("cTelefono", cliente.getCTelefono());
            obj.put("dDni", cliente.getDDni());
            obj.put("eEmail", cliente.getEEmail());
            obj.put("fDirecion", cliente.getFDirecion());
            obj.put("password", cliente.getPassword());

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

                    if(mensaje.contains("X")){

                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
                        progressDialog.setMessage("Efectuando el registro...");
                        progressDialog.show();
                        cambiar(s);

                    }else if(!mensaje.contains("X")){

                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(getApplicationContext(),"El servicio no ha podido conceder el registro.",Toast.LENGTH_LONG).show();

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

    private void cambiar(Sesion sesion){

        Intent intNt = new Intent(Cliente.this,Login.class);
        intNt.putExtra("sesion",sesion);
        startActivity(intNt);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(!hasFocus){
            if(textName.length()>40) {
                correcto = false;
                Toast.makeText(Cliente.this, "Proceda a registrar un nombre más corto!", Toast.LENGTH_SHORT).show();
            }else if(textName.length()==0){
                correcto = false;
                Toast.makeText(Cliente.this, "Proceda a registrar un nombre!", Toast.LENGTH_SHORT).show();
            }else if(textName.length()<40||textName.length()>0){
                Toast.makeText(Cliente.this,s.getUrl(),Toast.LENGTH_SHORT).show();
                correcto=true;
            }
            if(textApel.length()>40) {
                correcto = false;
                Toast.makeText(Cliente.this, "Proceda a registrar un apellido más corto!", Toast.LENGTH_SHORT).show();
            }else if(textApel.length()==0){
                correcto = false;
                Toast.makeText(Cliente.this, "Proceda a registrar un apellido!", Toast.LENGTH_SHORT).show();
            }else if(textApel.length()<40||textApel.length()>0){
                correcto = true;
            }
            if(textEmail.length()>45) {
                correcto = false;
                Toast.makeText(Cliente.this, "Proceda a registrar un correo electrónico más corto!", Toast.LENGTH_SHORT).show();
            }else if(textEmail.length()==0){
                correcto = false;
                Toast.makeText(Cliente.this, "Proceda a registrar un correo electrónico!", Toast.LENGTH_SHORT).show();
            }else if(textEmail.length()<45||textEmail.length()>0){
                correcto = true;
            }
            if(textDni.length()<8||textDni.length()>0) {
                correcto = false;
                Toast.makeText(Cliente.this, "Debe ingresar un número de DNI válido!", Toast.LENGTH_SHORT).show();
            }else if(textDni.length()==8){
                correcto = true;
            }
            if(textPass.length()<8||textPass.length()==0) {
                correcto = false;
                Toast.makeText(Cliente.this, "Debe ingresar una contraseña válida!", Toast.LENGTH_SHORT).show();
            }else if(textPass.length()==8){
                correcto = true;
            }
            if(textcPass.length()<8||textcPass.length()==0) {
                correcto = false;
                Toast.makeText(Cliente.this, "Debe ingresar una contraseña válida!", Toast.LENGTH_SHORT).show();
            }else if(textcPass.length()==8){
                correcto = true;
            }
            if(textCel.length()<9||textCel.length()==0) {
                correcto = false;
                Toast.makeText(Cliente.this, "Debe ingresar número de teléfono celular válido!", Toast.LENGTH_SHORT).show();
            }else if(textCel.length()==9){
                correcto = true;
            }
        }
    }
}
