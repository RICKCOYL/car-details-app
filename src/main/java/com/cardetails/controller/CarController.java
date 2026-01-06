package com.cardetails.controller;

import com.cardetails.entity.Car;
import com.cardetails.entity.User;
import com.cardetails.model.Role;
import com.cardetails.service.CarService;
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

        carService.saveCar(car, user);

        Role role = resolveRole(session.getAttribute("userRole"));
        return carService.determinePostSaveRedirect(role);
    }

    @GetMapping("/my-cars")
    public String myCars(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
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

        Role role = resolveRole(session.getAttribute("userRole"));

        CarService.CarListView carListView = carService.getCarsForAdminSection(user, role);
        if (!carListView.isAdminView()) {
            return "redirect:/my-cars";
        }

        model.addAttribute("cars", carListView.getCars());
        model.addAttribute("isAdmin", carListView.isAdminView());
        return "car-list";
    }

    @GetMapping("/vehicles/all")
    @ResponseBody
    public ResponseEntity<?> getAllVehicles() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

        private Role resolveRole(Object roleAttr) {
            if (roleAttr instanceof Role) {
                return (Role) roleAttr;
            }
            if (roleAttr instanceof String str) {
                return Role.from(str);
            }
            return Role.USER;
        }

}
