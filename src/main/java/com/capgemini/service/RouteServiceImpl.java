package com.capgemini.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.exceptions.NoSuchRouteException;
import com.capgemini.model.Route;
import com.capgemini.repository.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteRepository repository;

	@Override
	@Transactional
	public Route addRoute(Route route){
		Route result = null;

		System.out.println(route);
		if (route != null) {
			result = repository.saveAndFlush(route);
		}
		return result;
	}

	@Override
	public Route findRouteById(int routeId) throws NoSuchRouteException {
		if (repository.existsById(routeId))
			return repository.findById(routeId).get();
		else
			throw new NoSuchRouteException("Route id " + routeId + " does not exist");
	}

	@Override
	public List<Route> findAllRoute() {
		return repository.findAll();
	}

//	@Override
//	public Route modifyRoute(Route route, int routeId) {
//		return repository.save(route);
//	}

	@Override
	public int modifyRouteSource(String source, int routeId) throws NoSuchRouteException {
		if (repository.existsById(routeId)) {
			return repository.updateRouteSource(source, routeId);
		}
		throw new NoSuchRouteException("Route id " + routeId + " does not exist");
	}

	@Override
	public int modifyRouteDestination(String destination, int routeId) throws NoSuchRouteException {
		if (repository.existsById(routeId)) {
			return repository.updateRouteDestination(destination, routeId);
		}
		throw new NoSuchRouteException("Route id " + routeId + " does not exist");
	}

	@Override
	public int modifyRouteDistance(int routeId, double distance) throws NoSuchRouteException {
		if (repository.existsById(routeId)) {
			return repository.updateRouteDistance(distance, routeId);
		}
		throw new NoSuchRouteException("Route id " + routeId + " does not exist");
	}

	@Override
	public int modifyRouteDuration(int routeId, double duration) throws NoSuchRouteException {
		if (repository.existsById(routeId)) {
			return repository.updateRouteDuration(duration, routeId);
		}
		throw new NoSuchRouteException("Route id " + routeId + " does not exist");
	}

	@Override
	public boolean removeRouteById(int routeId) {
		boolean result = false;
		if (repository.existsById(routeId))
			repository.deleteById(routeId);
		result = true;
		return result;

	}
}
