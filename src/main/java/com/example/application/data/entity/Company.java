package com.example.application.data.entity;


import com.example.application.data.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Company extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Contact> employees = new LinkedList<>();

    public Company() {
    }

    public Company(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getEmployees() {
        return employees;
    }
}