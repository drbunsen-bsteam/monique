package com.example.application;


import com.example.application.data.entity.StopWatchEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopWatchEntryServiceRepository extends JpaRepository<StopWatchEntry, Long> {
}