package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.NoSuchVehicleException;
import com.capgemini.model.Vehicle;

public interface VehicleService {
	
	public boolean addVehicle(Vehicle vehicle);
	public Vehicle findVehicleById(int vehicleId) throws NoSuchVehicleException;
	public List<Vehicle> findAllVehicle();
	public int removeVehicleById(int vehicleId) throws NoSuchVehicleException;
	public Vehicle modifyVehicleById(Vehicle vehicle, int vehicleId);
	
}
