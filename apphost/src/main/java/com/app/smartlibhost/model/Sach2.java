package com.app.smartlibhost.model;


import java.io.Serializable;

public class Sach2 implements Serializable {
    public String id_sach;
    public String barcode;
    public String id_theloai;
    public String id_ngonngu;
    public String tensach;
    public String tentacgia;
    public String Mota;
    public String NXB;

    public Sach2(String id_sach, String barcode, String id_theloai, String id_ngonngu, String tensach, String tentacgia, String mota, String NXB, String soluong, String img_sach, String conlai, boolean checkedstate) {
        this.id_sach = id_sach;
        this.barcode = barcode;
        this.id_theloai = id_theloai;
        this.id_ngonngu = id_ngonngu;
        this.tensach = tensach;
        this.tentacgia = tentacgia;
        Mota = mota;
        this.NXB = NXB;
        this.soluong = soluong;
        this.img_sach = img_sach;
        this.conlai = conlai;
        this.checkedstate = checkedstate;
    }

    public String soluong;
    public String img_sach;
    public String conlai;
    public boolean checkedstate;

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

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
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

    public boolean getCheckedstate() {
        return checkedstate;
    }

    public void setCheckedstate(boolean checkedstate) {
        this.checkedstate = checkedstate;
    }

    public Sach2() {
    }

}
