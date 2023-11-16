package com.app.smartlibhost.model;

public class Menu_main {



    public int id;
    public String ten_menu,img_menu;

    public Menu_main(int id, String ten_menu, String img_menu) {
        this.id = id;
        this.ten_menu = ten_menu;
        this.img_menu = img_menu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_menu() {
        return ten_menu;
    }

    public void setTen_menu(String ten_menu) {
        this.ten_menu = ten_menu;
    }

    public String getImg_menu() {
        return img_menu;
    }

    public void setImg_menu(String img_menu) {
        this.img_menu = img_menu;
    }


}
