package pe.edu.idat.appbarberia.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import pe.edu.idat.appbarberia.Modelos.JSON.MessagenID;
import pe.edu.idat.appbarberia.Modelos.Parcel.ProductoModel;
import pe.edu.idat.appbarberia.R;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductoModel> lista;
    private Link link;
    private boolean correcto = false;

    public ProductoAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.producto_detalle,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ViewHolder viewHolder = holder;
        final ProductoModel item = lista.get(position);
        viewHolder.nombre.setText(item.getNombreProducto());
        viewHolder.precio.setText(String.valueOf(item.getPrecio()));
        viewHolder.cantidad.setText(0+"");
        viewHolder.cantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    viewHolder.cantidad.setText("");
                }
                if(!hasFocus){
                    if(Integer.parseInt(viewHolder.cantidad.getText().toString().trim())==0){
                        correcto = false;
                        Toast.makeText(context,"se debe ingresar una cantidad mayor a cero!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(viewHolder.cantidad.getText().toString().trim())>0){
                        correcto = true;
                    }
                }
            }
        });
        final int idProducto = item.getIdProducto();

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"id del producto"+idProducto,Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String q = viewHolder.cantidad.getText().toString().trim();

                if(TextUtils.isEmpty(q)||TextUtils.equals(q,"0")){
                    Toast.makeText(context,"se debe ingresar una cantidad mayor a cero!",Toast.LENGTH_LONG).show();
                    correcto = false;
                    return;
                }else{
                    correcto = true;
                }

                if(correcto) {

                    int cantidad = Integer.parseInt(viewHolder.cantidad.getText().toString().trim());
                    String url = link.regDetVenta + idProducto;
                    comprar(url, cantidad + "", viewHolder);
                }
            }
        });

    }

    private void comprar(String url, String cantidad, final ViewHolder viewHolder){

        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonRequest<JSONArray> jsonArrayJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.POST,url
                , cantidad
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<MessagenID> msg = new ArrayList<>();
                    int id = 0;
                    String mensaje = "";
                    for(int i=0; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        msg.add(new MessagenID(jsonObject.getString("message"),
                                jsonObject.getInt("id")
                        ));
                        id = jsonObject.getInt("id");
                        mensaje = jsonObject.getString("message");
                    }

                    if(id>0){

                        viewHolder.txtmensaje.setText(mensaje);

                    }else if(id==0){

                        viewHolder.txtmensaje.setText(mensaje);

                    }else{

                        Toast.makeText(context,"El servicio no ha podido conceder la consulta.",Toast.LENGTH_LONG).show();

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

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, precio, cantidad, txtmensaje;
        Button buton;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.prod_edt_nombre);
            cardView = itemView.findViewById(R.id.cardProducto);
            precio = itemView.findViewById(R.id.prod_edt_price);
            cantidad = itemView.findViewById(R.id.prod_edt_qant);
            txtmensaje = itemView.findViewById(R.id.prod_edt_msj);
            buton = itemView.findViewById(R.id.prod_btnBuy);

        }
    }

    public void agregarElemento(ArrayList<ProductoModel> data, Sesion sesion){

        link = new Link(sesion);
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();

    }

}
