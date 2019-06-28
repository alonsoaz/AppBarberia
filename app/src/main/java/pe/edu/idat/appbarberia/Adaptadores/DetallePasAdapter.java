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

import java.util.ArrayList;

import pe.edu.idat.appbarberia.API.Sesion;
import pe.edu.idat.appbarberia.Modelos.Parcel.DetalleVentaModel;
import pe.edu.idat.appbarberia.R;

public class DetallePasAdapter extends RecyclerView.Adapter<DetallePasAdapter.ViewHolder> {

    Context context;
    ArrayList<DetalleVentaModel> lista;
    Sesion sesion = new Sesion();

    public DetallePasAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.detallepas_detalle,parent,false);
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

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        EditText txtCodDetVent, txtCodVent, txtCodClient, txtDate, txtStatus,
                txtCliente, txtTipo, txtProd, txtMarca, txtPrecio, txtStock, txtDsct,
                txtSubt;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardDetallePas);
            txtCodDetVent = itemView.findViewById(R.id.dep_edt_coddetventa);
            txtCodVent = itemView.findViewById(R.id.dep_edt_codventa);
            txtCodClient = itemView.findViewById(R.id.dep_edt_cliente);
            txtDate = itemView.findViewById(R.id.dep_edt_fecha);
            txtStatus = itemView.findViewById(R.id.dep_edt_status);
            txtCliente = itemView.findViewById(R.id.dep_edt_client);
            txtTipo = itemView.findViewById(R.id.dep_edt_tipo);
            txtProd = itemView.findViewById(R.id.dep_edt_prod);
            txtMarca = itemView.findViewById(R.id.dep_edt_marca);
            txtPrecio = itemView.findViewById(R.id.dep_edt_precio);
            txtStock = itemView.findViewById(R.id.dep_edt_qant);
            txtDsct = itemView.findViewById(R.id.dep_edt_desc);
            txtSubt = itemView.findViewById(R.id.dep_edt_subt);
        }
    }

    public void agregarElemento(ArrayList<DetalleVentaModel> data, Sesion ses){

        sesion = ses;
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();

    }

}
