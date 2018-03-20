package Utils.Math.NURBS;

/**
 * @author Matthieu Le Boucher
 *
 * B-spline base function of degree n.
 */
public class BSpline {
    public static float evaluate(float[] nodes, int i, int n, float u) {
        if(n == 0)
            return u >= nodes[i] && u <= nodes[i + 1] ? 1 : 0;
        else {
            float f = nodes[i + n] == nodes[i] ? 0 : (u - nodes[i]) / (nodes[i + n] - nodes[i]);
            float g = nodes[i + n + 1] == nodes[i + 1] ? 0 : (nodes[i + n + 1] - u) / (nodes[i + n + 1] - nodes[i + 1]);

            return f * evaluate(nodes, i, n - 1, u) + g * evaluate(nodes, i + 1, n - 1, u);
        }
    }
}
