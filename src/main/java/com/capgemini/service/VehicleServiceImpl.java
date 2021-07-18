package com.capgemini.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.capgemini.exceptions.NoSuchVehicleException;
import com.capgemini.model.Vehicle;
import com.capgemini.repository.DriverRepository;
import com.capgemini.repository.RouteRepository;
import com.capgemini.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Transactional
	@Override
	public boolean addVehicle(Vehicle vehicle) {
		boolean result = false;
		vehicle.setStatus(true);
		vehicle.setDriver(driverRepository.findById(vehicle.getDriver().getDriverId()).get());
		vehicle.setRoute(routeRepository.findById(vehicle.getRoute().getRouteId()).get());
		vehicle = vehicleRepository.save(vehicle);
		if(vehicle.getVehicleId() > 0)
			result = true;
		return result;
	}

	@Override
	public Vehicle findVehicleById(int vehicleId) throws NoSuchVehicleException {
		if(vehicleRepository.existsById(vehicleId)) {
			return vehicleRepository.findById(vehicleId).get();
		}
		throw new NoSuchVehicleException("Vehicle with id " + vehicleId + " not found.");
	}

	@Override
	public List<Vehicle> findAllVehicle() {
			return vehicleRepository.findAll();
	}

	@Override
	public int removeVehicleById(int vehicleId) throws NoSuchVehicleException {
		if (vehicleRepository.existsById(vehicleId)) {
			return vehicleRepository.updateVehicleStatus(vehicleId);
		}
		throw new NoSuchVehicleException("Vehicle with id " + vehicleId + " not found.");
	}

	@Override
	public Vehicle modifyVehicleById(Vehicle vehicle, int vehicleId) {
		vehicle.setDriver(driverRepository.findById(vehicle.getDriver().getDriverId()).get());
		vehicle.setRoute(routeRepository.findById(vehicle.getRoute().getRouteId()).get());
//		vehicle.setVehicleType();
		return vehicleRepository.save(vehicle);
	}

}
