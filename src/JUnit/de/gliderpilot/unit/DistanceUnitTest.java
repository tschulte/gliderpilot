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
public class DistanceUnitTest extends TestCase {

	/**
	 * Constructor for DistanceUnitTest.
	 * @param arg0
	 */
	public DistanceUnitTest(String arg0) {
		super(arg0);
	}
	
	public void testGetConversionFactor() {
		assertEquals(1, DistanceUnit.METER.getConversionFactor(DistanceUnit.METER), 0.00001);
		assertEquals(0.001, DistanceUnit.METER.getConversionFactor(DistanceUnit.KILOMETER), 0.00001);
	}

}
