package com.mas.patientmanagementwebapp.service;

import com.mas.patientmanagementwebapp.model.Patient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

@Service
public class DbToJsonService {
    @Autowired
    private PatientService patientService;
    private String path = "dbJSON.json";

    public String getPath(){
        return path;
    }

    private JSONObject patientToJson(Patient patient){
        JSONObject jsonP = new JSONObject();
        jsonP.put("ID", patient.getPatientID());
        jsonP.put("SSN", patient.getSSN());
        jsonP.put("First", patient.getFirst());
        jsonP.put("Last", patient.getLast());
        jsonP.put("Ethnicity", patient.getEthnicity());
        jsonP.put("Gender", String.valueOf(patient.getGender()));
        jsonP.put("Zip", patient.getZip());
        return jsonP;
    }

    private List<Patient> getPatients(){
        return patientService.listAll();
    }

    private void writeDbToJson(){
        List<Patient> allPatients = getPatients();
        FileWriter file = null;
        try {
            file = new FileWriter(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Patient patient: allPatients){
            JSONObject jsonP = patientToJson(patient);
            try {
                file.write(jsonP.toJSONString()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] returnDbToJson(){
        writeDbToJson();
        byte[] dbData = null;
        try {
            dbData = readFileToByteArray(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbData;
    }
}
