package pe.edu.idat.appbarberia.Modelos.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

public class ServicioModel implements Parcelable{

    private int idServicio;
    private String nombreServicio;
    private int duracion;
    private double precio;

    public ServicioModel(int idServicio, String nombreServicio, int duracion, double precio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.duracion = duracion;
        this.precio = precio;
    }

    protected ServicioModel(Parcel in) {
        idServicio = in.readInt();
        nombreServicio = in.readString();
        duracion = in.readInt();
        precio = in.readDouble();
    }

    public static final Creator<ServicioModel> CREATOR = new Creator<ServicioModel>() {
        @Override
        public ServicioModel createFromParcel(Parcel in) {
            return new ServicioModel(in);
        }

        @Override
        public ServicioModel[] newArray(int size) {
            return new ServicioModel[size];
        }
    };

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idServicio);
        dest.writeString(nombreServicio);
        dest.writeInt(duracion);
        dest.writeDouble(precio);
    }
}
