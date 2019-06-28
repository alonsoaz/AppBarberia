package pe.edu.idat.appbarberia.Modelos.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

public class  BarberoModel implements Parcelable{

    private int idBarbero;
    private String nombreBarbero;

    public BarberoModel(int idBarbero, String nombreBarbero) {
        this.idBarbero = idBarbero;
        this.nombreBarbero = nombreBarbero;
    }

    protected BarberoModel(Parcel in) {
        idBarbero = in.readInt();
        nombreBarbero = in.readString();
    }

    public static final Creator<BarberoModel> CREATOR = new Creator<BarberoModel>() {
        @Override
        public BarberoModel createFromParcel(Parcel in) {
            return new BarberoModel(in);
        }

        @Override
        public BarberoModel[] newArray(int size) {
            return new BarberoModel[size];
        }
    };

    public int getIdBarbero() {
        return idBarbero;
    }

    public void setIdBarbero(int idBarbero) {
        this.idBarbero = idBarbero;
    }

    public String getNombreBarbero() {
        return nombreBarbero;
    }

    public void setNombreBarbero(String nombreBarbero) {
        this.nombreBarbero = nombreBarbero;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idBarbero);
        dest.writeString(nombreBarbero);
    }
}
