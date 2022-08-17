package com.example.taskmanagement.model;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 16/08/2022
 */
public class CreateTeam {
    private String teamName;

    public CreateTeam(String teamName, String status) {
        this.teamName = teamName;
        this.status = status;
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
