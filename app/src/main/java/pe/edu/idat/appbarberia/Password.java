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
import pe.edu.idat.appbarberia.Modelos.JSON.MessagenID;
import pe.edu.idat.appbarberia.Modelos.JSON.PasswordBeans;

public class Password extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText password, newpassword, cpassword;
    private Button btnMenu, btnChange;

    private ProgressDialog progressDialog;

    private boolean correcto = true;

    private Sesion s  = new Sesion();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMenu = findViewById(R.id.pwd_btnMenu);
        btnChange = findViewById(R.id.pwd_btnRegistro);
        password = findViewById(R.id.pwd_edt_password);
        newpassword = findViewById(R.id.pwd_edt_newpassword);
        cpassword = findViewById(R.id.pwd_edt_cpassword);

        password.setOnFocusChangeListener(this);
        newpassword.setOnFocusChangeListener(this);
        cpassword.setOnFocusChangeListener(this);

        s = getIntent().getParcelableExtra("sesion");
        s.setUrl(s.getUrl());

        final Link link = new Link(s);
        Toast.makeText(Password.this,s.getUrl(),Toast.LENGTH_SHORT).show();

        final String urlPassword = link.cambiarPassword;

        progressDialog = new ProgressDialog(this);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar(s,Menu.class);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().hasExtra("sesion")){
                    validacionInputTexto(urlPassword);
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
            Intent intInfo = new Intent(Password.this, Informacion.class);
            startActivity(intInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void validacionInputTexto(String url){

        String pass = password.getText().toString();
        String newpass = newpassword.getText().toString();
        String cpass = cpassword.getText().toString();

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Debe ingresar la contraseña actual!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }

        if(TextUtils.isEmpty(newpass)){
            Toast.makeText(this,"Debe ingresar una nueva contraaseña!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }

        if(TextUtils.isEmpty(cpass)){
            Toast.makeText(this,"Debe ingresar la confirmación de una nueva conraseña!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else{
            correcto = true;
        }

        if(!newpass.equals(cpass)){
            Toast.makeText(this,"Las contraseñas igresadas no coinciden!",Toast.LENGTH_LONG).show();
            correcto = false;
            return;
        }else if(newpass.equals(cpass)){
            correcto = true;
        }

        if(correcto){

            PasswordBeans p = new PasswordBeans(
                    pass,
                    newpass
            );

            cambiarPassword(url, p);

        }

    }

    private void cambiarPassword (String url, PasswordBeans passwordBeans){

        JSONObject obj = new JSONObject();
        try {
            obj.put("apass",passwordBeans.getApass());
            obj.put("bNewPass",passwordBeans.getBNewPass());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.PUT, url, obj.toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<MessagenID> msgID = new ArrayList<>();
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        msgID.add(new MessagenID(jsonObject.getString("message"),jsonObject.getInt("id")));
                    }

                    int id = msgID.get(0).getId();

                    if(id>0){

                        Toast.makeText(getApplicationContext(),msgID.get(0).getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.setMessage("Efectuando el cambio de contraseña...");
                        progressDialog.show();
                        cambiar(s,Perfil.class);

                    }else if(id==0){

                        Toast.makeText(getApplicationContext(),msgID.get(0).getMessage(),Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(getApplicationContext(),"El servicio no ha podido conceder el cambio de contraseña.",Toast.LENGTH_LONG).show();

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

    private void cambiar(Sesion sesion, Class aClass){

        Intent intent = new Intent(Password.this, aClass);
        intent.putExtra("sesion", sesion);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        if(!hasFocus){

            if(password.length()<8&&password.length()>0) {
                correcto = false;
                Toast.makeText(Password.this, "Debe ingresar una contraseña válida!", Toast.LENGTH_SHORT).show();
            }else if(password.length()==8&&password.length()>0){
                correcto = true;
            }
            if(newpassword.length()<8&&newpassword.length()>0) {
                correcto = false;
                Toast.makeText(Password.this, "Debe ingresar una contraseña válida!", Toast.LENGTH_SHORT).show();
            }else if(newpassword.length()==8&&newpassword.length()>0){
                correcto = true;
            }
            if(cpassword.length()<8&&cpassword.length()>0) {
                correcto = false;
                Toast.makeText(Password.this, "Debe ingresar una contraseña válida!", Toast.LENGTH_SHORT).show();
            }else if(cpassword.length()==8&&cpassword.length()>0){
                correcto = true;
            }

        }

    }
}
