package com.capgemini.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.exceptions.NoSuchDriverException;
import com.capgemini.model.Driver;
import com.capgemini.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverRepository repository;

	@Override
	@Transactional
	public Driver addDriver(Driver driver) {
		//boolean result = false;
		driver = repository.save(driver);
		//if (driver.getDriverId() > 0)
			//result = true;
		return driver;
	}

	@Override
	public Driver findDriverById(int driverId) throws NoSuchDriverException {
		if (repository.existsById(driverId)) {
			return repository.findById(driverId).get();
		}
		throw new NoSuchDriverException("Driver with id " + driverId + " not found");
	}

	@Override
	public List<Driver> findAllDriver() {
		return repository.findAll();
	}

//	@Override
//	public Driver modifyDriver(Driver driver, int driverId) {
//		return repository.save(driver);
//	}
//	

	@Override
	public int updateDriverName(int driverId, String driverName) throws NoSuchDriverException {
		if (repository.existsById(driverId)) {
			return repository.updateDriverName(driverId, driverName);
		}
		throw new NoSuchDriverException("Driver with id " + driverId + " not found");
	}

//	@Override
//	public int updateDriverAddress(int driverId, String city, String pincode) throws NoSuchDriverException {
//		if(repository.existsById(driverId)) {
//			return repository.updateDriverAddress(driverId, city, pincode);
//		}
//		throw new NoSuchDriverException("Driver with id "+driverId+" not found");
//	}

	@Override
	public int updateDriverContactNo(int driverId, String contactNo) throws NoSuchDriverException {
		if (repository.existsById(driverId)) {
			return repository.updateDriverContactNo(driverId, contactNo);
		}
		throw new NoSuchDriverException("Driver with id " + driverId + " not found");
	}

	@Override
	public int updateDriverLicenceNo(int driverId, String licenceNo) throws NoSuchDriverException {
		if (repository.existsById(driverId)) {
			return repository.updateDriverLicenceNo(driverId, licenceNo);
		}
		throw new NoSuchDriverException("Driver with id " + driverId + " not found");
	}

	@Override
	public boolean removeDriverById(int driverId) throws NoSuchDriverException {
		if (repository.existsById(driverId)) {
			repository.deleteById(driverId);
			return true;
		}
		throw new NoSuchDriverException("Driver with id " + driverId + " not found");
	}

}
