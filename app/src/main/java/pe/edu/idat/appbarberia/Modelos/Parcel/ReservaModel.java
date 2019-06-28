package pe.edu.idat.appbarberia.Modelos.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

public class ReservaModel implements Parcelable{

    private int idReserva;
    private String cliente, servicio, barbero, inicio, fin, estado;

    public ReservaModel(int idReserva, String cliente, String servicio, String barbero, String inicio, String fin, String estado) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.servicio = servicio;
        this.barbero = barbero;
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }

    protected ReservaModel(Parcel in) {
        idReserva = in.readInt();
        cliente = in.readString();
        servicio = in.readString();
        barbero = in.readString();
        inicio = in.readString();
        fin = in.readString();
        estado = in.readString();
    }

    public static final Creator<ReservaModel> CREATOR = new Creator<ReservaModel>() {
        @Override
        public ReservaModel createFromParcel(Parcel in) {
            return new ReservaModel(in);
        }

        @Override
        public ReservaModel[] newArray(int size) {
            return new ReservaModel[size];
        }
    };

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getBarbero() {
        return barbero;
    }

    public void setBarbero(String barbero) {
        this.barbero = barbero;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idReserva);
        dest.writeString(cliente);
        dest.writeString(servicio);
        dest.writeString(barbero);
        dest.writeString(inicio);
        dest.writeString(fin);
        dest.writeString(estado);
    }
}
