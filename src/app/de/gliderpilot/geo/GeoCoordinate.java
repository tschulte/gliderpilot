/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import static org.jscience.physics.units.NonSI.DEGREE_ANGLE;
import static org.jscience.physics.units.SI.KILO;
import static org.jscience.physics.units.SI.METER;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;

/**
 * A geo coordinate is a coordinate somewhere on the earth. Coordinates are
 * encoded in WGS84.
 * 
 * @author tobias
 */
public class GeoCoordinate {

    private static final Length EARTH_RADIUS = (Length) Quantity.valueOf(
            6366.71, KILO(METER)); // due to ICAO 6371 km

    private static final Angle MAX_LON = (Angle) Quantity.valueOf(180,
            DEGREE_ANGLE);

    private static final Angle MAX_LAT = (Angle) Quantity.valueOf(90,
            DEGREE_ANGLE);

    /**
     * This is the latitude of the coordinate in the range -90° to +90° with
     * negative values representing southern hemisphere and positive values
     * representing northern hemisphere.
     */
    private Angle lat;

    /**
     * This is the longitude of the coordinate in the range -180° to +180° with
     * negative values representing western hemisphere and positive values
     * representing eastern hemisphere.
     */
    private Angle lon;

    /**
     * Construct a new GeoCoordinate for the given latitude and longtude. Both
     * are given in decimal degrees.
     */
    public GeoCoordinate(double lat, double lon) {
        this.lat = (Angle) Quantity.valueOf(lat, DEGREE_ANGLE);
        this.lon = (Angle) Quantity.valueOf(lon, DEGREE_ANGLE);
    }

    /**
     * Construct a new GeoCoordinate for the given latitude and longtude.
     */
    public GeoCoordinate(Angle lat, Angle lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * The coordinate is valid if both lon and lat are in the defined range.
     */
    public boolean isValid() {
        return lat != null && lon != null && lat.abs().compareTo(MAX_LAT) <= 0
                && lon.abs().compareTo(MAX_LON) <= 0;
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
    public Length getDistanceTo(GeoCoordinate coord) {
        if (!isValid() || !coord.isValid()) {
            return null;
        }
        Angle lat1 = getLat();
        Angle lat2 = coord.getLat();
        Angle lon1 = getLon();
        Angle lon2 = coord.getLon();
        Angle deltaLon = (Angle) lon2.minus(lon1);
        Angle circularDistance;
        circularDistance = ((Dimensionless) lat1.sine().times(lat2.sine())
                .plus(lat1.cos().times(lat2.cos()).abs().times(deltaLon.cos())))
                .acos();
        return Length.valueOf(EARTH_RADIUS, circularDistance);
    }

    /**
     * Get the latidute of this GeoCoordinate in decimal degrees.
     */
    public Angle getLat() {
        return lat;
    }

    /**
     * Get the longitude of this GeoCoordinate in decimal degrees.
     */
    public Angle getLon() {
        return lon;
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
     * @param coord
     *            The target coordinate
     * @return the true course or null, if any of the given point is invalid
     */
    public Angle trueCourseTo(GeoCoordinate coord) {
        if (coord == null || !coord.isValid() || !isValid()) {
            return null;
        }
        Angle lat1 = getLat();
        Angle lat2 = coord.getLat();
        Angle lon1 = getLon();
        Angle lon2 = coord.getLon();
        Angle deltaLon = (Angle) lon2.minus(lon1);

        Quantity q1 = deltaLon.sine().times(lat2.cos());
        Quantity q2 = lat1.cos().times(lat2.sine());
        Quantity q3 = lat1.sine().times(lat2.cos()).times(deltaLon.cos());
        Quantity q4 = q2.minus(q3);
        Angle angle = Angle.atan2(q1, q4);
        return Course.bound(angle);
    }

    /**
     * Get the course you have to steer to reach the endpoint when you don't
     * want to change the course all the time. This method can be used when you
     * want to create a flight plan or such.
     * 
     * @param coord
     * @return the average course to steer
     */
    public Angle getAverageCourseTo(GeoCoordinate coord) {
        if (coord == null || !coord.isValid() || !isValid()) {
            return null;
        }
        Angle trueCourseInitial = trueCourseTo(coord);
        Angle trueCourseEnd = Course.inverse(coord.trueCourseTo(this));
        Angle diff = Course.diff(trueCourseInitial, trueCourseEnd);
        return Course.bound((Angle) trueCourseInitial.plus(diff.divide(2)));
    }

}
