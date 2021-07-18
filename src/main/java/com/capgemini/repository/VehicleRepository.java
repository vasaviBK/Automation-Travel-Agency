package com.capgemini.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	
//	@Query(value = "Select v From Vehicle v Where v.vehicleName = :name")
//	public List<Vehicle> readAllVehicle(@Param("name") String vehicleName);	
	
	@Query(value = "SELECT v, r FROM Vehicle v JOIN v.route r where r.source = :source and r.destination = :destination and v.journeyDate = :journeyDate")
	public List<Vehicle> searchByCustomerRequest(@Param("source") String source, @Param("destination") String destination, @Param("journeyDate") LocalDate journeyDate);
	
	@Modifying
	@Transactional
	@Query(value = "Update Vehicle v Set v.status= false where v.vehicleId = :vehicleId")
	int updateVehicleStatus(@Param("vehicleId") int vehicleId);
}
