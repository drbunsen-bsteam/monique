package com.example.application.views.dashboard;

import java.time.LocalDate;

/**
 * Simple DTO class for the inbox list to demonstrate complex object data
 */
public class HealthGridItem {

    private LocalDate date;
    private String city;
    private String tagesZeit;
    private String status;
    private String theme;

    public HealthGridItem() {

    }

    public HealthGridItem(LocalDate date, String city, String tagesZeit, String status, String theme) {
        this.date = date;
        this.city = city;
        this.tagesZeit = tagesZeit;
        this.status = status;
        this.theme = theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTagesZeit() {
        return tagesZeit;
    }

    public void setTagesZeit(String tagesZeit) {
        this.tagesZeit = tagesZeit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
