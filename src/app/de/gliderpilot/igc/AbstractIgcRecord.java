/*
 * Created on 04.02.2005
 */
package de.gliderpilot.igc;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;

import de.gliderpilot.geo.GeoCoordinate;
import de.gliderpilot.geo.GeoCoordinateImpl;

/**
 * Base class for all IGC-records
 * 
 * @author tobias
 * 
 */
abstract class AbstractIgcRecord {

    /**
     * Parse the given line for a geocoord at the given index. A coordinate is
     * encoded in the form
     * 
     * <pre>
     *   
     *   DDMMmmmNDDDMMmmmE
     *   or
     *   DDMMmmmSDDDMMmmmW
     * </pre>
     */
    protected GeoCoordinateImpl parsePoint(String line, int index) {
        Angle lat = parseLat(line, index);
        Angle lon = parseLon(line, index);
        return new GeoCoordinateImpl(lat, lon);
    }

    /**
     * Get the longitude at the specified position.
     * 
     * @param line
     * @param index
     * @return the longitude
     */
    private Angle parseLon(String line, int index) {
        Angle lon = Angle.ZERO;
        int i = Integer.parseInt(line.substring(index + 8, index + 11));
        lon = (Angle) lon.plus(Quantity.valueOf(i, NonSI.DEGREE_ANGLE));
        i = Integer.parseInt(line.substring(index + 11, index + 13));
        lon = (Angle) lon.plus(Quantity.valueOf(i, NonSI.MINUTE_ANGLE));
        i = Integer.parseInt(line.substring(index + 13, index + 16));
        double d = (double) i / 1000;
        lon = (Angle) lon.plus(Quantity.valueOf(d, NonSI.MINUTE_ANGLE));
        boolean east = line.charAt(index + 16) == 'E';
        if (!east) {
            lon = (Angle) lon.opposite();
        }
        return lon;
    }

    /**
     * Get the latitude at the specified position.
     * 
     * @param line
     * @param index
     * @return the latitude
     */
    private Angle parseLat(String line, int index) {
        Angle lat = Angle.ZERO;
        int i = Integer.parseInt(line.substring(index, index + 2));
        lat = (Angle) lat.plus(Quantity.valueOf(i, NonSI.DEGREE_ANGLE));
        i = Integer.parseInt(line.substring(index + 2, index + 4));
        lat = (Angle) lat.plus(Quantity.valueOf(i, NonSI.MINUTE_ANGLE));
        i = Integer.parseInt(line.substring(index + 4, index + 7));
        double d = (double) i / 1000;
        lat = (Angle) lat.plus(Quantity.valueOf(d, NonSI.MINUTE_ANGLE));
        boolean north = line.charAt(index + 7) == 'N';
        if (!north) {
            lat = (Angle) lat.opposite();
        }
        return lat;
    }

}
