package pe.edu.idat.appbarberia.API;

import android.os.Parcel;
import android.os.Parcelable;

public class Sesion implements Parcelable {

    private int idCliente;
    private int idBarbero;
    private int idServicio;
    private int idReserva;
    private String url;

    public Sesion() {
    }

    public Sesion(int idCliente, int idBarbero, int idServicio, int idReserva, String url) {
        this.idCliente = idCliente;
        this.idBarbero = idBarbero;
        this.idServicio = idServicio;
        this.idReserva = idReserva;
        this.url = url;
    }

    protected Sesion(Parcel in) {
        idCliente = in.readInt();
        idBarbero = in.readInt();
        idServicio = in.readInt();
        idReserva = in.readInt();
        url = in.readString();
    }

    public static final Creator<Sesion> CREATOR = new Creator<Sesion>() {
        @Override
        public Sesion createFromParcel(Parcel in) {
            return new Sesion(in);
        }

        @Override
        public Sesion[] newArray(int size) {
            return new Sesion[size];
        }
    };

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdBarbero() {
        return idBarbero;
    }

    public void setIdBarbero(int idBarbero) {
        this.idBarbero = idBarbero;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idCliente);
        parcel.writeInt(idBarbero);
        parcel.writeInt(idServicio);
        parcel.writeInt(idReserva);
        parcel.writeString(url);
    }

    @Override
    public String toString() {
        return "Sesion{" +
                "idCliente=" + idCliente +
                ", idBarbero=" + idBarbero +
                ", idServicio=" + idServicio +
                ", idReserva=" + idReserva +
                ", url='" + url + '\'' +
                '}';
    }
}
