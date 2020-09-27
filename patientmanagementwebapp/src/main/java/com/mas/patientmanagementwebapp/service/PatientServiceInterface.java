package com.mas.patientmanagementwebapp.service;

import com.mas.patientmanagementwebapp.model.Patient;

public interface PatientServiceInterface {
    boolean patientExists(Patient patient);
}
