package com.app.smartlibhost.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Sach implements Serializable, Parcelable {
    public  String barcode;
    public  int id_sach;
    public int id_theloai;
    public int id_ngonngu;
    public String tensach;
    public String tentacgia;
    public String Mota;
    public String NXB;
    public int soluong;
    public String img_sach;
    public int conlai;



    public Sach(int id_sach, String barcode, int id_theloai, int id_ngonngu, String tensach, String tentacgia, String Mota, String NXB, int soluong, String img_sach, int conlai) {
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

    }


    protected Sach(Parcel in) {
        barcode = in.readString();
        id_sach = in.readInt();
        id_theloai = in.readInt();
        id_ngonngu = in.readInt();
        tensach = in.readString();
        tentacgia = in.readString();
        Mota = in.readString();
        NXB = in.readString();
        soluong = in.readInt();
        img_sach = in.readString();
        conlai = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(barcode);
        dest.writeInt(id_sach);
        dest.writeInt(id_theloai);
        dest.writeInt(id_ngonngu);
        dest.writeString(tensach);
        dest.writeString(tentacgia);
        dest.writeString(Mota);
        dest.writeString(NXB);
        dest.writeInt(soluong);
        dest.writeString(img_sach);
        dest.writeInt(conlai);
    }

    public Sach() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sach> CREATOR = new Creator<Sach>() {
        @Override
        public Sach createFromParcel(Parcel in) {
            return new Sach(in);
        }

        @Override
        public Sach[] newArray(int size) {
            return new Sach[size];
        }
    };

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public int getId_theloai() {
        return id_theloai;
    }

    public void setId_theloai(int id_theloai) {
        this.id_theloai = id_theloai;
    }

    public int getId_ngonngu() {
        return id_ngonngu;
    }

    public void setId_ngonngu(int id_ngonngu) {
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

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getImg_sach() {
        return img_sach;
    }

    public void setImg_sach(String img_sach) {
        this.img_sach = img_sach;
    }

    public int getConlai() {
        return conlai;
    }

    public void setConlai(int conlai) {
        this.conlai = conlai;
    }








}
