/*
 * Created on 20.02.2005
 */
package de.gliderpilot.geo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.quantities.Quantity;

/**
 * This class is just a wrapper around a Map that does additional checking in
 * the setter.
 * 
 * @author tobias
 * 
 */
public final class FlightCoordinateAttributes {

    /**
     * Contains all additional attributes that a track-coordinate might have.
     */
    private Map<FlightCoordinateAttribute, Quantity> _attributes = new HashMap<FlightCoordinateAttribute, Quantity>();

    /**
     * Set the given attribute to the given value. The Value may be null to
     * remove the given attribute.
     * 
     * @throws IllegalArgumentException
     *             if the given quantity is not compatible to the type of
     *             attribute, i.e. setting a GPS_ALTITUDE of "5 V".
     */
    public <T extends Quantity> void set(
            FlightCoordinateAttribute<T> attribute, Quantity<T> value) {
        _attributes.put(attribute, value);
    }

    /**
     * Get the specified attribute of this coordinate or null, if it was not set
     * before or set to null.
     */
    @SuppressWarnings("unchecked")
    public <T extends Quantity> Quantity<T> get(
            FlightCoordinateAttribute<T> attribute) {
        return _attributes.get(attribute);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Entry<FlightCoordinateAttribute, Quantity> entry : _attributes
                .entrySet()) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
        }
        sb.append("]");
        return sb.toString();
    }
}
