/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.geo;

import static org.jscience.physics.units.NonSI.DEGREE_ANGLE;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Length;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;

/**
 * @author tobias
 */
public class GeoCoordinateTest extends AbstractJadeTest {

    public void testIsValid() {
        assertTrue(new GeoCoordinate(-90, -180).isValid());
        assertTrue(new GeoCoordinate(-90, 0).isValid());
        assertTrue(new GeoCoordinate(-90, 180).isValid());

        assertTrue(new GeoCoordinate(0, -180).isValid());
        assertTrue(new GeoCoordinate(0, 0).isValid());
        assertTrue(new GeoCoordinate(0, 180).isValid());

        assertTrue(new GeoCoordinate(90, -180).isValid());
        assertTrue(new GeoCoordinate(90, 0).isValid());
        assertTrue(new GeoCoordinate(90, 180).isValid());

        assertFalse(new GeoCoordinate(-91, 0).isValid());
        assertFalse(new GeoCoordinate(91, 0).isValid());
        assertFalse(new GeoCoordinate(0, -181).isValid());
        assertFalse(new GeoCoordinate(0, 181).isValid());
    }

    public void testDistanceInvalidPos() {
        assertNull(new GeoCoordinate(0, 0).getDistanceTo(new GeoCoordinate(91,
                0)));
        assertNull(new GeoCoordinate(91, 0).getDistanceTo(new GeoCoordinate(0,
                0)));
    }

    public void testDistance() {
        Length l = new GeoCoordinate(0, 0)
                .getDistanceTo(new GeoCoordinate(1, 0));
        assertNotNull(l);
        assertEquals(Quantity.valueOf(60, NonSI.NAUTICAL_MILE), l);
    }

    public void testDistancePoleToPole() {
        Length l = new GeoCoordinate(-90, 0).getDistanceTo(new GeoCoordinate(
                90, 0));
        assertNotNull(l);
        assertEquals(Quantity.valueOf(10800, NonSI.NAUTICAL_MILE), l);
    }

    public void testDistancePoleToPoleLonSwitch() {
        Length l = new GeoCoordinate(-90, 0).getDistanceTo(new GeoCoordinate(
                90, 180));
        assertNotNull(l);
        assertEquals(Quantity.valueOf(10800, NonSI.NAUTICAL_MILE), l);
    }

    public void testDistanceEquator() {
        Length l = new GeoCoordinate(0, 0).getDistanceTo(new GeoCoordinate(0,
                180));
        assertNotNull(l);
        assertEquals(Quantity.valueOf(10800, NonSI.NAUTICAL_MILE), l);
    }

    public void testShortestDistance() {
        Length l1 = new GeoCoordinate(0, 0).getDistanceTo(new GeoCoordinate(0,
                179));
        Length l2 = new GeoCoordinate(0, 0).getDistanceTo(new GeoCoordinate(0,
                -179));
        assertEquals(l1, l2);
    }

    public void testTrueCourseTo() {
        Angle a = new GeoCoordinate(0, 0).trueCourseTo(null);
        assertNull(a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(91, 0));
        assertNull(a);
        a = new GeoCoordinate(91, 0).trueCourseTo(new GeoCoordinate(0, 0));
        assertNull(a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(1, 0));
        assertEquals(Quantity.valueOf(0, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(1, 1));
        assertEquals(Quantity.valueOf(45, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(0, 1));
        assertEquals(Quantity.valueOf(90, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(-1, 1));
        assertEquals(Quantity.valueOf(135, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(-1, 0));
        assertEquals(Quantity.valueOf(180, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(-1, -1));
        assertEquals(Quantity.valueOf(225, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(0, -1));
        assertEquals(Quantity.valueOf(270, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(1, -1));
        assertEquals(Quantity.valueOf(315, NonSI.DEGREE_ANGLE), a);
    }

    public void testAverageCourseTo() {
        Angle a = new GeoCoordinate(0, 0).getAverageCourseTo(null);
        assertNull(a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(91, 0));
        assertNull(a);
        a = new GeoCoordinate(91, 0)
                .getAverageCourseTo(new GeoCoordinate(0, 0));
        assertNull(a);
        a = new GeoCoordinate(0, 0).getAverageCourseTo(new GeoCoordinate(1, 0));
        assertEquals(Quantity.valueOf(0, DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).getAverageCourseTo(new GeoCoordinate(1, 1));
        assertEquals(Quantity.valueOf(45, DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).getAverageCourseTo(new GeoCoordinate(0, 1));
        assertEquals(Quantity.valueOf(90, DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(-1, 1));
        assertEquals(Quantity.valueOf(135, DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(-1, 0));
        assertEquals(Quantity.valueOf(180, DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(-1, -1));
        assertEquals(Quantity.valueOf(225, DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(0, -1));
        assertEquals(Quantity.valueOf(270, DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(1, -1));
        assertEquals(Quantity.valueOf(315, DEGREE_ANGLE), a);
    }

}
