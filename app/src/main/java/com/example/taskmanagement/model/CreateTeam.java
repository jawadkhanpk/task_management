package com.example.taskmanagement.model;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 16/08/2022
 */
public class CreateTeam {
    private String teamName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String designation,email,name;

    public CreateTeam(String teamName, String status,String designation,String name,String email) {
        this.teamName = teamName;
        this.status = status;
        this.designation=designation;
        this.name=name;
        this.email=email;
    }

    public CreateTeam() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
}
