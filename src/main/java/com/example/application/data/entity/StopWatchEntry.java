package com.example.application.data.entity;
//package com.vaadin.tutorial.crm.backend.entity;

import com.example.application.data.AbstractEntity;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class StopWatchEntry extends AbstractEntity implements Cloneable {

    
    public enum DlName {
        //ImportedLead, NotContacted, Contacted, Customer, ClosedLost
        SRMAcceptance, Monica, WSG, QPortal, DiesUndDas
    }

    public enum Worktype {
        //ImportedLead, NotContacted, Contacted, Customer, ClosedLost
        WREAL, WMEET,WSUPP,WOTHER
    }

    public StopWatchEntry() {
    }

    public StopWatchEntry(LocalDate dateOfTask, String message,DlName dlName, Worktype workType, String sumOfMinutes) {
        this.dateOfTask = dateOfTask;
        this.sumOfMinutes = sumOfMinutes;
        this.dlName = dlName;
        this.workType = workType;
        this.message =message;
    }
    
    @NotNull
    private LocalDate dateOfTask;

    @NotNull
    @NotEmpty
    private String message= "";

    @Enumerated(EnumType.STRING)
    @NotNull
    private StopWatchEntry.DlName dlName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StopWatchEntry.Worktype workType;

    
    @NotNull
    private String sumOfMinutes;
    
    
    

    public LocalDate getDateOfTask() {
        return dateOfTask;
    }

    public void setDateOfTask(LocalDate dateOfTask) {
        this.dateOfTask = dateOfTask;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DlName getDlName() {
        return dlName;
    }

    public void setDlName(DlName dlName) {
        this.dlName = dlName;
    }

    public Worktype getWorkType() {
        return workType;
    }

    public void setWorkType(Worktype workType) {
        this.workType = workType;
    }


    public String getSumOfMinutes() {
        return sumOfMinutes;
    }

    public void setSumOfMinutes(String sumOfMinutes) {
        this.sumOfMinutes = sumOfMinutes;
    }

    @Override
    public String toString() {
        return "StopWatchEntry{" + "dateOfTask=" + dateOfTask + ", message=" + message + ", sumOfMinutes=" + sumOfMinutes + ", dlName=" + dlName + ", workType=" + workType + '}';
    }
    
    

}