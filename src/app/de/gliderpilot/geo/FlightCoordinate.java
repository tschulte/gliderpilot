/*
 * Created on 04.02.2005
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
public class FlightCoordinate extends GeoCoordinate {

    /**
     * The date the coordinate was recorded.
     */
    private Date date;

    /**
     * Holds the additional attributes of the coordinate.
     */
    private FlightCoordinateAttributes attributes;

    /**
     * Construct a FlightCoordinate with the same lat and lon as the given
     * coordinate, that was recorded at the given date and has the given
     * additional information.
     */
    public FlightCoordinate(GeoCoordinate coord, Date date,
            FlightCoordinateAttributes attributes) {
        super(coord.getLat(), coord.getLon());
        this.date = date;
        this.attributes = attributes;
    }

    /**
     * Get the specified attribute of this coordinate or null, if it was not set
     * before or set to null.
     */
    public <Q extends Quantity> Q get(FlightCoordinateAttribute<Q> attribute) {
        return attributes.get(attribute);
    }

    /**
     * Get the date the coordinate was recorded.
     */
    public Date getDate() {
        return date;
    }

}
