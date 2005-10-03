/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;

public class CourseTest extends AbstractJadeTest {

    public void testBound() {
        Angle a = Quantity.valueOf(361, NonSI.DEGREE_ANGLE);
        a = Course.bound(a);
        Angle expected = Quantity.valueOf(1, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Quantity.valueOf(-1, NonSI.DEGREE_ANGLE);
        a = Course.bound(a);
        expected = Quantity.valueOf(359, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);
    }

    public void testInverse() {
        Angle a = Quantity.valueOf(90, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        Angle expected = Quantity.valueOf(270, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = Quantity.valueOf(180, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Quantity.valueOf(360, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = Quantity.valueOf(180, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Quantity.valueOf(180, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);
    }

    public void testDiff() {
        Angle a = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        Angle b = Quantity.valueOf(90, NonSI.DEGREE_ANGLE);
        Angle expected = Quantity.valueOf(90, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Quantity.valueOf(100, NonSI.DEGREE_ANGLE);
        expected = Quantity.valueOf(100, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Quantity.valueOf(270, NonSI.DEGREE_ANGLE);
        expected = Quantity.valueOf(-90, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Quantity.valueOf(260, NonSI.DEGREE_ANGLE);
        expected = Quantity.valueOf(-100, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Quantity.valueOf(370, NonSI.DEGREE_ANGLE);
        expected = Quantity.valueOf(10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Quantity.valueOf(370, NonSI.DEGREE_ANGLE);
        b = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        expected = Quantity.valueOf(-10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Quantity.valueOf(350, NonSI.DEGREE_ANGLE);
        b = Quantity.valueOf(0, NonSI.DEGREE_ANGLE);
        expected = Quantity.valueOf(10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));
    }
}
