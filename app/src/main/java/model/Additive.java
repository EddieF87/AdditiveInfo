package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eddie on 3/25/2018.
 */

public class Additive implements Parcelable {

    private long _id;
    private String name;
    private String codeUSA;
    private String codeEU;
    private String codeChina;
    private String description;
    private int level;
    private boolean favorite;

    public Additive(){
    }

    public Additive(long _id, String name, String codeUSA, String codeEU, String codeChina, String description, int level, boolean favorite) {
        this._id = _id;
        this.name = name;
        this.codeUSA = codeUSA;
        this.codeEU = codeEU;
        this.codeChina = codeChina;
        this.description = description;
        this.level = level;
        this.favorite = favorite;
    }

    public long get_id() {
        return _id;
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

    public boolean getFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }


    protected Additive(Parcel in) {
        _id = in.readLong();
        name = in.readString();
        codeUSA = in.readString();
        codeEU = in.readString();
        codeChina = in.readString();
        description = in.readString();
        level = in.readInt();
        favorite = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(name);
        dest.writeString(codeUSA);
        dest.writeString(codeEU);
        dest.writeString(codeChina);
        dest.writeString(description);
        dest.writeInt(level);
        dest.writeByte((byte) (favorite ? 0x01 : 0x00));
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