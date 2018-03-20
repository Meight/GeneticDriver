package Utils.Math.NURBS;

import org.dyn4j.geometry.Vector2;

/**
 * @author Matthieu Le Boucher
 */
public class NURBSCurve {
    public static Vector2 evaluate(float[] nodes, float[] weights, Vector2[] controlPoints, int n, float t) {
        float denominator = 0;
        Vector2 numerator = new Vector2();
        float base;

        for (int i = 1; i < nodes.length - n - 1; i++) {
            base = BSpline.evaluate(nodes, i, n, t);
            denominator += weights[i] * base;
            numerator.add(controlPoints[i].multiply(weights[i]).multiply(base));
        }

        return numerator.multiply(1 / denominator);
    }
}
