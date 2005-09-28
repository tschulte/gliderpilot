/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import junit.framework.TestCase;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Quantity;

/**
 * Base class for all tests of functionality dealing with Jade.
 * 
 * @author tobias
 * 
 */
public abstract class AbstractJadeTest extends TestCase {

    /**
     * Asserts that the two given quantities are nearly equal to each other (by
     * 0.1 permille)
     * 
     * @param q1
     * @param q2
     */
    protected void assertEquals(Quantity q1, Quantity q2) {
        double maxDiff = Math.abs(.0001 * q1.doubleValue());
        maxDiff = Math.max(maxDiff, 0.0001);
        assertEquals(q1.doubleValue(), q2.doubleValue(), maxDiff);
    }

    /**
     * Asserts that the diff of the given two angles is very close to zero.
     * 
     * @param a1
     * @param a2
     */
    protected void assertEquals(Angle a1, Angle a2) {
        Angle diff = Course.diff(a1, a2);
        assertEquals((Quantity) Angle.ZERO, diff);
    }

}
