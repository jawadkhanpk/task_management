package com.example.taskmanagement.model;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 26/08/2022
 */
public class RegisterEmployModel {

    public RegisterEmployModel(String name, String role, String email, String password, String profile,String companyName,String key,String employCreatedBy,String designation) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.companyName=companyName;
        this.key=key;
        this.employCreatedBy=employCreatedBy;
        this.designation=designation;
    }

    String name;
    String role;
    String email;
    String password;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    String designation;

    public String getEmployCreatedBy() {
        return employCreatedBy;
    }

    public void setEmployCreatedBy(String employCreatedBy) {
        this.employCreatedBy = employCreatedBy;
    }

    String employCreatedBy;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    String companyName;

    public RegisterEmployModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    String profile;

    @BindingAdapter("android:imgUrlHP")
    public static void loadImageHP(ImageView imageView, Uri profile)
    {
        Picasso.get().load(profile).into(imageView);


    }
}
