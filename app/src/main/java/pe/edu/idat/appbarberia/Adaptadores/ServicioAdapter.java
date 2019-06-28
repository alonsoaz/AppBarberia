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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.edu.idat.appbarberia.API.Sesion;
import pe.edu.idat.appbarberia.Disponibilidad;
import pe.edu.idat.appbarberia.Modelos.Parcel.BarberoModel;
import pe.edu.idat.appbarberia.Modelos.Parcel.ServicioModel;
import pe.edu.idat.appbarberia.R;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ServicioModel> lista;

    Sesion sesionAd = new Sesion();

    public ServicioAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.servicio_detalle,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ViewHolder viewHolder = holder;
        final ServicioModel item = lista.get(position);
        viewHolder.nombre.setText(item.getNombreServicio());
        viewHolder.duracion.setText(String.valueOf(item.getDuracion()));
        viewHolder.precio.setText(String.valueOf(item.getPrecio()));
        final String mensaje = String.valueOf(item.getIdServicio());

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sesionAd.setIdServicio(item.getIdServicio());
                Toast.makeText(context,"id del servicio"+mensaje,Toast.LENGTH_LONG).show();
                Intent intReserva = new Intent(context, Disponibilidad.class);
                intReserva.putExtra("sesion",sesionAd);
                context.startActivity(intReserva);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, duracion, precio;
        Button button;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.serv_edt_name);
            duracion = itemView.findViewById(R.id.serv_edt_duracion);
            precio = itemView.findViewById(R.id.serv_edt_precio);
            button = itemView.findViewById(R.id.serv_btnAgenda);
            cardView = itemView.findViewById(R.id.cardServicio);
        }
    }

    public void agregarElemento(ArrayList<ServicioModel> data, Sesion sesion){
        sesionAd = sesion;
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }

}
