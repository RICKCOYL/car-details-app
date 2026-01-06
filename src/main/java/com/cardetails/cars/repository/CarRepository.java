package com.cardetails.cars.repository;

import com.cardetails.cars.entity.Car;
import com.cardetails.cars.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUser(User user);
}
