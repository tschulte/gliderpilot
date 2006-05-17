/*
 * Created on 04.02.2005
 */
package de.gliderpilot.geo;

import java.util.Date;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;

/**
 * The FlightCoordinate is a geo coordinate with additional attributes like
 * altitude, speed, vertical speed, enl and a date.
 * 
 * @author tobias
 * 
 */
public final class FlightCoordinateImpl implements FlightCoordinate {

    /**
     * The date the coordinate was recorded.
     */
    private Date _date;
    
    /**
     * The Geocoordinate.
     */
    private GeoCoordinateImpl _coord;

    /**
     * Holds the additional attributes of the coordinate.
     */
    private FlightCoordinateAttributes _attributes;

    /**
     * Construct a FlightCoordinate with the same lat and lon as the given
     * coordinate, that was recorded at the given date and has the given
     * additional information.
     */
    public FlightCoordinateImpl(GeoCoordinateImpl pCoord, Date pDate,
            FlightCoordinateAttributes pAttributes) {
        _coord = pCoord;
        _date = pDate;
        _attributes = pAttributes;
    }

    /**
     * Get the specified attribute of this coordinate or null, if it was not set
     * before or set to null.
     */
    public <Q extends Quantity> Q get(FlightCoordinateAttribute<Q> attribute) {
        return _attributes.get(attribute);
    }

    /**
     * Get the date the coordinate was recorded.
     */
    public Date getDate() {
        return _date;
    }

    public boolean isValid() {
        return _coord.isValid();
    }

    public Length getDistanceTo(GeoCoordinate pCoord) {
        return _coord.getDistanceTo(pCoord);
    }

    public Angle getLat() {
        return _coord.getLat();
    }

    public Angle getLon() {
        return _coord.getLon();
    }

    public Angle trueCourseTo(GeoCoordinate pCoord) {
        return _coord.trueCourseTo(pCoord);
    }

    public Angle getAverageCourseTo(GeoCoordinate pCoord) {
        return _coord.getAverageCourseTo(pCoord);
    }

}
