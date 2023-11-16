package com.app.smartlibhost.model;

public class Users {
    String id_fb,name_user,email_user,fb_img;



    public Users(String id_fb, String name_user, String email_user , String fb_img) {
        this.id_fb = id_fb;
        this.name_user = name_user;
        this.email_user = email_user;

        this.fb_img = fb_img;
    }

    public String getFb_img() {
        return fb_img;
    }

    public void setFb_img(String fb_img) {
        this.fb_img = fb_img;
    }

    public String getId_fb() {
        return id_fb;
    }

    public void setId_fb(String id_fb) {
        this.id_fb = id_fb;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }


}
