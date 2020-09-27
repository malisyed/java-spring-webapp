package com.mas.patientmanagementwebapp.service;

import com.mas.patientmanagementwebapp.dao.PatientRepository;
import com.mas.patientmanagementwebapp.model.Patient;
import com.mas.patientmanagementwebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements PatientServiceInterface{
    @Autowired
    private PatientRepository repo;

    @Override
    public boolean patientExists(Patient patient) {
        if (repo.findByPatientID(patient.getPatientID()) != null){
            return true;
        }
        return false;
    }

    public List<Patient> listAll(){
        return repo.findAll();
    }

    public void save(Patient patient){
        repo.save(patient);
    }

    public Patient get(int id){
        return repo.findById(id).get();
    }

    public void delete(int id){
        repo.deleteById(id);
    }
}
