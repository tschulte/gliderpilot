/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.unit;

import java.text.NumberFormat;

/**
 * Represents a distance. The distance has a value and a unit.
 * @author tobias
 */
public class Distance {
	
	private DistanceUnit unit;
	private double distance;
	
	/**
	 * Create a new Distance of a unit.
	 * @param distance the value of the distance
	 * @param unit the unit of the distance
	 */
	public Distance(double distance, DistanceUnit unit) {
		this.distance = distance;
		this.unit = unit;
	}
	
	/**
	 * Get the sum of this Distance and the given Distance.
	 * @param dist the Distance to be added to this Distance
	 * @return the Distance containing the sum
	 */
	public Distance getSum(Distance dist) {
		if (unit != dist.unit) {
			dist = dist.get(unit);
		}
		return new Distance(distance+dist.distance, unit);
	}
	
	public String toString() {
		NumberFormat format = unit.getDefaultFormat();
		return format.format(distance) + " " + unit;
	}

	/**
	 * Get the value of the distance.
	 * @return the value of the distance without any calculation.
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Get a Distance object with the given unit
	 * @param unit the Unit the returned Object uses
	 * @return a Distance object representing the same distance but with the 
	 * given Unit (if unit == getUnit(); returns this)
	 */
	public Distance get(DistanceUnit unit) {
		if (unit == this.unit) {
			return this;
		}
		double distance = this.distance*this.unit.getConversionFactor(unit);
		return new Distance(distance, unit);
	}

	/**
	 * Get the unit of this Distance.
	 * @return the Unit this Distance uses
	 */
	public DistanceUnit getUnit() {
		return unit;
	}

}
