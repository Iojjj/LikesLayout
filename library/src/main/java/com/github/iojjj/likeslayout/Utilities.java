package com.github.iojjj.likeslayout;

/**
 * Helpful utils.
 */
class Utilities {

    private Utilities() {
        //no instance
    }

    /**
     * Trapeze function.
     * @param t current value
     * @param a value at <b>aT</b> point of time
     * @param aT first point
     * @param b value at <b>bT</b> point of time
     * @param bT second point
     * @param c value at <b>cT</b> point of time
     * @param cT third point
     * @param d value at <b>dT</b> point of time
     * @param dT forth point
     * @return calculated value
     */
    public static float trapeze(float t, float a, float aT, float b, float bT, float c, float cT, float d, float dT) {
        if (t < aT) {
            return a;
        }
        if (t >= aT && t < bT) {
            float norm = normalize(t, aT, bT);
            return a + norm * (b - a);
        }
        if (t >= bT && t < cT) {
            float norm = normalize(t, bT, cT);
            return b + norm * (c - b);
        }
        if (t >= cT && t <= dT) {
            float norm = normalize(t, cT, dT);
            return c + norm * (d - c);
        }
        return d;
    }

    /**
     * Normalize value between minimum and maximum.
     * @param val value
     * @param minVal minimum value
     * @param maxVal maximum value
     * @return normalized value in range <code>0..1</code>
     * @throws IllegalArgumentException if value is out of range <code>[minVal, maxVal]</code>
     */
    public static float normalize(float val, float minVal, float maxVal) {
        if (val < minVal || val > maxVal)
            throw new IllegalArgumentException("Value must be between min and max values. [val, min, max]: [" + val + "," + minVal + ", " + maxVal + "]");
        return (val - minVal) / (maxVal - minVal);
    }
}
