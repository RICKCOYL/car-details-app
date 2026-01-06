package com.cardetails.service;

import com.cardetails.entity.Car;
import com.cardetails.entity.User;
import com.cardetails.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    public Car saveCar(Car car, User creator) {
        Objects.requireNonNull(car, "Car cannot be null");
        Objects.requireNonNull(creator, "Creator user cannot be null");
        
        car.setCreatedByUser(creator);
        return carRepository.save(car);
    }
    public List<Car> getCarsByUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        return carRepository.findByCreatedByUser(user);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public boolean isAdminRole(String userRole) {
        if (userRole == null) {
            return false;
        }
        return "ADMIN".equals(userRole);
    }

    public String determinePostSaveRedirect(String userRole) {
        return isAdminRole(userRole) ? "redirect:/all-cars" : "redirect:/my-cars";
    }

    public CarListView getCarsForAdminSection(User currentUser, String userRole) {
        Objects.requireNonNull(currentUser, "Current user cannot be null");

        boolean admin = isAdminRole(userRole);
        List<Car> cars = admin ? getAllCars() : getCarsByUser(currentUser);
        return new CarListView(cars, admin);
    }

    public static class CarListView {
        private final List<Car> cars;
        private final boolean adminView;

        public CarListView(List<Car> cars, boolean adminView) {
            this.cars = cars;
            this.adminView = adminView;
        }

        public List<Car> getCars() {
            return cars;
        }

        public boolean isAdminView() {
            return adminView;
        }
    }

}
