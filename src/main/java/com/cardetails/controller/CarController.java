package com.cardetails.controller;

import com.cardetails.entity.Car;
import com.cardetails.entity.User;
import com.cardetails.service.CarService;
import com.cardetails.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final UserService userService;

    @GetMapping("/car-form")
    public String carForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("car", new Car());
        return "car-form";
    }

    @PostMapping("/car/add")
    public String addCar(@ModelAttribute Car car, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        // Delegate business logic to service layer
        carService.saveCar(car, user);
        
        String role = (String) session.getAttribute("userRole");
        if ("ADMIN".equals(role)) {
            return "redirect:/all-cars";
        } else {
            return "redirect:/my-cars";
        }
    }

    @GetMapping("/my-cars")
    public String myCars(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        // Delegate business logic to service layer
        List<Car> cars = carService.getCarsByUser(user);
        model.addAttribute("cars", cars);
        model.addAttribute("isAdmin", false);
        return "car-list";
    }

    @GetMapping("/all-cars")
    public String allCars(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        String role = (String) session.getAttribute("userRole");
        // Delegate authorization check to service layer (business logic)
        if (!"ADMIN".equals(role)) {
            return "redirect:/my-cars";
        }
        
        // Delegate data retrieval to service layer
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        model.addAttribute("isAdmin", true);
        return "car-list";
    }

    @GetMapping("/vehicles/all")
    @ResponseBody
    public ResponseEntity<?> getAllVehicles() {
        // Delegate data retrieval to service layer
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

}
