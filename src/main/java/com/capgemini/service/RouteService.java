package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.NoSuchRouteException;
import com.capgemini.model.Route;

public interface RouteService {
	public Route addRoute(Route route);

	public Route findRouteById(int routeId) throws NoSuchRouteException;

	public List<Route> findAllRoute();

	public int modifyRouteSource(String source, int routeId) throws NoSuchRouteException;

	public int modifyRouteDestination(String destination, int routeId) throws NoSuchRouteException;

	public int modifyRouteDistance(int routeId, double distance) throws NoSuchRouteException;

	public int modifyRouteDuration(int routeId, double duration) throws NoSuchRouteException;

	public boolean removeRouteById(int routeId);
}
