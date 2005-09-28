/*
 * Created on 03.02.2005
 */
package de.gliderpilot.igc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.SI;

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

    private Calendar cal;

    private String record;

    private FlightCoordinate coordinate;

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
    public BRecord(String record, Calendar cal, IRecord iRecord)
            throws ParseException {
        this.record = record;
        this.cal = cal;
        GeoCoordinate geoCoordinate = parsePoint(record, 7);
        Date date = getDate();
        FlightCoordinateAttributes attributes = new FlightCoordinateAttributes();
        int alt = Integer.parseInt(record.substring(25, 30));
        Length baroAlt = (Length) Quantity.valueOf(alt, SI.METER);
        attributes.set(FlightCoordinateAttribute.BARO_ALTITUDE, baroAlt);
        alt = Integer.parseInt(record.substring(30, 35));
        Length gpsAlt = (Length) Quantity.valueOf(alt, SI.METER);
        attributes.set(FlightCoordinateAttribute.GPS_ALTITUDE, gpsAlt);
        if (iRecord != null) {
            ExtensionIndex index = iRecord.getIndex(IRecord.Extension.ENL);
            if (index != null) {
                String s = record.substring(index.getStart(), index.getEnd());
                Quantity enl = Quantity.valueOf(s);
                attributes.set(FlightCoordinateAttribute.ENL, enl);
            }
        }
        this.coordinate = new FlightCoordinate(geoCoordinate, date, attributes);
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
        int millis = (int) timeFormat.parse(record.substring(1, 7)).getTime();
        Date oldDate = cal.getTime();
        resetCal();
        cal.add(Calendar.MILLISECOND, millis);
        Date date = cal.getTime();
        if (date.before(oldDate)) {
            resetCal();
            cal.add(Calendar.DATE, 1);
            return getDate();
        }
        return cal.getTime();
    }

    /**
     * Reset the given calendar to the start of it's current day.
     */
    private void resetCal() {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Get the coordinate given by this B-record
     * 
     * @return
     */
    public FlightCoordinate getCoordinate() {
        return coordinate;
    }

}
