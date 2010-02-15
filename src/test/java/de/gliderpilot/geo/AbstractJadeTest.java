/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import javax.quantities.Quantity;

import junit.framework.TestCase;

import org.jscience.physics.measures.Measure;

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
    @SuppressWarnings("unchecked")
    protected <T extends Quantity> void assertEquals(Measure<T> q1,
            Measure<T> q2) {
        double maxDiff = Math.abs(.0001 * q1.doubleValue(q1.getUnit()));
        maxDiff = Math.max(maxDiff, 0.0001);
        assertEquals(q1.doubleValue(q1.getUnit()),
                q2.doubleValue(q1.getUnit()), maxDiff);
    }

}
