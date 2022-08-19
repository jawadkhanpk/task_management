package com.example.taskmanagement.model;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 18/08/2022
 */
public class CreateHP {

    String key;
    String name;

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    String companyName;

    public CreateHP(String key, String name, String imageUrl, String designation, String email,String password,String companyName) {
        this.key = key;
        this.name = name;
        this.imageUrl = imageUrl;
        this.designation = designation;
        this.email = email;
        this.password=password;
        this.companyName=companyName;
    }

    public CreateHP() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String imageUrl;
    String designation;
    String email;

    @BindingAdapter("android:imgUrlHP")
    public static void loadImageHP(ImageView imageView, String companyLogo)
    {
        Picasso.get().load(companyLogo).into(imageView);


    }
}
