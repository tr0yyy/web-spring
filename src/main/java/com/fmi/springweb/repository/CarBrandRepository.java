package com.fmi.springweb.repository;

import com.fmi.springweb.model.CarBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrandEntity, Long> {
    Optional<CarBrandEntity> findByBrandName(String brandName);
    default CarBrandEntity insertIfNotExists(String carBrandName, String carBrandCountry) {
        CarBrandEntity existingCarBrandEntity = findByBrandName(carBrandName).orElse(null);

        if (existingCarBrandEntity == null) {
            CarBrandEntity carBrandEntity = new CarBrandEntity();
            carBrandEntity.setBrandName(carBrandName);
            carBrandEntity.setCountry(carBrandCountry);
            return save(carBrandEntity);
        }

        return existingCarBrandEntity;
    }
}
