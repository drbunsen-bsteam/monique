package com.example.application.views.stopwatch;

public class DlEntry {
    private String Name;
    private String Descritpion;

    public DlEntry() {
    }

    public DlEntry(String Name, String YearOfBirth) {
        this.Name = Name;
        this.Descritpion = YearOfBirth;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescritpion() {
        return Descritpion;
    }

    public void setDescritpion(String Descritpion) {
        this.Descritpion = Descritpion;
    }

    @Override
    public String toString() {
        return "DlEntry{" + "Name=" + Name + ", Descritpion=" + Descritpion + '}';
    }
    
}