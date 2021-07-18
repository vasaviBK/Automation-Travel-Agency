package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.NoSuchDriverException;
import com.capgemini.model.Driver;

public interface DriverService {
	public Driver addDriver(Driver driver);
	public Driver findDriverById(int driverId)throws NoSuchDriverException;
	public List<Driver> findAllDriver();
	//public Driver modifyDriver(Driver driver,int driverId);
	public boolean removeDriverById(int driverId)throws NoSuchDriverException;
	public int updateDriverName(int driverId, String driverName) throws NoSuchDriverException;
	//public int updateDriverAddress(int driverId, String city, String pincode) throws NoSuchDriverException;
	public int updateDriverContactNo(int driverId, String contactNo) throws NoSuchDriverException;
	public int updateDriverLicenceNo(int driverId, String licenceNo) throws NoSuchDriverException;

}
