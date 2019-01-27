package com.raman.location.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raman.location.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

	@Query("SELECT type, COUNT(type) FROM Location GROUP BY type")
	public List<Object[]> findTypeAndTypeCount();
	
}
 	