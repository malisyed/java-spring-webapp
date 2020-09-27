package com.mas.patientmanagementwebapp.controller;

import com.mas.patientmanagementwebapp.service.DbToJsonService;
import com.mas.patientmanagementwebapp.service.PatientService;
import com.mas.patientmanagementwebapp.model.Patient;
import com.mas.patientmanagementwebapp.model.User;
import com.mas.patientmanagementwebapp.service.PatientUploadService;
import com.mas.patientmanagementwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private DbToJsonService dbToJsonService;

    @Autowired
    private PatientUploadService patientUploadService;

    @RequestMapping("/")
    public String redirectHome(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String viewHomePage(){
        return "index";
    }

    @RequestMapping("/patients")
    public String viewPatientsPage(Model model){
        List<Patient> listPatients = patientService.listAll();
        model.addAttribute("listPatients", listPatients);
        return "patients";
    }

    @RequestMapping("/addPatient")
    public String newPatientForm(Model model){
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "new_patient";
    }

    @RequestMapping(value = "/saveNewPatient", method = RequestMethod.POST)
    public ModelAndView saveNewPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("new_patient");
        if (bindingResult.hasErrors()){
            mav.addObject("successMessage", "Invalid inputs");
            return mav;
        }
        if (patientService.patientExists(patient)){
            mav.addObject("successMessage", "Patient already exists");
            return mav;
        }
        patientService.save(patient);
        mav.addObject("patient", new Patient());
        ModelAndView modelAndView =  new ModelAndView("redirect:/patients");
        return modelAndView;
    }


    @RequestMapping("/editPatient/{id}")
    public ModelAndView editPatientForm(@PathVariable(name = "id") int id){
        ModelAndView  mav = new ModelAndView("edit_patient");
        Patient patient = patientService.get(id);
        mav.addObject("patient", patient);
        patientService.delete(id);
        return mav;
    }

    @RequestMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable(name = "id") int id){
        patientService.delete(id);
        return "redirect:/patients";
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(name = "file") MultipartFile file){
        patientUploadService.uploadFile(file);
        return "redirect:/patients";
    }

    @RequestMapping(value = "/patientsJSON", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> patientDataJson(){
        String path = dbToJsonService.getPath();
        byte[] dbJson = dbToJsonService.returnDbToJson();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path + "\"")
                .body(dbJson);
    }

    @RequestMapping(value = "/login")
    public String loginRequest(Principal user){
        if (user != null){
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        if (bindingResult.hasErrors()){
            mav.addObject("successMessage", "Invalid inputs");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else if (userService.userExists(user)){
            mav.addObject("successMessage", "Account already exists");
        } else {
            userService.saveUser(user);
            mav.addObject("user", new User());
            ModelAndView modelAndView =  new ModelAndView("redirect:/patients");
            return modelAndView;
        }
        return mav;
    }

    public ModelAndView validRegisterRequest(){
        ModelAndView mav = new ModelAndView();
        User user = new User();
        mav.addObject("user", user);
        mav.setViewName("register");
        return mav;
    }

    @RequestMapping(value = "/register")
    public ModelAndView registerRequest(Principal user){
        if (user != null){
            ModelAndView mav = new ModelAndView();
            mav.setViewName("index");
            return mav;
        }
        return validRegisterRequest();
    }

    @RequestMapping("/access-denied")
    public String accessDenied(){ return "access-denied"; }
}
