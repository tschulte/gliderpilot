/*
 * Created on 04.02.2005
 */
package de.gliderpilot.geo;

import java.util.Date;

import javax.quantities.Quantity;

/**
 * The FlightCoordinate is a geo coordinate with additional attributes like
 * altitude, speed, vertical speed, enl and a date.
 * 
 * @author tobias
 * 
 */
public final class FlightCoordinate {

    /**
     * The date the coordinate was recorded.
     */
    private Date _date;

    /**
     * The Geocoordinate.
     */
    private GeoCoordinate _coordinate;

    /**
     * Holds the additional attributes of the coordinate.
     */
    private FlightCoordinateAttributes _attributes;

    /**
     * Construct a FlightCoordinate with the same lat and lon as the given
     * coordinate, that was recorded at the given date and has the given
     * additional information.
     * @param pDate the date the coordinate was recorded
     * @param pCoord the coordinate
     * @param pAttributes the attributes
     */
    public FlightCoordinate(Date pDate, GeoCoordinate pCoord,
            FlightCoordinateAttributes pAttributes) {
        _date = pDate;
        _coordinate = pCoord;
        _attributes = pAttributes;
    }

    /**
     * Get the specified attribute of this coordinate or null, if it was not set
     * before or set to null.
     * @param attribute the attribute to retrieve
     * @return the value of the attribute
     */
    public <Q extends Quantity> Quantity<Q> get(
            FlightCoordinateAttribute<Q> attribute) {
        return _attributes.get(attribute);
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return _date;
    }

    /**
     * @return the coordinate
     */
    public GeoCoordinate getCoordinate() {
        return _coordinate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return _date.toString() + ": " + _coordinate.toString() + "["
                + _attributes.toString() + "]";
    }

}
