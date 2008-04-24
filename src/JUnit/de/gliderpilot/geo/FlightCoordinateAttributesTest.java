/*
 * Created on 20.02.2005
 */
package de.gliderpilot.geo;

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.quantities.Quantity;
import javax.quantities.Scalar;
import javax.units.NonSI;
import javax.units.SI;

import junit.framework.TestCase;

import org.jscience.physics.measures.Measure;

public class FlightCoordinateAttributesTest extends TestCase {

    FlightCoordinateAttributes attributes;

    @Override
    protected void setUp() throws Exception {
        attributes = new FlightCoordinateAttributes();
    }

    public void testSetAttributes() {
        Quantity<Length> in = Measure.valueOf(1000, SI.METER);
        attributes.set(FlightCoordinateAttribute.BARO_ALTITUDE, in);
        Quantity<Length> out = attributes
                .get(FlightCoordinateAttribute.BARO_ALTITUDE);
        assertSame(in, out);
        out = attributes.get(FlightCoordinateAttribute.GPS_ALTITUDE);
        assertNull(out);
        Quantity<Dimensionless> in2 = new Scalar<Dimensionless>(1,
                NonSI.PERCENT);
        attributes.set(FlightCoordinateAttribute.ENL, in2);
    }

}
