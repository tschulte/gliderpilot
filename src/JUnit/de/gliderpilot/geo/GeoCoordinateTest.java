/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.geo;

import junit.framework.TestCase;
import de.gliderpilot.unit.DistanceUnit;
import de.gliderpilot.unit.DistanceVector;

/**
 * @author tobias
 */
public class GeoCoordinateTest extends TestCase {

	private GeoCoordinate geoCoord, geoCoordPole;

	/**
	 * Constructor for GeoCoordinateTest.
	 * @param arg0
	 */
	public GeoCoordinateTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		geoCoord = new GeoCoordinate(0,0);
		geoCoordPole = new GeoCoordinate(90,0);
	}
	
	public void testIsValid() {
		assertTrue(geoCoord.isValid());
	}
	
	public void testDistance0() {
		DistanceVector vector = geoCoord.getDistanceTo(geoCoord);
		vector.get(DistanceUnit.METER);
		assertEquals(0, vector.getDistance(), 0.0001);
		assertEquals(0, vector.getCourse(), 0.001);
		
		vector = geoCoordPole.getDistanceTo(geoCoordPole);
		vector.get(DistanceUnit.METER);
		assertEquals(0, vector.getDistance(), 0.0001);
		assertEquals(0, vector.getCourse(), 0.001);
	}

	public void testDistance1DegreeEast() {
		GeoCoordinate to = new GeoCoordinate(0, 1);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(60, vector.getDistance(), 0.0001);
		assertEquals(90, vector.getCourse(), 0.001);


		to = new GeoCoordinate(90, 1);
		vector = geoCoordPole.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(0, vector.getDistance(), 0.0001);
		assertEquals(0, vector.getCourse(), 0.001);
	}

	public void testDistance1DegreeWest() {
		GeoCoordinate to = new GeoCoordinate(0, -1);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(60, vector.getDistance(), 0.0001);
		assertEquals(270, vector.getCourse(), 0.001);

		to = new GeoCoordinate(90, -1);
		vector = geoCoordPole.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(0, vector.getDistance(), 0.0001);
		assertEquals(0, vector.getCourse(), 0.001);
	}

	public void testDistance1DegreeNorth() {
		GeoCoordinate to = new GeoCoordinate(1, 0);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(60, vector.getDistance(), 0.0001);
		assertEquals(0, vector.getCourse(), 0.1);
	}
	public void testDistance1DegreeSouth() {
		GeoCoordinate to = new GeoCoordinate(-1, 0);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(60, vector.getDistance(), 0.0001);
		assertEquals(180, vector.getCourse(), 0.001);

		to = new GeoCoordinate(89, 0);
		vector = geoCoordPole.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(60, vector.getDistance(), 0.0001);
		assertEquals(180, vector.getCourse(), 0.001);
	}

	public void testDistance1DegreeNorthEast() {
		GeoCoordinate to = new GeoCoordinate(1, 1);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(84.85, vector.getDistance(), 0.1);
		assertEquals(45, vector.getCourse(), 0.01);
	}
	public void testDistance1DegreeSouthEast() {
		GeoCoordinate to = new GeoCoordinate(-1, 1);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(84.85, vector.getDistance(), 0.1);
		assertEquals(135, vector.getCourse(), 0.01);

		to = new GeoCoordinate(89, 1);
		vector = geoCoordPole.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(60, vector.getDistance(), 0.01);
		assertEquals(179.5, vector.getCourse() ,0.001);
	}

	public void testDistance1DegreeSouthWest() {
		GeoCoordinate to = new GeoCoordinate(-1, -1);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(84.85, vector.getDistance(), 0.1);
		assertEquals(225, vector.getCourse(), 0.01);
	}

	public void testDistance1DegreeNorthWest() {
		GeoCoordinate to = new GeoCoordinate(1, -1);
		DistanceVector vector = geoCoord.getDistanceTo(to);
		vector.get(DistanceUnit.NAUTICAL_MILE);
		assertEquals(84.85, vector.getDistance(), 0.1);
		assertEquals(315, vector.getCourse(), 0.01);
	}
	
	public void testGetCoordIn60MilesEast() {
		GeoCoordinate coord = geoCoord.getCoordIn(new DistanceVector(60, DistanceUnit.NAUTICAL_MILE, 90));
		assertEquals(1, coord.getLon(), 0.001);
		assertEquals(0, coord.getLat(), 0.001);

	}
	public void testGetCoordIn60MilesWest() {
		GeoCoordinate coord = geoCoord.getCoordIn(new DistanceVector(60, DistanceUnit.NAUTICAL_MILE, 270));
		assertEquals(-1, coord.getLon(), 0.001);
		assertEquals(0, coord.getLat(), 0.001);
	}
	public void testGetCoordIn60MilesNorth() {
		GeoCoordinate coord = geoCoord.getCoordIn(new DistanceVector(60, DistanceUnit.NAUTICAL_MILE, 0));
		assertEquals(0, coord.getLon(), 0.001);
		assertEquals(1, coord.getLat(), 0.001);
	}
	public void testGetCoordIn60MilesSouth() {
		GeoCoordinate coord = geoCoord.getCoordIn(new DistanceVector(60, DistanceUnit.NAUTICAL_MILE, 180));
		assertEquals(0, coord.getLon(), 0.001);
		assertEquals(-1, coord.getLat(), 0.001);
	}

}
