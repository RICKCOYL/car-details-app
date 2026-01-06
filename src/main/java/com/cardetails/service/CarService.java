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

    /**
     * Saves a car for a given user.
     * Business logic: Associates the car with the user creating it.
     */
    public Car saveCar(Car car, User creator) {
        Objects.requireNonNull(car, "Car cannot be null");
        Objects.requireNonNull(creator, "Creator user cannot be null");
        
        car.setCreatedByUser(creator);
        return carRepository.save(car);
    }

    /**
     * Retrieves all cars created by a specific user.
     * Business logic: Enforces user isolation for data retrieval.
     */
    public List<Car> getCarsByUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        return carRepository.findByCreatedByUser(user);
    }

    /**
     * Retrieves all cars in the system.
     * Business logic: Only admins should call this method (checked by controller).
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Retrieves a car by its ID.
     * Business logic: Returns null if not found.
     */
    public Car getCarById(Integer id) {
        Objects.requireNonNull(id, "Car ID cannot be null");
        return carRepository.findById(id).orElse(null);
    }

    /**
     * Determines whether a user can view a specific car.
     * Business logic: Admin can view all cars, regular users can only view their own cars.
     */
    public boolean canUserViewCar(Car car, User user, String userRole) {
        Objects.requireNonNull(car, "Car cannot be null");
        Objects.requireNonNull(user, "User cannot be null");
        
        if ("ADMIN".equals(userRole)) {
            return true;
        }
        
        return car.getCreatedByUser().getId().equals(user.getId());
    }

    /**
     * Determines whether a user can edit a specific car.
     * Business logic: Only the creator or admin can edit a car.
     */
    public boolean canUserEditCar(Car car, User user, String userRole) {
        Objects.requireNonNull(car, "Car cannot be null");
        Objects.requireNonNull(user, "User cannot be null");
        
        if ("ADMIN".equals(userRole)) {
            return true;
        }
        
        return car.getCreatedByUser().getId().equals(user.getId());
    }

}

