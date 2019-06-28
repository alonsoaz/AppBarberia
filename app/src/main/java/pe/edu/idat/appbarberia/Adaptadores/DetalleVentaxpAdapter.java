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
import pe.edu.idat.appbarberia.Modelos.Parcel.DetalleVentaModel;
import pe.edu.idat.appbarberia.R;

public class DetalleVentaxpAdapter extends RecyclerView.Adapter<DetalleVentaxpAdapter.ViewHolder> {

    Context context;
    ArrayList<DetalleVentaModel> lista;
    Sesion sesion = new Sesion();

    public DetalleVentaxpAdapter(Context context) {
        lista = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.detallexp_detalle,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ViewHolder viewHolder = holder;
        DetalleVentaModel item = lista.get(position);
        final int idDetVenta = item.getAidDetalleventa();
        viewHolder.txtCodDetVent.setText(item.geteCodDet());
        viewHolder.txtCodVent.setText(item.getcCodVenta());
        viewHolder.txtCodClient.setText(item.getdCodClient());
        viewHolder.txtDate.setText(item.getFecha());
        viewHolder.txtStatus.setText(item.getgEstado());
        viewHolder.txtCliente.setText(item.gethCLiente());
        viewHolder.txtTipo.setText(item.getiTipo());
        viewHolder.txtProd.setText(item.getjProducto());
        viewHolder.txtMarca.setText(item.getkMarca());
        viewHolder.txtPrecio.setText(String.format("%.2f", item.getlPrecio())+"");
        viewHolder.txtStock.setText(item.getmCantidad()+"");
        viewHolder.txtDsct.setText(String.format("%.2f", 1-item.getnDescuento())+"");
        viewHolder.txtSubt.setText(String.format("%.2f", item.getoSubtotal())+"");

        boolean habilitar = item.getgEstado().contains("POR");

        viewHolder.btnPay.setEnabled(habilitar);
        viewHolder.btnDelete.setEnabled(habilitar);

        Link link = new Link(sesion);
        final String urlPayDetVent = link.payDetVenta;
        final String urlDelDetVenta = link.delDV;

        viewHolder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.btnPay.setEnabled(false);
                viewHolder.btnDelete.setEnabled(false);
                eliminarYpagar(urlPayDetVent+idDetVenta, viewHolder);

            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.btnPay.setEnabled(false);
                viewHolder.btnDelete.setEnabled(false);
                eliminarYpagar(urlDelDetVenta+idDetVenta, viewHolder);

            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        Button btnPay, btnDelete;
        EditText txtCodDetVent, txtCodVent, txtCodClient, txtDate, txtStatus,
            txtCliente, txtTipo, txtProd, txtMarca, txtPrecio, txtStock, txtDsct,
            txtSubt, txtMsj;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardDetallexp);
            btnPay = itemView.findViewById(R.id.det_btnPay);
            btnDelete = itemView.findViewById(R.id.det_btnErase);
            txtCodDetVent = itemView.findViewById(R.id.det_edt_coddetventa);
            txtCodVent = itemView.findViewById(R.id.det_edt_codventa);
            txtCodClient = itemView.findViewById(R.id.det_edt_cliente);
            txtDate = itemView.findViewById(R.id.det_edt_fecha);
            txtStatus = itemView.findViewById(R.id.det_edt_status);
            txtCliente = itemView.findViewById(R.id.det_edt_client);
            txtTipo = itemView.findViewById(R.id.det_edt_tipo);
            txtProd = itemView.findViewById(R.id.det_edt_prod);
            txtMarca = itemView.findViewById(R.id.det_edt_marca);
            txtPrecio = itemView.findViewById(R.id.det_edt_precio);
            txtStock = itemView.findViewById(R.id.det_edt_qant);
            txtDsct = itemView.findViewById(R.id.det_edt_desc);
            txtSubt = itemView.findViewById(R.id.det_edt_subt);
            txtMsj = itemView.findViewById(R.id.det_edt_msj);

        }
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

                    if(response.length()>0){

                        ArrayList<MensajesBeans> msg = new ArrayList<>();
                        for(int i=0; i<response.length();i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            msg.add(new MensajesBeans(jsonObject.getString("mensaje")
                            ));
                        }

                        String mensaje = msg.get(0).getMensaje();

                        viewHolder.txtMsj.setText(mensaje);

                    }else if(response.length()==0){

                        Toast.makeText(context,"El servicio no ha podido conceder la consulta.",Toast.LENGTH_LONG).show();

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

    public void agregarElemento(ArrayList<DetalleVentaModel> data, Sesion ses){

        sesion = ses;
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();

    }

}
