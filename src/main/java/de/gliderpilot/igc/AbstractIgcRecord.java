/*
 * Created on 04.02.2005
 */
package de.gliderpilot.igc;

import javax.quantities.Angle;
import javax.units.NonSI;
import javax.units.SI;

import org.jscience.physics.measures.Measure;

import de.gliderpilot.geo.GeoCoordinate;

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
    protected GeoCoordinate parsePoint(String line, int index) {
        Measure<Angle> lat = parseLat(line, index);
        Measure<Angle> lon = parseLon(line, index);
        return new GeoCoordinate(lat, lon);
    }

    /**
     * Get the longitude at the specified position.
     * 
     * @param line
     * @param index
     * @return the longitude
     */
    private Measure<Angle> parseLon(String line, int index) {
        Measure<Angle> lon = Measure.valueOf(0, SI.RADIAN);
        int i = Integer.parseInt(line.substring(index + 8, index + 11));
        lon = lon.plus(Measure.valueOf(i, NonSI.DEGREE_ANGLE));
        i = Integer.parseInt(line.substring(index + 11, index + 13));
        lon = lon.plus(Measure.valueOf(i, NonSI.MINUTE_ANGLE));
        i = Integer.parseInt(line.substring(index + 13, index + 16));
        double d = (double) i / 1000;
        lon = lon.plus(Measure.valueOf(d, NonSI.MINUTE_ANGLE));
        boolean east = line.charAt(index + 16) == 'E';
        if (!east) {
            lon = lon.opposite();
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
    private Measure<Angle> parseLat(String line, int index) {
        Measure<Angle> lat = Measure.valueOf(0, SI.RADIAN);
        int i = Integer.parseInt(line.substring(index, index + 2));
        lat = lat.plus(Measure.valueOf(i, NonSI.DEGREE_ANGLE));
        i = Integer.parseInt(line.substring(index + 2, index + 4));
        lat = lat.plus(Measure.valueOf(i, NonSI.MINUTE_ANGLE));
        i = Integer.parseInt(line.substring(index + 4, index + 7));
        double d = (double) i / 1000;
        lat = lat.plus(Measure.valueOf(d, NonSI.MINUTE_ANGLE));
        boolean north = line.charAt(index + 7) == 'N';
        if (!north) {
            lat = lat.opposite();
        }
        return lat;
    }

}
