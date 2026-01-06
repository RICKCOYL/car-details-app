package com.cardetails.service;

import com.cardetails.entity.Car;
import com.cardetails.entity.User;
import com.cardetails.model.Role;
import com.cardetails.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    @Transactional
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

    public boolean isAdminRole(Role role) {
        if (role == null) {
            return false;
        }
        return role == Role.ADMIN;
    }

    public String determinePostSaveRedirect(Role role) {
        return isAdminRole(role) ? "redirect:/all-cars" : "redirect:/my-cars";
    }

    public CarListView getCarsForAdminSection(User currentUser, Role role) {
        Objects.requireNonNull(currentUser, "Current user cannot be null");

        boolean admin = isAdminRole(role);
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
