/*
 * Created on 03.02.2005
 */
package de.gliderpilot.geo;

import org.jscience.physics.quantities.Angle;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.SI;

/**
 * This class provides static methods for handling of Angles that represent
 * courses or course deltas. I.e. angles that should be used within the bounds 0
 * to 360° or -180° to +180° for course deltas.
 * 
 * @author tobias
 * 
 */
public class Course {

    private static final Angle TWO_PI = (Angle) Quantity.valueOf(2 * Math.PI,
            SI.RADIAN);

    private static final Angle PI = (Angle) Quantity
            .valueOf(Math.PI, SI.RADIAN);

    private static final Angle MINUS_PI = (Angle) Quantity.valueOf(-Math.PI,
            SI.RADIAN);

    /**
     * Get the inverse course. The inverse course is the course to steer to
     * return to the original point. The result is already bounded.
     */
    public static Angle inverse(Angle course) {
        return bound((Angle) course.plus(PI));
    }

    /**
     * Get an angle in the bounds 0 <= angle <= 360°.
     */
    public static Angle bound(Angle angle) {
        return bound(angle, Angle.ZERO);
    }

    /**
     * Get an angle in the bounds lowerBound <= angle <= (lowerBound+2pi).
     */
    private static Angle bound(Angle angle, Angle lowerBound) {
        Angle upperBound = (Angle) lowerBound.plus(TWO_PI);
        if (lowerBound.isGreaterThan(angle)) {
            return bound((Angle) angle.plus(TWO_PI), lowerBound);
        }
        if (upperBound.isLessThan(angle)) {
            return bound((Angle) angle.minus(TWO_PI), lowerBound);
        }
        return angle;
    }

    /**
     * Returns the difference between the two given angles. This method is
     * necessary, because 0° and 360° are the same, although the naive
     * difference is 360°. The result of this method can be used to see how to
     * correct course a to get the course b.
     * 
     * @return the difference in the range -180 <= diff <= 180
     */
    public static Angle diff(Angle a, Angle b) {
        Angle diff = (Angle) b.minus(a);
        return bound(diff, MINUS_PI);
    }

}
