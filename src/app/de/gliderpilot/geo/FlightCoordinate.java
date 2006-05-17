/*
 * Created on 08.10.2005
 */
package de.gliderpilot.geo;

import java.util.Date;

import org.jscience.physics.quantities.Quantity;

/**
 * The FlightCoordinate is a geo coordinate with additional attributes like
 * altitude, speed, vertical speed, enl and a date.
 * 
 * @author tobias
 * 
 */
public interface FlightCoordinate extends GeoCoordinate {

    /**
     * Get the specified attribute of this coordinate or null, if it was not set
     * before or set to null.
     */
    public abstract <Q extends Quantity> Q get(
            FlightCoordinateAttribute<Q> attribute);

    /**
     * Get the date the coordinate was recorded.
     */
    public abstract Date getDate();

}
