/*
 * Created on 20.02.2005
 */
package de.gliderpilot.geo;

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.quantities.Quantity;
import javax.quantities.Velocity;

/**
 * Enumerates additional Attributes a coordinate might have.
 */
public final class FlightCoordinateAttribute<T extends Quantity> {

    /**
     * The GPS altitute
     */
    public static final FlightCoordinateAttribute<Length> GPS_ALTITUDE = new FlightCoordinateAttribute<Length>("GPS_ALTITUDE");

    /**
     * The Pressure altitude
     */
    public static final FlightCoordinateAttribute<Length> BARO_ALTITUDE = new FlightCoordinateAttribute<Length>("BARO_ALTITUDE");

    /**
     * The horizontal speed of the plane
     */
    public static final FlightCoordinateAttribute<Velocity> SPEED = new FlightCoordinateAttribute<Velocity>("SPEED");

    /**
     * The vertical speed of the plane
     */
    public static final FlightCoordinateAttribute<Velocity> VERTICAL_SPEED = new FlightCoordinateAttribute<Velocity>("VERTICAL_SPEED");

    /**
     * The Engine level
     */
    public static final FlightCoordinateAttribute<Dimensionless> ENL = new FlightCoordinateAttribute<Dimensionless>("ENL");

    private String _name;

    /**
     * Cannot directly be instantiated.
     * 
     * @param pName the name for the toString method
     */
    private FlightCoordinateAttribute(String pName) {
        _name = pName;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return _name;
    }

}
