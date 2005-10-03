/*
 * Created on 20.02.2005
 */
package de.gliderpilot.geo;

import java.util.HashMap;
import java.util.Map;

import org.jscience.physics.quantities.Quantity;

/**
 * This class is just a wrapper around a Map that does additional checking in
 * the setter.
 * 
 * @author tobias
 * 
 */
public class FlightCoordinateAttributes {

    /**
     * Contains all additional attributes that a track-coordinate might have.
     */
    private Map<FlightCoordinateAttribute, Quantity> attributes = new HashMap<FlightCoordinateAttribute, Quantity>();

    /**
     * Set the given attribute to the given value. The Value may be null to
     * remove the given attribute.
     * 
     * @throws IllegalArgumentException
     *             if the given quantity is not compatible to the type of
     *             attribute, i.e. setting a GPS_ALTITUDE of "5 V".
     */
    public <T extends Quantity> void set(
            FlightCoordinateAttribute<T> attribute, T value) {
        attributes.put(attribute, value);
    }

    /**
     * Get the specified attribute of this coordinate or null, if it was not set
     * before or set to null.
     */
    @SuppressWarnings("unchecked")
    public <T extends Quantity> T get(FlightCoordinateAttribute<T> attribute) {
        return (T)attributes.get(attribute);
    }

}
