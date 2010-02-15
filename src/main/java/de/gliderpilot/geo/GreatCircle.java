/*
 * Created on 05.08.2006
 */

package de.gliderpilot.geo;

import static java.lang.Math.acos;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import javax.quantities.Angle;
import javax.quantities.Length;
import javax.units.NonSI;
import javax.units.SI;

import org.jscience.geography.coordinates.Coordinates;
import org.jscience.geography.coordinates.LatLong;
import org.jscience.physics.measures.Measure;

/**
 * Methods for calculating great circle and other distances on the sphere and
 * ellipsoid.
 * <p>
 * Spherical equations taken from John Synder's <i>Map Projections --A Working
 * Manual </i>, pp29-31. <br>
 * Latitude/longitude arguments must be in valid radians: -PI&lt;=lambda&lt;PI,
 * -PI/2&lt;=phi&lt;=PI/2
 */
public class GreatCircle {

    // cannot construct
    private GreatCircle() {
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
    @SuppressWarnings("unchecked")
    public static Measure<Length> distanceBetween(Coordinates pCoord1,
            Coordinates pCoord2) {
        LatLong latLong1 = (LatLong) pCoord1.getCoordinateReferenceSystem()
                .getConverterTo(LatLong.CRS).convert(pCoord1);
        LatLong latLong2 = (LatLong) pCoord2.getCoordinateReferenceSystem()
                .getConverterTo(LatLong.CRS).convert(pCoord2);
        double lat1 = latLong1.latitudeValue(SI.RADIAN);
        double lon1 = latLong1.longitudeValue(SI.RADIAN);
        double lat2 = latLong2.latitudeValue(SI.RADIAN);
        double lon2 = latLong2.longitudeValue(SI.RADIAN);

        double sphericalDistance = acos(sin(lat1) * sin(lat2) + cos(lat1)
                * cos(lat2) * cos(lon1 - lon2));

        double distance = Math.toDegrees(sphericalDistance) * 60;
        return Measure.valueOf(distance, NonSI.NAUTICAL_MILE);
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
     * <code>tc=mod(atan2(sin(lon2-lon1)*cos(lat2),
     *     cos(lat1)*sin(lat2)-sin(lat1)*cos(lat2)*cos(lon2-lon1)), 2*pi)
     *     </code>
     * 
     * @param pCoord1
     *            The source coordinate
     * @param pCoord2
     *            The target coordinate
     * @return the true course or null, if any of the given point is invalid
     */
    @SuppressWarnings("unchecked")
    public static Measure<Angle> trueCourseBetween(Coordinates pCoord1,
            Coordinates pCoord2) {
        LatLong latLong1 = (LatLong) pCoord1.getCoordinateReferenceSystem()
                .getConverterTo(LatLong.CRS).convert(pCoord1);
        LatLong latLong2 = (LatLong) pCoord2.getCoordinateReferenceSystem()
                .getConverterTo(LatLong.CRS).convert(pCoord2);
        double lat1 = latLong1.latitudeValue(SI.RADIAN);
        double lon1 = latLong1.longitudeValue(SI.RADIAN);
        double lat2 = latLong2.latitudeValue(SI.RADIAN);
        double lon2 = latLong2.longitudeValue(SI.RADIAN);
        double tc = atan2(sin(lon2 - lon1) * cos(lat2), cos(lat1) * sin(lat2)
                - sin(lat1) * cos(lat2) * cos(lon2 - lon1));
        Measure<Angle> course = Measure.valueOf(tc, SI.RADIAN);
        return Course.bound(course);
    }

    /**
     * Get the course you have to steer to reach the endpoint when you don't
     * want to change the course all the time. This method can be used when you
     * want to create a flight plan or such.
     * 
     * @param pCoord1
     *            The source coordinate
     * @param pCoord2
     *            The target coordinate
     * @return the average course to steer
     */
    public static Measure<Angle> averageCourseBetween(Coordinates pCoord1,
            Coordinates pCoord2) {
        Measure<Angle> trueCourseInitial = trueCourseBetween(pCoord1, pCoord2);
        Measure<Angle> trueCourseEnd = Course.inverse(trueCourseBetween(
                pCoord2, pCoord1));
        Measure<Angle> diff = Course.diff(trueCourseInitial, trueCourseEnd);
        return Course.bound(trueCourseInitial.plus(diff.divide(2)));
    }

}
