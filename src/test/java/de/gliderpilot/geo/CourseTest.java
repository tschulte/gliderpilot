/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import javax.quantities.Angle;
import javax.units.NonSI;

import org.jscience.physics.measures.Measure;

public class CourseTest extends AbstractJadeTest {

    public void testBound() {
        Measure<Angle> a = Measure.valueOf(361, NonSI.DEGREE_ANGLE);
        a = Course.bound(a);
        Measure<Angle> expected = Measure.valueOf(1, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Measure.valueOf(-1, NonSI.DEGREE_ANGLE);
        a = Course.bound(a);
        expected = Measure.valueOf(359, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);
    }

    public void testInverse() {
        Measure<Angle> a = Measure.valueOf(90, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        Measure<Angle> expected = Measure.valueOf(270, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = Measure.valueOf(180, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Measure.valueOf(360, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = Measure.valueOf(180, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);

        a = Measure.valueOf(180, NonSI.DEGREE_ANGLE);
        a = Course.inverse(a);
        expected = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        assertEquals(expected, a);
    }

    public void testDiff() {
        Measure<Angle> a = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        Measure<Angle> b = Measure.valueOf(90, NonSI.DEGREE_ANGLE);
        Measure<Angle> expected = Measure.valueOf(90, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Measure.valueOf(100, NonSI.DEGREE_ANGLE);
        expected = Measure.valueOf(100, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Measure.valueOf(270, NonSI.DEGREE_ANGLE);
        expected = Measure.valueOf(-90, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Measure.valueOf(260, NonSI.DEGREE_ANGLE);
        expected = Measure.valueOf(-100, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        b = Measure.valueOf(370, NonSI.DEGREE_ANGLE);
        expected = Measure.valueOf(10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Measure.valueOf(370, NonSI.DEGREE_ANGLE);
        b = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        expected = Measure.valueOf(-10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));

        a = Measure.valueOf(350, NonSI.DEGREE_ANGLE);
        b = Measure.valueOf(0, NonSI.DEGREE_ANGLE);
        expected = Measure.valueOf(10, NonSI.DEGREE_ANGLE);
        assertEquals(expected, Course.diff(a, b));
    }
}
