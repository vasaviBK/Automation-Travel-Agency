package com.capgemini.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchDriverException;
import com.capgemini.exceptions.NoSuchRouteException;
import com.capgemini.exceptions.NoSuchVehicleException;
import com.capgemini.model.Driver;
import com.capgemini.model.Route;
import com.capgemini.model.Vehicle;
import com.capgemini.service.DriverService;
import com.capgemini.service.RouteService;
import com.capgemini.service.VehicleService;

@RestController
@RequestMapping(path = "admin")
public class AdminController {


	@Autowired
	private VehicleService service1;

	// ------------------------------ VEHICLE CONTROLLER
	// START-------------------------------------------------

	// http://localhost:9090/user-api/admin/vehicles/ -POST
	@PostMapping(path = "vehicles/")
	public ResponseEntity<String> saveVehicle(@Valid @RequestBody Vehicle vehicle)  throws NoSuchRouteException, NoSuchDriverException{
		ResponseEntity<String> response = null;
		boolean result = service1.addVehicle(vehicle);
		if (result)
			response = new ResponseEntity<String>("Vehicle with id " + vehicle.getVehicleId() + " is added",
					HttpStatus.CREATED);
		return response;
	}

	// http://localhost:9090/user-api/admin/searchByVehicleId/1 -GET
	@GetMapping(path = "searchByVehicleId/{vehicleId}")
	public ResponseEntity<Vehicle> getVehicleById(@PathVariable("vehicleId") int vehicleId)
			throws NoSuchVehicleException {
		ResponseEntity<Vehicle> response = null;
		Vehicle vehicle = service1.findVehicleById(vehicleId);
		response = new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/getAllVehicles -GET
	@GetMapping(path = "getAllVehicles")
	public ResponseEntity<List<Vehicle>> getAllVehicles() {
		ResponseEntity<List<Vehicle>> response;
		List<Vehicle> list = service1.findAllVehicle();
		response = new ResponseEntity<List<Vehicle>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/updateVehicle/1 -PUT
	@PutMapping(path = "updateVehicle/{vehicleId}")
	public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("vehicleId") int vehicleId)
			throws NoSuchVehicleException {
		ResponseEntity<Vehicle> response = null;
		Vehicle result = service1.modifyVehicleById(vehicle, vehicleId);
		response = new ResponseEntity<Vehicle>(result, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/deleteVehicle/1 -DELETE
	@PutMapping(path = "deleteVehicle/{vehicleId}")
	public ResponseEntity<String> deleteVehicle(@PathVariable("vehicleId") int vehicleId)
			throws NoSuchVehicleException {
		ResponseEntity<String> response = null;
		int result = service1.removeVehicleById(vehicleId);
		if (result != 0)
			response = new ResponseEntity<String>("Vehicle is deleted.", HttpStatus.OK);
		return response;
	}


//	VEHICLE CONTROLLER ENDS -------------------------------------------------------------------------------------------

//	DRIVER CONTROLLER STARTS-------------------------------------------------------------------------------------------

	@Autowired
	private DriverService service2;

	// http://localhost:9090/user-api/admin/saveDriver -POST
	@PostMapping(path = "saveDriver")
	public ResponseEntity<String> saveDriver(@Valid @RequestBody Driver driver) {

		ResponseEntity<String> response = null;
		Driver result = service2.addDriver(driver);
		if (result != null)
			response = new ResponseEntity<String>("Driver with id " + driver.getDriverId() + " is added.",
					HttpStatus.CREATED);
		return response;
	}

	// http://localhost:9090/user-api/admin/searchByDriverId/1 -GET
	@GetMapping(path = "searchByDriverId/{driverId}")
	public ResponseEntity<Driver> getDriverById(@PathVariable("driverId") int driverId) throws NoSuchDriverException {
		ResponseEntity<Driver> response = null;
		Driver driver = service2.findDriverById(driverId);
		response = new ResponseEntity<Driver>(driver, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/getAllDrivers -GET
	@GetMapping(path = "getAllDrivers")
	public ResponseEntity<List<Driver>> getAllDrivers() {
		ResponseEntity<List<Driver>> response = null;
		List<Driver> list = service2.findAllDriver();
		response = new ResponseEntity<List<Driver>>(list, HttpStatus.OK);
		return response;
	}

//		// http://localhost:9090/user-api/admin/updateDriver/1
//		@PutMapping(path = "updateDriver/{driverId}")
//		public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver, @PathVariable("driverId") int driverId) {
//			ResponseEntity<Driver> response = null;
//			Driver result = service2.modifyDriver(driver, driverId);
//			response = new ResponseEntity<Driver>(result, HttpStatus.OK);
//			return response;
//		}

	// http://localhost:9090/user-api/admin/updateDriverName/2/Ram
	@PutMapping(path = "updateDriverName/{driverId}/{driverName}")
	public ResponseEntity<String> updateDriverName(@PathVariable("driverName") String driverName,
			@PathVariable("driverId") int driverId) throws NoSuchDriverException {
		ResponseEntity<String> response = null;
		// Driver driver = null;
		int result = service2.updateDriverName(driverId, driverName);
		if (result > 0)
			response = new ResponseEntity<String>("Driver Name with id " + driverId + " is updated", HttpStatus.OK);
		return response;
	}

//		// http://localhost:9090/user-api/admin/updateDriverAddress/1/Hyderabad/500002
//		@PutMapping(path = "updateDriverAddress/{driverId}/{city}/{pincode}")
//		public ResponseEntity<String> updateDriverAddress(@PathVariable("city") String city,@PathVariable("pincode") String pincode,
//				@PathVariable("driverId") int driverId) throws NoSuchDriverException {
//			ResponseEntity<String> response = null;
//			// Driver driver = null;
//			int result = service2.updateDriverAddress(driverId, city, pincode);
//			if (result > 0)
//				response = new ResponseEntity<String>("Driver Name with id " + driverId + " is updated", HttpStatus.OK);
//			return response;
//		}

	// http://localhost:9090/user-api/admin/updateDriverContactNo/2/8106762539
	@PutMapping(path = "updateDriverContactNo/{driverId}/{contactNo}")
	public ResponseEntity<String> updateDriverContactNo(@PathVariable("contactNo") String contactNo,
			@PathVariable("driverId") int driverId) throws NoSuchDriverException {
		ResponseEntity<String> response = null;
		// Driver driver = null;
		int result = service2.updateDriverContactNo(driverId, contactNo);
		if (result > 0)
			response = new ResponseEntity<String>("Driver ContactNo with id " + driverId + " is updated",
					HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/updateDriverLicenceNo/2/236781
	@PutMapping(path = "updateDriverLicenceNo/{driverId}/{licenceNo}")
	public ResponseEntity<String> updateDriverLicenceNo(@PathVariable("licenceNo") String licenceNo,
			@PathVariable("driverId") int driverId) throws NoSuchDriverException {
		ResponseEntity<String> response = null;
		// Driver driver = null;
		int result = service2.updateDriverLicenceNo(driverId, licenceNo);
		if (result > 0)
			response = new ResponseEntity<String>("Driver LicenceNo with id " + driverId + " is updated",
					HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/deleteDriver/1
	@DeleteMapping(path = "deleteDriver/{driverId}")
	public ResponseEntity<String> deleteDriver(@PathVariable("driverId") int driverId) throws NoSuchDriverException {
		ResponseEntity<String> response = null;
		boolean result = service2.removeDriverById(driverId);
		if (result)
			response = new ResponseEntity<String>("Driver is deleted.", HttpStatus.OK);
		return response;
	}


//	DRIVER CONTROLLER ENDS----------------------------------------------------------------------------------------------------

//	ROUTE CONTROLLER STARTS---------------------------------------------------------------------------------------------------

	@Autowired
	private RouteService service3;

	// http://localhost:9090/user-api/admin/route/ -POST
	@PostMapping(path = "route/")
	public ResponseEntity<String> saveRoute(@Valid @RequestBody Route route) throws NoSuchRouteException{
		ResponseEntity<String> response = null;
		Route result = service3.addRoute(route);
		if (result != null)
			response = new ResponseEntity<String>("Route with id " + route.getRouteId() + " is added.",
					HttpStatus.CREATED);
		else
			throw new NoSuchRouteException("Route is not added");
		return response;
	}

	// http://localhost:9090/user-api/admin/route/searchByRouteId/1 -GET
	@GetMapping(path = "route/searchByRouteId/{routeId}")
	public ResponseEntity<Route> getRouteById(@PathVariable("routeId") int routeId) throws NoSuchRouteException {
		ResponseEntity<Route> response = null;
		Route route = service3.findRouteById(routeId);
		response = new ResponseEntity<Route>(route, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/route/ -GET
	@GetMapping(path = "route/")
	public ResponseEntity<List<Route>> getAllRoute() {
		ResponseEntity<List<Route>> response = null;
		List<Route> list = service3.findAllRoute();
		response = new ResponseEntity<List<Route>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/updateRoute/1
//	@PutMapping(path = "updateRoute/{routeId}")
//	public ResponseEntity<Route> updateRoute(@RequestBody Route route, @PathVariable("routeId") int routeId) {
//		ResponseEntity<Route> response = null;
//		Route result = service3.modifyRoute(route, routeId);
//		response = new ResponseEntity<Route>(result, HttpStatus.OK);
//		return response;
//
//	}

	// http://localhost:9090/user-api/admin/route/updateRouteSource/1/pune
	@PutMapping(path = "route/updateRouteSource/{routeId}/{source}")
	public ResponseEntity<String> modifyRouteDestination(@PathVariable("source") String source,
			@PathVariable("routeId") int routeId) throws NoSuchRouteException {
		ResponseEntity<String> response = null;
		int result = service3.modifyRouteSource(source, routeId);
		if (result != 0)
			response = new ResponseEntity<String>("Route with id " + routeId + " is updated", HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/updateRouteDestination/1/pune
	@PutMapping(path = "/route/updateRouteDestination/{routeId}/{destination}")
	public ResponseEntity<String> modifyRouteDistance(@PathVariable("destination") String destination,
			@PathVariable("routeId") int routeId) throws NoSuchRouteException {
		ResponseEntity<String> response = null;
		int result = service3.modifyRouteDestination(destination, routeId);
		if (result != 0)
			response = new ResponseEntity<String>("Route with id " + routeId + " is updated", HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/updateRouteDistance/1/149
	@PutMapping(path = "route/updateRouteDistance/{routeId}/{distance}")
	public ResponseEntity<String> modifyRouteDistance(@PathVariable("distance") double distance,
			@PathVariable("routeId") int routeId) throws NoSuchRouteException {
		ResponseEntity<String> response = null;
		int result = service3.modifyRouteDistance(routeId, distance);
		if (result != 0)
			response = new ResponseEntity<String>("Route with id " + routeId + " is updated", HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/updateRouteDuration/1/4
	@PutMapping(path = "route/updateRouteDuration/{routeId}/{duration}")
	public ResponseEntity<String> modifyRouteDuration(@PathVariable("duration") double duration,
			@PathVariable("routeId") int routeId) throws NoSuchRouteException {
		ResponseEntity<String> response = null;
		int result = service3.modifyRouteDuration(routeId, duration);
		if (result != 0)
			response = new ResponseEntity<String>("Route with id " + routeId + " is updated", HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/admin/deleteRoute/1
	@DeleteMapping(path = "/route/deleteRoute/{routeId}")
	public ResponseEntity<String> deleteRoute(@PathVariable("routeId") int routeId) {
		ResponseEntity<String> response = null;
		boolean result = service3.removeRouteById(routeId);
		if (result)
			response = new ResponseEntity<String>("Route is deleted.", HttpStatus.OK);
		return response;
	}
//	ROUTE CONTROLLER ENDS------------------------------------------------------------------------------------------------------------

}
