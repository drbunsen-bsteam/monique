package com.example.application.data.service;

import com.example.application.ServiceEntryRepository;
import com.example.application.data.entity.ServiceEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntryService {

    private ServiceEntryRepository serviceEntryRepository;

    public ServiceEntryService(ServiceEntryRepository companyRepository) {
        this.serviceEntryRepository = companyRepository;
    }

    public List<ServiceEntry> findAll() {
        return serviceEntryRepository.findAll();
    }

}