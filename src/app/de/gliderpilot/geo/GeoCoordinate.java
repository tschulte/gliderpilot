/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.geo;

import de.gliderpilot.unit.Distance;
import de.gliderpilot.unit.DistanceUnit;
import de.gliderpilot.unit.DistanceVector;

/**
 * @author tobias
 */
public class GeoCoordinate {
	private static final Distance EARTH_RADIUS = new Distance(21600/2/Math.PI, DistanceUnit.NAUTICAL_MILE); // due to ICAO 6371 km
	private static final double INVALID_LON = 181;
	private static final double INVALID_LAT = 91;
	private static final double MAX_LON = 180;
	private static final double MAX_LAT = 90;

	private double lat = INVALID_LAT;
	private double lon = INVALID_LON;

	public boolean isValid() {
		return Math.abs(lat) <= MAX_LAT && Math.abs(lon) <= MAX_LON;
	}
	
	public GeoCoordinate(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
//	public DistanceVector getDistanceTo(GeoCoordinate coord) {
//		double radLat = Math.toRadians(lat);
//		double sinLat = Math.sin(radLat);
//		double cosLat = Math.cos(radLat);
//		double radDestLat = Math.toRadians(coord.lat);
//		double sinDestLat = Math.sin(radDestLat);
//		double cosDestLat = Math.cos(radDestLat);
//		double radLonMinusDestLon = Math.toRadians(lon - coord.lon);
//		double cosLonMinusDestLon= Math.cos(radLonMinusDestLon);
//		double distance =
//			Math.acos((sinLat * sinDestLat) + (cosLat * cosDestLat * cosLonMinusDestLon))
//				* EARTH_RADIUS.getDistance();
//		if (java.lang.Double.isNaN(distance)) return null;
//		double course = 0;
//		if (distance != 0) {
//			GeoCoordinate center = new GeoCoordinate((lat+coord.lat)/2, (lon+coord.lon)/2);
//			double latDiff = coord.lat - lat;
//			double lonDiff = coord.lon - lon;
//			lonDiff = lonDiff * Math.cos(Math.toRadians(center.lat));
//			double degreeDistance = Math.sqrt(Math.pow(latDiff,2)+Math.pow(lonDiff,2));
//			double radCourse;
//			if (Math.abs(latDiff) > Math.abs(lonDiff)) {
//				radCourse = Math.acos(latDiff/degreeDistance);
//				if (lonDiff < 0) {
//					radCourse *= -1;
//				}
//			} else {
//				radCourse = Math.asin(lonDiff/degreeDistance);
//				if (latDiff < 0) {
//					if (radCourse > 0) {
//						radCourse += Math.PI/2;
//					} else {
//						radCourse -= Math.PI/2;
//					}
//				}
//			}
//			course = Math.toDegrees(radCourse);
//		}
//		return new DistanceVector(distance, EARTH_RADIUS.getUnit(), course);
//	}

	public DistanceVector getDistanceTo(GeoCoordinate coord) {
		GeoCoordinate center = new GeoCoordinate((lat+coord.lat)/2, (lon+coord.lon)/2);
		double dx = coord.lon - lon;
		double x = 0;
		if (Math.abs(center.lat) < 90) { 
			x = dx * 60 * Math.cos(Math.toRadians(center.lat));
		}
		double dy = coord.lat - lat;
		double y = dy * 60;
		DistanceVector vx = new DistanceVector(Math.abs(x), DistanceUnit.NAUTICAL_MILE, (x<0?270:90));
		DistanceVector vy = new DistanceVector(Math.abs(y), DistanceUnit.NAUTICAL_MILE, (y<0?180:0));
		return vx.getSum(vy);
	}
	
	public GeoCoordinate getCoordIn(DistanceVector vector) {
		vector = (DistanceVector) vector.get(DistanceUnit.NAUTICAL_MILE);
		DistanceVector vx = vector.getXAmount();
		double dx = vx.getDistance()/60*(vx.getCourse()>180?-1:1)*Math.cos(Math.toRadians(lat));
		DistanceVector vy = vector.getYAmount();		
		double dy = vy.getDistance()/60*(vy.getCourse()>90?-1:1);
		return new GeoCoordinate(lat+dy,lon+dx);
	}
	/**
	 * @return
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @return
	 */
	public double getLon() {
		return lon;
	}

}
