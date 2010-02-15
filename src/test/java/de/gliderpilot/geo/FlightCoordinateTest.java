/*
 * Created on 04.02.2005
 */
package de.gliderpilot.geo;

import java.util.Date;

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.units.NonSI;
import javax.units.SI;

import junit.framework.TestCase;

import org.jscience.physics.measures.Measure;

public class FlightCoordinateTest extends TestCase {

    public void testAttributes() {
        Measure<Length> alt = Measure.valueOf(1000, SI.METER);
        FlightCoordinateAttributes attributes = new FlightCoordinateAttributes();
        attributes.set(FlightCoordinateAttribute.BARO_ALTITUDE, alt);
        Measure<Dimensionless> enl = Measure.valueOf(1, NonSI.PERCENT);
        attributes.set(FlightCoordinateAttribute.ENL, enl);
        FlightCoordinate coord = new FlightCoordinate(new Date(), new GeoCoordinate(0, 0), attributes);
        assertSame(enl, coord.get(FlightCoordinateAttribute.ENL));
        assertSame(alt, coord.get(FlightCoordinateAttribute.BARO_ALTITUDE));
        assertNull(coord.get(FlightCoordinateAttribute.GPS_ALTITUDE));
    }

}
