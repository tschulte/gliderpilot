/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.unit;

import javax.units.NonSI;
import javax.units.SI;
import javax.units.Unit;

import com.dautelle.JADE;
import com.dautelle.physics.Length;
import com.dautelle.physics.Quantity;

import junit.framework.TestCase;

/**
 * @author tobias
 */
public class DistanceTest extends TestCase {

	/**
	 * Constructor for DistanceTest.
	 * @param arg0
	 */
	public DistanceTest(String arg0) {
		super(arg0);
		JADE.initialize();
	}
	
	public void testAdd() {
		Distance dist = new Distance(0, DistanceUnit.METER);
		Distance newDist = dist.getSum(new Distance(1, DistanceUnit.KILOMETER));
		assertEquals(1000, newDist.getDistance(), 0.00001);
		assertEquals(DistanceUnit.METER, newDist.getUnit());
		
		Quantity length = Length.ZERO;
		Quantity newLength = length.add(Quantity.valueOf("1 km"));
		assertEquals(1000, newLength.intValue());
		assertEquals(SI.METER, newLength.getSystemUnit());
	}
	
	public void testConversion() {
		Distance dist = new Distance(1, DistanceUnit.METER);
		dist = dist.get(DistanceUnit.KILOMETER);
		assertEquals(0.001, dist.getDistance(), 0.00001);
		assertEquals(DistanceUnit.KILOMETER, dist.getUnit());
		
		Quantity q = Quantity.valueOf(1, SI.KILO(SI.METER));
		assertEquals(1000, q.intValue());
		assertEquals(1, q.intValue(SI.KILO(SI.METER)));
		
		q = Quantity.valueOf(1, NonSI.NAUTICAL_MILE);
		assertEquals(1.852, q.doubleValue(SI.KILO(SI.METER)), 0.01);
	}

}
