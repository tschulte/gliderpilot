/*
 * Created on 03.02.2005
 */
package de.gliderpilot.igc;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.units.NonSI;
import javax.units.Unit;

import junit.framework.TestCase;
import de.gliderpilot.geo.FlightCoordinate;
import de.gliderpilot.geo.FlightCoordinateAttribute;

public class BRecordTest extends TestCase {

    public void testRecord() throws ParseException {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        IRecord iRecord = new IRecord("I023638FXA3941ENL");
        BRecord bRecord = new BRecord(
                "B0953434804754N01116695EA0049600590033002", cal, iRecord);
        FlightCoordinate coordinate = bRecord.getCoordinate();
        assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(53, cal.get(Calendar.MINUTE));
        assertEquals(43, cal.get(Calendar.SECOND));
        assertEquals(2, (int) coordinate.get(FlightCoordinateAttribute.ENL)
                .longValue(Unit.ONE));
        assertEquals(48, (int) coordinate.getCoordinate().latitudeValue(
                NonSI.DEGREE_ANGLE));
        assertEquals(4, (int) coordinate.getCoordinate().latitudeValue(
                NonSI.MINUTE_ANGLE) - 48 * 60);
        assertEquals(11, (int) coordinate.getCoordinate().longitudeValue(
                NonSI.DEGREE_ANGLE));
        assertEquals(16, (int) coordinate.getCoordinate().longitudeValue(
                NonSI.MINUTE_ANGLE) - 11 * 60);
    }

    public void testRecordSW() throws ParseException {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        IRecord iRecord = new IRecord("I023638FXA3941ENL");
        BRecord bRecord = new BRecord(
                "B0953434804754S01116695WA0049600590033002", cal, iRecord);
        FlightCoordinate coordinate = bRecord.getCoordinate();
        assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(53, cal.get(Calendar.MINUTE));
        assertEquals(43, cal.get(Calendar.SECOND));
        assertEquals(2, (int) coordinate.get(FlightCoordinateAttribute.ENL)
                .longValue(Unit.ONE));
        assertEquals(-48, (int) coordinate.getCoordinate().latitudeValue(
                NonSI.DEGREE_ANGLE));
        assertEquals(-4, (int) coordinate.getCoordinate().latitudeValue(
                NonSI.MINUTE_ANGLE) + 48 * 60);
        assertEquals(-11, (int) coordinate.getCoordinate().longitudeValue(
                NonSI.DEGREE_ANGLE));
        assertEquals(-16, (int) coordinate.getCoordinate().longitudeValue(
                NonSI.MINUTE_ANGLE) + 11 * 60);
    }

}
