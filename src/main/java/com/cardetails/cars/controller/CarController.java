package com.cardetails.cars.controller;

import com.cardetails.cars.entity.Car;
import com.cardetails.cars.entity.User;
import com.cardetails.cars.service.CarService;
import com.cardetails.cars.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class CarController {
    @Autowired
    private CarService carService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        List<Car> cars;
        if ("admin".equals(user.getRole())) {
            cars = carService.getAllCars();
        } else {
            cars = carService.getCarsByUser(user);
        }
        
        model.addAttribute("user", user);
        model.addAttribute("cars", cars);
        return "dashboard";
    }

    @PostMapping("/addCar")
    public String addCar(@RequestParam String make, @RequestParam String model, @RequestParam Integer year, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setYear(year);
        car.setUser(user);
        carService.saveCar(car);
        
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/vehicles/all")
    @ResponseBody
    public List<Car> getAllVehicles() {
        return carService.getAllCars();
    }
}
