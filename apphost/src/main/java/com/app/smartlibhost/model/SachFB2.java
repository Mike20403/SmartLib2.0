package com.app.smartlibhost.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class SachFB2 implements Serializable, Parcelable {
    protected String id_sach;
    protected String barcode;
    protected String id_theloai;
    protected String id_ngonngu;
    protected String tensach;
    protected String tentacgia;
    protected String Mota;
    protected String NXB;
    protected String soluong;
    protected String img_sach;
    protected String conlai;
    protected String date_added;

    protected SachFB2(Parcel in) {
        id_sach = in.readString();
        barcode = in.readString();
        id_theloai = in.readString();
        id_ngonngu = in.readString();
        tensach = in.readString();
        tentacgia = in.readString();
        Mota = in.readString();
        NXB = in.readString();
        soluong = in.readString();
        img_sach = in.readString();
        conlai = in.readString();
        date_added = in.readString();
    }

    public static final Creator<SachFB2> CREATOR = new Creator<SachFB2>() {
        @Override
        public SachFB2 createFromParcel(Parcel in) {
            return new SachFB2(in);
        }

        @Override
        public SachFB2[] newArray(int size) {
            return new SachFB2[size];
        }
    };

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public SachFB2() {
    }

    public String getId_sach() {
        return id_sach;
    }

    public void setId_sach(String id_sach) {
        this.id_sach = id_sach;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getId_theloai() {
        return id_theloai;
    }

    public void setId_theloai(String id_theloai) {
        this.id_theloai = id_theloai;
    }

    public String getId_ngonngu() {
        return id_ngonngu;
    }

    public void setId_ngonngu(String id_ngonngu) {
        this.id_ngonngu = id_ngonngu;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTentacgia() {
        return tentacgia;
    }

    public void setTentacgia(String tentacgia) {
        this.tentacgia = tentacgia;
    }


    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getImg_sach() {
        return img_sach;
    }

    public void setImg_sach(String img_sach) {
        this.img_sach = img_sach;
    }

    public String getConlai() {
        return conlai;
    }

    public void setConlai(String conlai) {
        this.conlai = conlai;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public SachFB2(String id_sach, String barcode, String id_theloai, String id_ngonngu, String tensach, String tentacgia, String Mota, String NXB, String soluong, String img_sach, String conlai, String date_added) {
        this.id_sach = id_sach;
        this.barcode = barcode;
        this.id_theloai = id_theloai;
        this.id_ngonngu = id_ngonngu;
        this.tensach = tensach;
        this.tentacgia = tentacgia;
        this.Mota = Mota;
        this.NXB = NXB;
        this.soluong = soluong;
        this.img_sach = img_sach;
        this.conlai = conlai;
        this.date_added = date_added;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(barcode);
        dest.writeString(id_sach);
        dest.writeString(id_theloai);
        dest.writeString(id_ngonngu);
        dest.writeString(tensach);
        dest.writeString(tentacgia);
        dest.writeString(Mota);
        dest.writeString(NXB);
        dest.writeString(soluong);
        dest.writeString(img_sach);
        dest.writeString(conlai);

    }
}
