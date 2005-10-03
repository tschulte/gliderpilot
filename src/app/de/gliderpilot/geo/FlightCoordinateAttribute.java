/*
 * Created on 20.02.2005
 */
package de.gliderpilot.geo;

import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.quantities.Velocity;

/**
 * Enumerates additional Attributes a coordinate might have.
 */
public class FlightCoordinateAttribute<T extends Quantity> {

    /**
     * The GPS altitute
     */
    public static final FlightCoordinateAttribute<Length> GPS_ALTITUDE = new FlightCoordinateAttribute<Length>();

    /**
     * The Pressure altitude
     */
    public static final FlightCoordinateAttribute<Length> BARO_ALTITUDE = new FlightCoordinateAttribute<Length>();

    /**
     * The horizontal speed of the plane
     */
    public static final FlightCoordinateAttribute<Velocity> SPEED = new FlightCoordinateAttribute<Velocity>();

    /**
     * The vertical speed of the plane
     */
    public static final FlightCoordinateAttribute<Velocity> VERTICAL_SPEED = new FlightCoordinateAttribute<Velocity>();

    /**
     * The Engine level
     */
    public static final FlightCoordinateAttribute<Dimensionless> ENL = new FlightCoordinateAttribute<Dimensionless>();

    private <T extends Quantity> FlightCoordinateAttribute() {
    }

}
