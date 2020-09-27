package com.mas.patientmanagementwebapp.service;

import com.mas.patientmanagementwebapp.model.Patient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PatientUploadService {
    @Autowired
    private PatientService patientService;
    private String path = "tempData";

    public void fileWrite(MultipartFile file){
        try (OutputStream os = Files.newOutputStream(Paths.get(path))) {
            os.write(file.getBytes());
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void jsonParser(String line){
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(line);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        String patientID = (String) jsonObject.get("ID");
        String SSN = (String) jsonObject.get("SSN");
        String First = (String) jsonObject.get("First");
        String Last = (String) jsonObject.get("Last");
        String Ethnicity = (String) jsonObject.get("Ethnicity");
        String SGender = (String) jsonObject.get("Gender");
        char Gender = SGender.charAt(0);
        String Zip = (String) jsonObject.get("Zip");
        Patient patient = new Patient(patientID, SSN, First, Last, Ethnicity, Gender, Zip);
        if (patientService.patientExists(patient) != true){
            patientService.save(patient);
        }
    }

    public void uploadFile(MultipartFile file){
        fileWrite(file);
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader((path)));
            while ((sCurrentLine = br.readLine()) != null) {
                jsonParser(sCurrentLine);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        try {
            if (br != null) br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
