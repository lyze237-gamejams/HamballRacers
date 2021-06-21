package dev.lyze.hamballracers.utils;

public class MathUtils2 {
    static public float moveTowards(float current, float target, float maxDelta)
    {
        if (Math.abs(target - current) <= maxDelta)
            return target;
        return current + Math.signum(target - current) * maxDelta;
    }
}
