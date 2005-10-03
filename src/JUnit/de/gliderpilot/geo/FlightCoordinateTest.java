/*
 * Created on 04.02.2005
 */
package de.gliderpilot.geo;

import java.util.Date;

import junit.framework.TestCase;

import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;
import org.jscience.physics.units.SI;

public class FlightCoordinateTest extends TestCase {

    public void testAttributes() {
        Length alt = Quantity.valueOf(1000, SI.METER);
        FlightCoordinateAttributes attributes = new FlightCoordinateAttributes();
        attributes.set(FlightCoordinateAttribute.BARO_ALTITUDE, alt);
        Dimensionless enl = Quantity.valueOf(1, NonSI.PERCENT);
        attributes.set(FlightCoordinateAttribute.ENL, enl);
        Date date = new Date();
        FlightCoordinate coord = new FlightCoordinate(new GeoCoordinate(0, 0),
                date, attributes);
        assertSame(enl, coord.get(FlightCoordinateAttribute.ENL));
        assertSame(alt, coord.get(FlightCoordinateAttribute.BARO_ALTITUDE));
        assertNull(coord.get(FlightCoordinateAttribute.GPS_ALTITUDE));
    }

}
