package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class ServiceEntry extends AbstractEntity {
    @NotNull
    @NotEmpty
    private String dlNbr = "";

    @NotNull
    @NotEmpty
    private String workType = "";
    /*
    @Enumerated(EnumType.STRING)
    @NotNull
    private Contact.Status status;
*/
    
/*
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company descritpion;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Contact> employees = new LinkedList<>();
    
*/    

    @NotNull
    @NotEmpty
    private String descritpion;
    
    //private String name;
    private LocalDate date;
    private Long duration;

    public ServiceEntry() {
    }
    public String getDlNbr() {
        return dlNbr;
    }

    public void setDlNbr(String dlNbr) {
        this.dlNbr = dlNbr;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
