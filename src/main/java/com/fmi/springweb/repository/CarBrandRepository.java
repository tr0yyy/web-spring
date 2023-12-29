package com.fmi.springweb.repository;

import com.fmi.springweb.model.CarBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrandEntity, Long> {

}
