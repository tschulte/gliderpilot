/*
 * Created on 08.10.2005
 */
package de.gliderpilot.geo;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;

/**
 * A geo coordinate is a coordinate somewhere on the earth. 
 * 
 * @author tobias
 */
public interface GeoCoordinate {

    /**
     * Check if the coordinate is valid.
     */
    public abstract boolean isValid();

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
    public abstract Length getDistanceTo(GeoCoordinate coord);

    /**
     * Get the true course to the given coordinate.
     * <p>
     * <b>Note:</b>The result is the course to steer at the moment. If you
     * follow this course to reach a coordinate far away and are not going north
     * or south or 90° or 270° at the equator, you will miss your target! So you
     * will have to continually update the course to steer. This method might be
     * used in a flight computer in the plane to get the true course to steer.
     * 
     * @param coord
     *            The target coordinate
     * @return the true course or null, if any of the given point is invalid
     */
    public abstract Angle trueCourseTo(GeoCoordinate coord);

    /**
     * Get the course you have to steer to reach the endpoint when you don't
     * want to change the course all the time. This method can be used when you
     * want to create a flight plan or such.
     * 
     * @param coord
     * @return the average course to steer
     */
    public abstract Angle getAverageCourseTo(GeoCoordinate coord);

}
