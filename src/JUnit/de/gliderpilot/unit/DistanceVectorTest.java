/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.unit;

import junit.framework.TestCase;

/**
 * @author tobias
 */
public class DistanceVectorTest extends TestCase {

	/**
	 * Constructor for DistanceVectorTest.
	 * @param arg0
	 */
	public DistanceVectorTest(String arg0) {
		super(arg0);
	}
	
	public void testCourse() {
		DistanceVector vector = new DistanceVector(1, DistanceUnit.METER, 0);
		assertEquals(0, vector.getCourse(), 0.0001);
		vector = new DistanceVector(1, DistanceUnit.METER, 1);
		assertEquals(1, vector.getCourse(), 0.0001);
		vector = new DistanceVector(1, DistanceUnit.METER, 360);
		assertEquals(360, vector.getCourse(), 0.0001);
		vector = new DistanceVector(1, DistanceUnit.METER, 360.1);
		assertEquals(0.1, vector.getCourse(), 0.001);
	}
	
	public void testSumCourse0() {
		DistanceVector vector1 = new DistanceVector(1, DistanceUnit.METER, 0);
		DistanceVector vector2 = new DistanceVector(1, DistanceUnit.METER, 0);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(2, sum.getDistance(), 0.0001);
		assertEquals(0, sum.getCourse(), 0.0001);
	}

	public void testSumCourseChange() {
		DistanceVector vector1 = new DistanceVector(1, DistanceUnit.METER, 0);
		DistanceVector vector2 = new DistanceVector(1, DistanceUnit.METER, 90);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(Math.sqrt(2), sum.getDistance(), 0.0001);
		assertEquals(45, sum.getCourse(), 0.0001);
	}

	public void testSum0() {
		DistanceVector vector1 = new DistanceVector(1, DistanceUnit.METER, 0);
		DistanceVector vector2 = new DistanceVector(0.999999999, DistanceUnit.METER, 180);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(0, sum.getDistance(), 0.0001);
		assertEquals(0, sum.getCourse(), 0.0001);
	}

	public void testSum270() {
		DistanceVector vector1 = new DistanceVector(1, DistanceUnit.METER, 0);
		DistanceVector vector2 = new DistanceVector(0.999999999, DistanceUnit.METER, 270);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(Math.sqrt(2), sum.getDistance(), 0.0001);
		assertEquals(315, sum.getCourse(), 0.0001);
	}

	public void testSum45_315() {
		DistanceVector vector1 = new DistanceVector(1, DistanceUnit.METER, 45);
		DistanceVector vector2 = new DistanceVector(0.999999999, DistanceUnit.METER, 315);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(Math.sqrt(2), sum.getDistance(), 0.0001);
		assertEquals(0, sum.getCourse(), 0.0001);
	}
	public void testSum315_45() {
		DistanceVector vector1 = new DistanceVector(1, DistanceUnit.METER, 315);
		DistanceVector vector2 = new DistanceVector(0.999999999, DistanceUnit.METER, 45);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(Math.sqrt(2), sum.getDistance(), 0.0001);
		assertEquals(0, sum.getCourse(), 0.00001);
	}

	public void testSum0_90() {
		DistanceVector vector1 = new DistanceVector(0, DistanceUnit.METER, 0);
		DistanceVector vector2 = new DistanceVector(1, DistanceUnit.METER, 90);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(1, sum.getDistance(), 0.0001);
		assertEquals(90, sum.getCourse(), 0.00001);
	}

	public void testSum0_270() {
		DistanceVector vector1 = new DistanceVector(0, DistanceUnit.METER, 0);
		DistanceVector vector2 = new DistanceVector(1, DistanceUnit.METER, 270);
		DistanceVector sum = vector1.getSum(vector2);
		assertEquals(1, sum.getDistance(), 0.0001);
		assertEquals(270, sum.getCourse(), 0.00001);
	}
	
	public void testGetXAmount() {
		DistanceVector v1 = new DistanceVector(1, DistanceUnit.METER, 90);
		DistanceVector vx = v1.getXAmount();
		assertEquals(1, vx.getDistance(), 0.0001);
	}
	public void testGetYAmount() {
		DistanceVector v1 = new DistanceVector(1, DistanceUnit.METER, 0);
		DistanceVector vy = v1.getYAmount();
		assertEquals(1, vy.getDistance(), 0.0001);
	}
}
