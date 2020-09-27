package com.mas.patientmanagementwebapp.dao;

import com.mas.patientmanagementwebapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByPatientID(String patientID);
}
