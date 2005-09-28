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
public enum FlightCoordinateAttribute {
    /**
     * The GPS altitute
     */
    GPS_ALTITUDE(Length.class),
    /**
     * The Pressure altitude
     */
    BARO_ALTITUDE(Length.class),
    /**
     * The horizontal speed of the plane
     */
    SPEED(Velocity.class),
    /**
     * The vertical speed of the plane
     */
    VERTICAL_SPEED(Velocity.class),
    /**
     * The Engine level
     */
    ENL(Dimensionless.class);

    private Class clazz;

    <T extends Quantity> FlightCoordinateAttribute(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Checks if a given Quantity type is valid for this key.
     */
    public boolean isValid(Quantity q) {
        return clazz.isInstance(q);
    }

}
