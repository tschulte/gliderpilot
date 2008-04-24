/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import javax.quantities.Angle;
import javax.units.SI;

import javolution.lang.MathLib;

import org.jscience.physics.measures.Measure;

/**
 * This class provides static methods for handling of Angles that represent
 * courses or course deltas. I.e. angles that should be used within the bounds 0
 * to 360° or -180° to +180° for course deltas.
 * 
 * @author tobias
 * 
 */
public class Course {

    /**
     * Constant defining an angle of 0°
     */
    private static final Measure<Angle> ZERO = Measure.valueOf(0, SI.RADIAN);

    /**
     * Constant defining an angle of 360°
     */
    private static final Measure<Angle> TWO_PI = Measure.valueOf(
            MathLib.TWO_PI, SI.RADIAN);

    /**
     * Constant defining an angle of 180°
     */
    private static final Measure<Angle> PI = Measure.valueOf(MathLib.PI,
            SI.RADIAN);

    /**
     * Constant defining an angle of -180°
     */
    private static final Measure<Angle> MINUS_PI = Measure.valueOf(-MathLib.PI,
            SI.RADIAN);

    /**
     * Get the inverse course. The inverse course is the course to steer to
     * return to the original point. The result is already bounded.
     * 
     * @param course
     *            the course to be inverted
     * @return the inverse course
     */
    public static Measure<Angle> inverse(Measure<Angle> course) {
        return bound(course.plus(PI));
    }

    /**
     * Returns the difference between the two given angles. This method is
     * necessary, because 0° and 360° are the same, although the naive
     * difference is 360°. The result of this method can be used to see how to
     * correct course a to get the course b.
     * 
     * @param a
     *            the first course
     * @param b
     *            the second course
     * 
     * @return the difference in the range -180 <= diff <= 180
     */
    public static Measure<Angle> diff(Measure<Angle> a, Measure<Angle> b) {
        Measure<Angle> diff = b.minus(a);
        return bound(diff, MINUS_PI, PI);
    }

    /**
     * Get an angle in the bounds 0 <= angle <= 360°.
     * 
     * @param angle
     *            the angle to be bound
     * @return the bound angle
     */
    public static Measure<Angle> bound(Measure<Angle> angle) {
        return bound(angle, ZERO, TWO_PI);
    }

    /**
     * Get an angle in the bounds lowerBound <= angle <= (lowerBound+2pi).
     * 
     * @param angle
     *            the angle to be bound
     * @param lowerBound
     *            the lower bound
     * @param upperBound
     *            the upper bound, it has to be lowerBound + 2 PI
     * @return the bound angle
     */
    private static Measure<Angle> bound(Measure<Angle> angle,
            Measure<Angle> lowerBound, Measure<Angle> upperBound) {
        if (lowerBound.isGreaterThan(angle)) {
            return bound(angle.plus(TWO_PI), lowerBound, upperBound);
        }
        if (upperBound.isLessThan(angle)) {
            return bound(angle.minus(TWO_PI), lowerBound, upperBound);
        }
        return angle;
    }

}
