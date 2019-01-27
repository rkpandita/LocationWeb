package com.raman.location.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raman.location.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
