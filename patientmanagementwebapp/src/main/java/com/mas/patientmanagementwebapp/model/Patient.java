package com.mas.patientmanagementwebapp.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @NotNull
    @Size(min=1)
    private String patientID;
    @NotNull
    @Size(min=9, max=9)
    private String SSN;
    @NotNull
    @Size(min=3)
    private String First;
    @NotNull
    @Size(min=3)
    private String Last;
    @NotNull
    @Size(min=3)
    private String Ethnicity;
    @NotNull
    private char Gender;
    @NotNull
    @Size(min=5, max=5)
    private String Zip;

    public Patient(String patientID, String SSN, String first, String last, String ethnicity, char gender, String zip) {
        this.patientID = patientID;
        this.SSN = SSN;
        First = first;
        Last = last;
        Ethnicity = ethnicity;
        Gender = gender;
        Zip = zip;
    }

    public Patient(){
        super();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getFirst() {
        return First;
    }

    public void setFirst(String first) {
        First = first;
    }

    public String getLast() {
        return Last;
    }

    public void setLast(String last) {
        Last = last;
    }

    public String getEthnicity() {
        return Ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        Ethnicity = ethnicity;
    }

    public char getGender() { return Gender; }

    public void setGender(char gender) {
        Gender = gender;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }
}
