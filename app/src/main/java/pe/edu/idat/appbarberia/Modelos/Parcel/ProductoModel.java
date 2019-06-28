package pe.edu.idat.appbarberia.Modelos.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductoModel implements Parcelable {

    private int idProducto;
    private String nombreProducto;
    private double precio;
    private String desProducto;

    public ProductoModel(int idProducto, String nombreProducto, double precio, String desProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.desProducto = desProducto;
    }

    protected ProductoModel(Parcel in) {
        idProducto = in.readInt();
        nombreProducto = in.readString();
        precio = in.readDouble();
        desProducto = in.readString();
    }

    public static final Creator<ProductoModel> CREATOR = new Creator<ProductoModel>() {
        @Override
        public ProductoModel createFromParcel(Parcel in) {
            return new ProductoModel(in);
        }

        @Override
        public ProductoModel[] newArray(int size) {
            return new ProductoModel[size];
        }
    };

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDesProducto() {
        return desProducto;
    }

    public void setDesProducto(String desProducto) {
        this.desProducto = desProducto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idProducto);
        dest.writeString(nombreProducto);
        dest.writeDouble(precio);
        dest.writeString(desProducto);
    }
}
