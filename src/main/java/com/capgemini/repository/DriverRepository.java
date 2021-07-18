package com.capgemini.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "Update Driver d set d.driverName = :driverName where d.driverId = :driverId")
	int updateDriverName(@Param("driverId")int driverId, @Param("driverName") String name);
	
//	@Transactional
//	@Modifying
//	@Query(value = "Update Driver d and Address a set a.city = :city and a.pincode = :pincode where d.driverId = :driverId")
//	int updateDriverAddress(@Param("driverId")int driverId, @Param("city") String city, @Param("pincode") String pincode);
	
	@Transactional
	@Modifying
	@Query(value = "Update Driver d set d.contactNo = :contactNo where d.driverId = :driverId")
	int updateDriverContactNo(@Param("driverId")int driverId, @Param("contactNo") String contactNo);
	
	@Transactional
	@Modifying
	@Query(value = "Update Driver d set d.licenceNo = :licenceNo where d.driverId = :driverId")
	int updateDriverLicenceNo(@Param("driverId")int driverId, @Param("licenceNo") String licenceNo);
	
	
}
