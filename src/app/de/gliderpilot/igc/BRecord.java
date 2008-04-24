/*
 * Created on 03.02.2005
 */
package de.gliderpilot.igc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.quantities.Dimensionless;
import javax.units.SI;
import javax.units.Unit;

import org.jscience.geography.coordinates.Altitude;
import org.jscience.physics.measures.Measure;

import de.gliderpilot.geo.FlightCoordinate;
import de.gliderpilot.geo.FlightCoordinateAttribute;
import de.gliderpilot.geo.FlightCoordinateAttributes;
import de.gliderpilot.geo.GeoCoordinate;

/**
 * The B-Record in an IGC-file contains one of the track points of the flight.
 * It contains the time the point was recorded, the latitude and longitude, GPS
 * and barographic altitude and maybe some additional information as defined in
 * the I-Record.
 * 
 * @author tobias
 */
class BRecord extends AbstractIgcRecord {

    private static SimpleDateFormat timeFormat;
    static {
        timeFormat = new SimpleDateFormat("HHmmss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private Calendar _cal;

    private String _record;

    private FlightCoordinate _coordinate;

    /**
     * Parse the given record of an igc file. If the iRecord parameter is non
     * null, its content is evaluated as well. The given calendar object must
     * contain the date that was given in the HFDTE record or of the last
     * coordinate and must be initialized to UTC.
     * <p>
     * <b>Note:</b> After the call to this constructor, cal's value will have
     * changed to the date of this B record.
     * 
     * @throws ParseException
     *             if the time could not be parsed.
     */
    public BRecord(String pRecord, Calendar pCal, IRecord pIRecord)
            throws ParseException {
        this._record = pRecord;
        this._cal = pCal;
        GeoCoordinate geoCoordinate = parsePoint(pRecord, 7);
        FlightCoordinateAttributes attributes = new FlightCoordinateAttributes();

        Date date = getDate();
        int alt = Integer.parseInt(pRecord.substring(25, 30));
        Altitude baroAlt = Altitude.valueOf(alt, SI.METER);
        attributes.set(FlightCoordinateAttribute.BARO_ALTITUDE, baroAlt);
        alt = Integer.parseInt(pRecord.substring(30, 35));
        Altitude gpsAlt = Altitude.valueOf(alt, SI.METER);
        attributes.set(FlightCoordinateAttribute.GPS_ALTITUDE, gpsAlt);
        if (pIRecord != null) {
            ExtensionIndex index = pIRecord.getIndex(IRecord.Extension.ENL);
            if (index != null) {
                String s = pRecord.substring(index.getStart(), index.getEnd());
                Measure<Dimensionless> enl = Measure.valueOf(Double
                        .parseDouble(s), Unit.ONE);
                attributes.set(FlightCoordinateAttribute.ENL, enl);
            }
        }
        _coordinate = new FlightCoordinate(date, geoCoordinate, attributes);
    }

    /**
     * Parse the record to get the date of the flight. Since the record itself
     * only contains the time, the date is taken from the header of the
     * IGC-file. To make sure we also get date wraps for flights recorded in
     * countries where a flight starts short before midnight UTC, the given
     * calendar object always reflects the date of the last parsed point.
     * 
     * <b>Note:</b> After calling this method, the given calendar object will
     * have changed.
     * 
     * @return the parsed date
     * @throws ParseException
     *             if the date could not be parsed
     */
    private Date getDate() throws ParseException {
        int millis = (int) timeFormat.parse(_record.substring(1, 7)).getTime();
        Date oldDate = _cal.getTime();
        resetCal();
        _cal.add(Calendar.MILLISECOND, millis);
        Date date = _cal.getTime();
        if (date.before(oldDate)) {
            resetCal();
            _cal.add(Calendar.DATE, 1);
            return getDate();
        }
        return _cal.getTime();
    }

    /**
     * Reset the given calendar to the start of it's current day.
     */
    private void resetCal() {
        _cal.set(Calendar.HOUR_OF_DAY, 0);
        _cal.set(Calendar.MINUTE, 0);
        _cal.set(Calendar.SECOND, 0);
        _cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Get the coordinate given by this B-record
     * 
     * @return the coordinate
     */
    public FlightCoordinate getCoordinate() {
        return _coordinate;
    }

}
