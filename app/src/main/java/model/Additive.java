package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eddie on 3/25/2018.
 */

public class Additive implements Parcelable {

    private String name;
    private String codeUSA;
    private String codeEU;
    private String codeChina;
    private String description;

    public Additive(){
    }

    public Additive(String name, String codeUSA, String codeEU, String codeChina, String description) {
        this.name = name;
        this.codeUSA = codeUSA;
        this.codeEU = codeEU;
        this.codeChina = codeChina;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeUSA() {
        return codeUSA;
    }

    public void setCodeUSA(String codeUSA) {
        this.codeUSA = codeUSA;
    }

    public String getCodeEU() {
        return codeEU;
    }

    public void setCodeEU(String codeEU) {
        this.codeEU = codeEU;
    }

    public String getCodeChina() {
        return codeChina;
    }

    public void setCodeChina(String codeChina) {
        this.codeChina = codeChina;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected Additive(Parcel in) {
        name = in.readString();
        codeUSA = in.readString();
        codeEU = in.readString();
        codeChina = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(codeUSA);
        dest.writeString(codeEU);
        dest.writeString(codeChina);
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Additive> CREATOR = new Parcelable.Creator<Additive>() {
        @Override
        public Additive createFromParcel(Parcel in) {
            return new Additive(in);
        }

        @Override
        public Additive[] newArray(int size) {
            return new Additive[size];
        }
    };
}