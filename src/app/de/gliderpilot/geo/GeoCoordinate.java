/*
 * Created on 08.10.2005
 */
package de.gliderpilot.geo;

import javax.quantities.Angle;
import javax.quantities.Length;
import javax.quantities.Quantity;
import javax.units.NonSI;
import javax.units.SI;

import javolution.lang.MathLib;

import org.jscience.geography.coordinates.LatLong;
import org.jscience.physics.measures.Measure;

/**
 * A geo coordinate is a coordinate somewhere on the earth. Coordinates are
 * encoded in WGS84.
 * 
 * @author tobias
 */
public final class GeoCoordinate extends LatLong {

    /**
     * Construct a new GeoCoordinate for the given latitude and longtude. Both
     * are given in decimal degrees.
     */
    public GeoCoordinate(double pLat, double pLon) {
        super(pLat, pLon, NonSI.DEGREE_ANGLE);
    }

    /**
     * Construct a new GeoCoordinate for the given latitude and longtude.
     */
    public GeoCoordinate(Quantity<Angle> pLat, Quantity<Angle> pLon) {
        super(pLat.doubleValue(SI.RADIAN), pLon.doubleValue(SI.RADIAN),
                SI.RADIAN);
    }

    /**
     * The coordinate is valid if both lon and lat are in the defined range.
     */
    public boolean isValid() {
        return Math.abs(latitudeValue(SI.RADIAN)) <= MathLib.HALF_PI
                && Math.abs(longitudeValue(SI.RADIAN)) <= MathLib.PI;
    }

    /**
     * Get the distance between this and the given geo-coordinate.
     * <p>
     * <b>Note:</b>The value is an approximation only, assuming the earth was a
     * sphere!
     * <p>
     * Formulary taken from <a
     * href="http://williams.best.vwh.net/avform.htm">Aviation Formulary </a>
     * <p>
     * The great circle distance d between two points with coordinates
     * {lat1,lon1} and {lat2,lon2} is given by <br>
     * <code>d=acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon1-lon2))<code>
     * <br>
     * and since one degree on a meridian are 60 NM, we multiply d with 60 NM.
     * 
     * @return the distance or null if one position is invalid
     */
    public Measure<Length> getDistanceTo(GeoCoordinate pCoord) {
        if (!isValid() || !pCoord.isValid()) {
            return null;
        }
        return GreatCircle.distanceBetween(this, pCoord);
    }

    /**
     * Get the true course to the given coordinate.
     * <p>
     * <b>Note:</b>The result is the course to steer at the moment. If you
     * follow this course to reach a coordinate far away and are not going north
     * or south or 90° or 270° at the equator, you will miss your target! So you
     * will have to continually update the course to steer. This method might be
     * used in a flight computer in the plane to get the true course to steer.
     * <p>
     * Formulary taken from <a
     * href="http://williams.best.vwh.net/avform.htm">Aviation Formulary </a>
     * <p>
     * The true course in radians is calculated by <br>
     * <code>tc=mod(atan2(sin(lon1-lon2)*cos(lat2),
     *     cos(lat1)*sin(lat2)-sin(lat1)*cos(lat2)*cos(lon1-lon2)), 2*pi)
     *     </code>
     * 
     * @param pCoord
     *            The target coordinate
     * @return the true course or null, if any of the given point is invalid
     */
    public Measure<Angle> trueCourseTo(GeoCoordinate pCoord) {
        if (pCoord == null || !pCoord.isValid() || !isValid()) {
            return null;
        }
        return GreatCircle.trueCourseBetween(this, pCoord);
    }

    /**
     * Get the course you have to steer to reach the endpoint when you don't
     * want to change the course all the time. This method can be used when you
     * want to create a flight plan or such.
     * 
     * @param pCoord
     * @return the average course to steer
     */
    public Measure<Angle> getAverageCourseTo(GeoCoordinate pCoord) {
        if (pCoord == null || !pCoord.isValid() || !isValid()) {
            return null;
        }
        return GreatCircle.averageCourseBetween(this, pCoord);
    }

}
