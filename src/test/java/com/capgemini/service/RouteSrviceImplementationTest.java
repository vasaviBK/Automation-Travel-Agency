package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.capgemini.exceptions.NoSuchRouteException;
import com.capgemini.model.Route;

class RouteSrviceImplementationTest {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private RouteService service;

	@Test
	public void testFindRouteByIdShouldReturnRoute() throws NoSuchRouteException {
		Route expected = context.getBean(Route.class);
		expected.setSource("Test");
		expected.setDestination("Test");
		expected.setDistance(10.0);
		expected.setDuration(10.0);

		service.addRoute(expected);

		Route actual = service.findRouteById(expected.getRouteId());
		assertEquals(expected.getRouteId(), actual.getRouteId());
		assertEquals(expected.getSource(), actual.getSource());
		assertEquals(expected.getDestination(), actual.getDestination());
		assertEquals(expected.getDistance(), actual.getDistance());
		assertEquals(expected.getDuration(), actual.getDuration());

		service.removeRouteById(expected.getRouteId());
	}

}
