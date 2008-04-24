/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.geo;

import javax.quantities.Angle;
import javax.quantities.Length;
import javax.units.NonSI;

import org.jscience.physics.measures.Measure;

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
        Measure<Length> l = new GeoCoordinate(0, 0)
                .getDistanceTo(new GeoCoordinate(1, 0));
        assertNotNull(l);
        assertEquals(Measure.valueOf(60, NonSI.NAUTICAL_MILE), l);
    }

    public void testDistancePoleToPole() {
        Measure<Length> l = new GeoCoordinate(-90, 0).getDistanceTo(new GeoCoordinate(
                90, 0));
        assertNotNull(l);
        assertEquals(Measure.valueOf(10800, NonSI.NAUTICAL_MILE), l);
    }

    public void testDistancePoleToPoleLonSwitch() {
        Measure<Length> l = new GeoCoordinate(-90, 0).getDistanceTo(new GeoCoordinate(
                90, 180));
        assertNotNull(l);
        assertEquals(Measure.valueOf(10800, NonSI.NAUTICAL_MILE), l);
    }

    public void testDistanceEquator() {
        Measure<Length> l = new GeoCoordinate(0, 0).getDistanceTo(new GeoCoordinate(0,
                180));
        assertNotNull(l);
        assertEquals(Measure.valueOf(10800, NonSI.NAUTICAL_MILE), l);
    }

    public void testShortestDistance() {
        Measure<Length> l1 = new GeoCoordinate(0, 0).getDistanceTo(new GeoCoordinate(0,
                179));
        Measure<Length> l2 = new GeoCoordinate(0, 0).getDistanceTo(new GeoCoordinate(0,
                -179));
        assertEquals(l1, l2);
    }

    public void testTrueCourseTo() {
        Measure<Angle> a = new GeoCoordinate(0, 0).trueCourseTo(null);
        assertNull(a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(91, 0));
        assertNull(a);
        a = new GeoCoordinate(91, 0).trueCourseTo(new GeoCoordinate(0, 0));
        assertNull(a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(1, 0));
        assertEquals(Measure.valueOf(0, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(1, 1));
        assertEquals(Measure.valueOf(45, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(0, 1));
        assertEquals(Measure.valueOf(90, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(-1, 1));
        assertEquals(Measure.valueOf(135, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(-1, 0));
        assertEquals(Measure.valueOf(180, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(-1, -1));
        assertEquals(Measure.valueOf(225, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(0, -1));
        assertEquals(Measure.valueOf(270, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).trueCourseTo(new GeoCoordinate(1, -1));
        assertEquals(Measure.valueOf(315, NonSI.DEGREE_ANGLE), a);
    }

    public void testAverageCourseTo() {
        Measure<Angle> a = new GeoCoordinate(0, 0).getAverageCourseTo(null);
        assertNull(a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(91, 0));
        assertNull(a);
        a = new GeoCoordinate(91, 0)
                .getAverageCourseTo(new GeoCoordinate(0, 0));
        assertNull(a);
        a = new GeoCoordinate(0, 0).getAverageCourseTo(new GeoCoordinate(1, 0));
        assertEquals(Measure.valueOf(0, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).getAverageCourseTo(new GeoCoordinate(1, 1));
        assertEquals(Measure.valueOf(45, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0).getAverageCourseTo(new GeoCoordinate(0, 1));
        assertEquals(Measure.valueOf(90, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(-1, 1));
        assertEquals(Measure.valueOf(135, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(-1, 0));
        assertEquals(Measure.valueOf(180, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(-1, -1));
        assertEquals(Measure.valueOf(225, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(0, -1));
        assertEquals(Measure.valueOf(270, NonSI.DEGREE_ANGLE), a);
        a = new GeoCoordinate(0, 0)
                .getAverageCourseTo(new GeoCoordinate(1, -1));
        assertEquals(Measure.valueOf(315, NonSI.DEGREE_ANGLE), a);
    }

}
