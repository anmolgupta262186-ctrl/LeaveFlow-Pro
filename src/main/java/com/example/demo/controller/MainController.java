package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Employee;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.LeaveRepository;

@Controller
public class MainController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private LeaveRepository leaveRepo;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/employee")
    public String employeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeRepo.save(employee);
        return "redirect:/";
    }

    @GetMapping("/leave")
    public String leaveForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "leave";
    }

    @PostMapping("/saveLeave")
    public String saveLeave(@ModelAttribute LeaveRequest leaveRequest) {
        leaveRequest.setStatus("Pending");
        leaveRepo.save(leaveRequest);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("leaves", leaveRepo.findAll());
        return "dashboard";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        LeaveRequest leave = leaveRepo.findById(id).orElse(null);
        if (leave != null) {
            leave.setStatus("Approved");
            leaveRepo.save(leave);
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        LeaveRequest leave = leaveRepo.findById(id).orElse(null);
        if (leave != null) {
            leave.setStatus("Rejected");
            leaveRepo.save(leave);
        }
        return "redirect:/dashboard";
    }
}