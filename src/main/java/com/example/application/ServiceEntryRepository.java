package com.example.application;


import com.example.application.data.entity.ServiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {
}