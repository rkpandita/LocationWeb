package com.raman.location.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raman.location.entities.Location;
import com.raman.location.repositories.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository repository;
	
	@Override
	public Location saveLocation(Location location) {
		return repository.save(location);
	}

	@Override
	public Location updateLocation(Location location) {
		return repository.save(location);
	}

	@Override
	public void deleteLocation(Location location) {
		repository.delete(location);
	}

	@Override
	public Location getLocationById(int id) {
		return repository.findById(id).orElseThrow(()->new EntityNotFoundException());
	}

	@Override
	public List<Location> getAllLocations() {
		return repository.findAll();
	}
	
	@Override
	public List<Object[]> findTypeAndTypeCount() {
		return repository.findTypeAndTypeCount();
	}

}
