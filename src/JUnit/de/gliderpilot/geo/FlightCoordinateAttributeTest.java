/*
 * Created on 20.02.2005
 */
package de.gliderpilot.geo;

import junit.framework.TestCase;

import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;
import org.jscience.physics.units.SI;

public class FlightCoordinateAttributeTest extends TestCase {
    public void testIsValid() {
        Quantity q = Quantity.valueOf(1000, SI.METER);
        assertTrue(FlightCoordinateAttribute.BARO_ALTITUDE.isValid(q));
        q = Quantity.valueOf(1000, SI.VOLT);
        assertFalse(FlightCoordinateAttribute.BARO_ALTITUDE.isValid(q));
    }

}
