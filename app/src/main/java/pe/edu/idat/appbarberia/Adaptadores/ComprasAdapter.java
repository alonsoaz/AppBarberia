package pe.edu.idat.appbarberia.Adaptadores;

import android.content.Context;
import android.content.Intent;
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
import pe.edu.idat.appbarberia.DetallePasado;
import pe.edu.idat.appbarberia.Modelos.Parcel.ComprasModel;
import pe.edu.idat.appbarberia.R;

public class ComprasAdapter extends RecyclerView.Adapter<ComprasAdapter.ViewHolder> {

    Context context;
    ArrayList<ComprasModel> lista;

    public ComprasAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    Sesion sesion = new Sesion();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.compras_detalle,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        final ComprasModel item = lista.get(position);
        viewHolder.txtCodVenta.setText(item.getbCodVenta());
        viewHolder.txtCodCliente.setText(item.getcCodCliente());
        viewHolder.txtFecha.setText(item.getgFecha());
        viewHolder.txtCliente.setText(item.geteCliente());
        viewHolder.txtEstado.setText(item.getdEstado());
        viewHolder.txtMonto.setText(String.format("%.2f", item.getfTotal()));
        viewHolder.btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intDetallePast = new Intent(context, DetallePasado.class);
                intDetallePast.putExtra("sesion",sesion);
                intDetallePast.putExtra("idVenta",item.getAidVenta()+"");
                context.startActivity(intDetallePast);

            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnDetalle;
        CardView cardView;
        EditText txtCodVenta, txtCodCliente, txtFecha, txtEstado, txtCliente, txtMonto;
        public ViewHolder(View itemView) {
            super(itemView);
            btnDetalle = itemView.findViewById(R.id.com_btnDetail);
            cardView = itemView.findViewById(R.id.cardCompras);
            txtCodVenta = itemView.findViewById(R.id.com_edt_codventa);
            txtCodCliente = itemView.findViewById(R.id.com_edt_codcliente);
            txtFecha = itemView.findViewById(R.id.com_edt_fecha);
            txtEstado = itemView.findViewById(R.id.com_edt_status);
            txtCliente = itemView.findViewById(R.id.com_edt_cliente);
            txtMonto = itemView.findViewById(R.id.com_edt_monto);
        }
    }

    public void agregarElemento(ArrayList<ComprasModel> data, Sesion ses){

        sesion = ses;
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();

    }

}
