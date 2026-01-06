package com.cardetails.cars.service;

import com.cardetails.cars.entity.Car;
import com.cardetails.cars.entity.User;
import com.cardetails.cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> getCarsByUser(User user) {
        return carRepository.findByUser(user);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }
}
