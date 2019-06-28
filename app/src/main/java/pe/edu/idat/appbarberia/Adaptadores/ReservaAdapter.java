package pe.edu.idat.appbarberia.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pe.edu.idat.appbarberia.Modelos.Parcel.ReservaModel;
import pe.edu.idat.appbarberia.R;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ReservaModel> lista;

    Sesion sesionAd = new Sesion();

    public ReservaAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).
                inflate(R.layout.reserva_detalle,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ViewHolder viewHolder = holder;
        final ReservaModel item = lista.get(position);
        viewHolder.txtcliente.setText(item.getCliente());
        viewHolder.txtservicio.setText(item.getServicio());
        viewHolder.txtbarbero.setText(item.getBarbero());
        viewHolder.txtinicio.setText(item.getInicio());
        viewHolder.txtfin.setText(item.getFin());
        viewHolder.txtestado.setText(item.getEstado());
        final int idReserva = (item.getIdReserva());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"id de la reserva"+idReserva,Toast.LENGTH_SHORT).show();
            }
        });

        boolean habilitar = item.getEstado().contains("POR");

        viewHolder.btnPay.setEnabled(habilitar);
        viewHolder.btnDel.setEnabled(habilitar);

        viewHolder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eliminarYpagar(link(2,idReserva), viewHolder);
                viewHolder.btnPay.setEnabled(false);
                viewHolder.btnDel.setEnabled(false);

            }
        });

        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eliminarYpagar(link(3,idReserva), viewHolder);
                viewHolder.btnPay.setEnabled(false);
                viewHolder.btnDel.setEnabled(false);

            }
        });

    }

    private String link(int choose,int id){

        sesionAd.setIdReserva(id);
        Link link = new Link(sesionAd);
        String url = "";

        if(choose==2){
            url =  link.payReserva;
        }else if(choose==3){
            url = link.delReserva;
        }
        return url;

    }

    private void eliminarYpagar(String url, final ViewHolder viewHolder){

        final RequestQueue mQueue = Volley.newRequestQueue(context);
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

                        viewHolder.txtmensaje.setText(mensaje);

                    }else if(response.length()==0){

                        Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();

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
        CardView cardView;
        EditText txtcliente, txtservicio, txtbarbero, txtinicio, txtfin, txtestado, txtmensaje;
        Button btnPay, btnDel;
        public ViewHolder(View itemView) {

            super(itemView);
            cardView = itemView.findViewById(R.id.cardReserva);
            txtcliente = itemView.findViewById(R.id.res_edt_cliente);
            txtservicio = itemView.findViewById(R.id.res_edt_servicio);
            txtbarbero = itemView.findViewById(R.id.res_edt_barbero);
            txtinicio = itemView.findViewById(R.id.res_edt_inicio);
            txtfin = itemView.findViewById(R.id.res_edt_fin);
            txtestado = itemView.findViewById(R.id.res_edt_status);
            txtmensaje = itemView.findViewById(R.id.res_edt_msj);
            btnPay = itemView.findViewById(R.id.res_btnPay);
            btnDel = itemView.findViewById(R.id.res_btnErase);

        }
    }

    public void agregarElemento(ArrayList<ReservaModel> data, Sesion sesion){

        sesionAd = sesion;
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();

    }

}
