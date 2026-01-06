package com.cardetails.repository;

import com.cardetails.entity.Car;
import com.cardetails.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByCreatedByUser(User user);
    List<Car> findAll();
}
