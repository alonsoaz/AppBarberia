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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import pe.edu.idat.appbarberia.Modelos.JSON.MessagenID;
import pe.edu.idat.appbarberia.API.Sesion;

public class Login extends AppCompatActivity implements View.OnFocusChangeListener{

    private Button registrar, ingresar;
    private EditText textAccount, textPassword;
    private ProgressDialog progressDialog;
    private String url = "";
    Sesion s  = new Sesion();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textAccount = (EditText) findViewById(R.id.edt_user);
        textPassword = (EditText) findViewById(R.id.edt_pass);

        textAccount.setOnFocusChangeListener(this);
        textPassword.setOnFocusChangeListener(this);

        progressDialog = new ProgressDialog(this);

        url = getIntent().getStringExtra("link");
        s.setUrl(url);

        if(getIntent().hasExtra("sesion")){
            s  = getIntent().getParcelableExtra("sesion");
            url = s.getUrl();
        }

        Toast.makeText(this, s.toString(),Toast.LENGTH_SHORT).show();

        final Link link = new Link(s);
        Toast.makeText(Login.this,url,Toast.LENGTH_SHORT).show();

        registrar=(Button)findViewById(R.id.btnRegistro);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrar =new Intent(Login.this, Cliente.class);
                registrar.putExtra("sesion",s);
                startActivity(registrar);
                overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
            }
        });

        ingresar=(Button)findViewById(R.id.btnLogin);
        if (getIntent().hasExtra("link")||getIntent().hasExtra("sesion")) {
            Toast.makeText(this,"Parámetro de conexión ingresado!", Toast.LENGTH_LONG).show();
            ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String account = textAccount.getText().toString().trim();
                    final String password = textPassword.getText().toString().trim();
                    final String urlLogin = link.login;
                    Login(urlLogin, account, password);

                }

            });
        }
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
            Intent intInfo = new Intent(Login.this, Informacion.class);
            startActivity(intInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Login(String url, String account, String password){

        if(TextUtils.isEmpty(account)){
            Toast.makeText(this,"Se debe ingresar un número de DNI!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"se debe ingresar una contraseña!", Toast.LENGTH_LONG).show();
            return;
        }

        JSONObject obj = new JSONObject();
        try {
            obj.put("aDni", account);
            obj.put("bPassword", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.POST, url, obj.toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<MessagenID> msgid = new ArrayList<>();
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        msgid.add(new MessagenID(jsonObject.getString("message"),
                                jsonObject.getInt("id")));
                    }

                    if(msgid.get(0).getId()>0){
                        s.setIdCliente(msgid.get(0).getId());
                        cambiar(s);
                        Toast.makeText(getApplicationContext(),msgid.get(0).getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.setMessage("Ingresando en la aplicación...");
                        progressDialog.show();
                    }else if(msgid.get(0).getId()==0){
                        Toast.makeText(getApplicationContext(),msgid.get(0).getMessage(),Toast.LENGTH_LONG).show();
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

    private void cambiar(Sesion sesion){

        Intent intNt = new Intent(this,Perfil.class);
        intNt.putExtra("sesion",sesion);
        startActivity(intNt);
        overridePendingTransition(R.anim.right_in,R.anim.right_out);


    }

    @Override
    public void onFocusChange(View view, boolean hasFocus ) {
        if(!hasFocus){
            if(textAccount.length()<8&&textAccount.length()>0) {
                Toast.makeText(Login.this, "Debe ingresar un número de DNI válido!", Toast.LENGTH_SHORT).show();
            }
            if(textPassword.length()<8&&textPassword.length()>0) {
                Toast.makeText(Login.this, "Debe ingresar una contraseña válida!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
