package pe.edu.idat.appbarberia.Modelos.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

public class DisponibilidadBeans implements Parcelable{

    String bYear, cMonth, dDay, eHour, fMinut;

    public DisponibilidadBeans(String bYear, String cMonth, String dDay, String eHour, String fMinut) {
        this.bYear = bYear;
        this.cMonth = cMonth;
        this.dDay = dDay;
        this.eHour = eHour;
        this.fMinut = fMinut;
    }

    protected DisponibilidadBeans(Parcel in) {
        bYear = in.readString();
        cMonth = in.readString();
        dDay = in.readString();
        eHour = in.readString();
        fMinut = in.readString();
    }

    public static final Creator<DisponibilidadBeans> CREATOR = new Creator<DisponibilidadBeans>() {
        @Override
        public DisponibilidadBeans createFromParcel(Parcel in) {
            return new DisponibilidadBeans(in);
        }

        @Override
        public DisponibilidadBeans[] newArray(int size) {
            return new DisponibilidadBeans[size];
        }
    };

    public String getbYear() {
        return bYear;
    }

    public void setbYear(String bYear) {
        this.bYear = bYear;
    }

    public String getcMonth() {
        return cMonth;
    }

    public void setcMonth(String cMonth) {
        this.cMonth = cMonth;
    }

    public String getdDay() {
        return dDay;
    }

    public void setdDay(String dDay) {
        this.dDay = dDay;
    }

    public String geteHour() {
        return eHour;
    }

    public void seteHour(String eHour) {
        this.eHour = eHour;
    }

    public String getfMinut() {
        return fMinut;
    }

    public void setfMinut(String fMinut) {
        this.fMinut = fMinut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bYear);
        dest.writeString(cMonth);
        dest.writeString(dDay);
        dest.writeString(eHour);
        dest.writeString(fMinut);
    }
}
