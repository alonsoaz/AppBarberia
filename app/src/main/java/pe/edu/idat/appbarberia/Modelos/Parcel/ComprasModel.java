package pe.edu.idat.appbarberia.Modelos.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

public class ComprasModel implements Parcelable {

    private int aidVenta;
    private String bCodVenta;
    private String cCodCliente;
    private String dEstado;
    private String eCliente;
    private Double fTotal;
    private String gFecha;

    public ComprasModel(int aidVenta, String bCodVenta, String cCodCliente, String dEstado, String eCliente, Double fTotal, String gFecha) {
        this.aidVenta = aidVenta;
        this.bCodVenta = bCodVenta;
        this.cCodCliente = cCodCliente;
        this.dEstado = dEstado;
        this.eCliente = eCliente;
        this.fTotal = fTotal;
        this.gFecha = gFecha;
    }

    protected ComprasModel(Parcel in) {
        aidVenta = in.readInt();
        bCodVenta = in.readString();
        cCodCliente = in.readString();
        dEstado = in.readString();
        eCliente = in.readString();
        if (in.readByte() == 0) {
            fTotal = null;
        } else {
            fTotal = in.readDouble();
        }
        gFecha = in.readString();
    }

    public static final Creator<ComprasModel> CREATOR = new Creator<ComprasModel>() {
        @Override
        public ComprasModel createFromParcel(Parcel in) {
            return new ComprasModel(in);
        }

        @Override
        public ComprasModel[] newArray(int size) {
            return new ComprasModel[size];
        }
    };

    public int getAidVenta() {
        return aidVenta;
    }

    public void setAidVenta(int aidVenta) {
        this.aidVenta = aidVenta;
    }

    public String getbCodVenta() {
        return bCodVenta;
    }

    public void setbCodVenta(String bCodVenta) {
        this.bCodVenta = bCodVenta;
    }

    public String getcCodCliente() {
        return cCodCliente;
    }

    public void setcCodCliente(String cCodCliente) {
        this.cCodCliente = cCodCliente;
    }

    public String getdEstado() {
        return dEstado;
    }

    public void setdEstado(String dEstado) {
        this.dEstado = dEstado;
    }

    public String geteCliente() {
        return eCliente;
    }

    public void seteCliente(String eCliente) {
        this.eCliente = eCliente;
    }

    public Double getfTotal() {
        return fTotal;
    }

    public void setfTotal(Double fTotal) {
        this.fTotal = fTotal;
    }

    public String getgFecha() {
        return gFecha;
    }

    public void setgFecha(String gFecha) {
        this.gFecha = gFecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(aidVenta);
        dest.writeString(bCodVenta);
        dest.writeString(cCodCliente);
        dest.writeString(dEstado);
        dest.writeString(eCliente);
        if (fTotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(fTotal);
        }
        dest.writeString(gFecha);
    }
}
