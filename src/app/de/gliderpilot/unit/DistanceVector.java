/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.unit;


/**
 * @author tobias
 */
public class DistanceVector extends Distance {
	
	private double course;

	/**
	 * 
	 * @param distance the distance
	 * @param unit the unit of the distance
	 * @param course the course in degrees
	 * if course is < 0 or > 360 it is transfered to the corresponding value
	 * between [0..360]
	 */
	public DistanceVector(double distance, DistanceUnit unit, double course) {
		super(distance, unit);
		if (course < 0) {
			course += 360;
		}
		if (course > 360) {
			course = course - (int) (course / 360) * 360;
		}
		this.course = course;
	}
	
	

	public DistanceVector getSum(DistanceVector vector) {
		if (getUnit() != vector.getUnit()) {
			vector = (DistanceVector) vector.get(getUnit());
		}
		double a = getDistance();
		double radA = Math.toRadians(getCourse());
		double aX = a*Math.sin(radA);
		double aY = a*Math.cos(radA);
		
		double b = vector.getDistance();
		double radB = Math.toRadians(vector.getCourse());
		double bX = b*Math.sin(radB);
		double bY = b*Math.cos(radB);
		
		double x = aX+bX;
		double y = aY+bY;

		double distance = Math.sqrt(sqr(x)+sqr(y));
		double rad = 0;
		if (distance != 0) {
			if (Math.abs(x) > Math.abs(y)) {
				rad = Math.asin(x/distance);
				if (y < 0) {
					rad = Math.PI - rad;
				}
			} else {
				rad = Math.acos(y/distance);
				if (x < 0) {
					rad *= -1;
				}
			}
		}
		double grad = Math.toDegrees(rad);
		DistanceVector vect = new DistanceVector(distance, getUnit(), grad);
		return vect;
	}
	
	private double sqr(double a) {
		return Math.pow(a, 2);
	}

	public double getCourse() {
		return course;
	}

	/**
	 * Get a DistanceVector object with the given unit
	 * @param unit the Unit the returned Object uses
	 * @return a DistanceUnit object representing the same distance but with the 
	 * given Unit (if unit == getUnit(); returns this)
	 */
	public Distance get(DistanceUnit unit) {
		if (getUnit() == unit) {
			return this;
		}
		Distance dist = super.get(unit);
		DistanceVector vector = new DistanceVector(dist.getDistance(), unit, getCourse());
		return vector;
	}
	
	public DistanceVector getXAmount() {
		double xAmount = Math.abs(getDistance()*Math.sin(Math.toRadians(getCourse())));
		return new DistanceVector(xAmount, getUnit(), getCourse()<180?90:270);
	}

	public DistanceVector getYAmount() {
		double yAmount = Math.abs(getDistance()*Math.cos(Math.toRadians(getCourse())));
		double direction = 0;
		if (getCourse() < 270 && getCourse() >= 90) {
			direction = 180;
		}
		return new DistanceVector(yAmount, getUnit(), direction);
	}

}
