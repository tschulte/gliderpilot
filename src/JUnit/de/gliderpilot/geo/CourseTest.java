/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;

public class CourseTest extends AbstractJadeTest {

    public void testBound() {
        Angle a = (Angle) Quantity.valueOf(361, NonSI.DEGREE_ANGLE);
        a = Course.bound(a);
        Angle expected = (Angle) Quantity.valueOf(1, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = (Angle) Quantity.valueOf(-1, NonSI.DEGREE_ANGLE);
        a = Course.bound(a);
        expected = (Angle) Quantity.valueOf(359, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);
    }

    public void testInverse() {
        Angle a = (Angle) Quantity.valueOf(90, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        Angle expected = (Angle) Quantity.valueOf(270, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = (Angle) Quantity.valueOf(180, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = (Angle) Quantity.valueOf(360, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = (Angle) Quantity.valueOf(180, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = (Angle) Quantity.valueOf(180, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);
    }

    public void testDiff() {
        Angle a = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        Angle b = (Angle) Quantity.valueOf(90, NonSI.DEGREE_ANGLE);
        Angle expected = (Angle) Quantity.valueOf(90, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = (Angle) Quantity.valueOf(100, NonSI.DEGREE_ANGLE);
        expected = (Angle) Quantity.valueOf(100, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = (Angle) Quantity.valueOf(270, NonSI.DEGREE_ANGLE);
        expected = (Angle) Quantity.valueOf(-90, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = (Angle) Quantity.valueOf(260, NonSI.DEGREE_ANGLE);
        expected = (Angle) Quantity.valueOf(-100, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = (Angle) Quantity.valueOf(370, NonSI.DEGREE_ANGLE);
        expected = (Angle) Quantity.valueOf(10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = (Angle) Quantity.valueOf(370, NonSI.DEGREE_ANGLE);
        b = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        expected = (Angle) Quantity.valueOf(-10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = (Angle) Quantity.valueOf(350, NonSI.DEGREE_ANGLE);
        b = (Angle) Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        expected = (Angle) Quantity.valueOf(10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));
    }
}
