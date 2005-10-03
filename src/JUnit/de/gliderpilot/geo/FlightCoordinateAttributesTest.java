/*
 * Created on 20.02.2005
 */
package de.gliderpilot.geo;

import junit.framework.TestCase;

import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;
import org.jscience.physics.units.SI;

public class FlightCoordinateAttributesTest extends TestCase {

    FlightCoordinateAttributes attributes;

    @Override
    protected void setUp() throws Exception {
        attributes = new FlightCoordinateAttributes();
    }

    public void testSetAttributes() {
        Length in = Quantity.valueOf(1000, SI.METER);
        attributes.set(FlightCoordinateAttribute.BARO_ALTITUDE, in);
        Length out = attributes.get(FlightCoordinateAttribute.BARO_ALTITUDE);
        assertSame(in, out);
        out = attributes.get(FlightCoordinateAttribute.GPS_ALTITUDE);
        assertNull(out);
        Dimensionless in2 = Quantity.valueOf(1, NonSI.PERCENT);
        attributes.set(FlightCoordinateAttribute.ENL, in2);
    }

}
