import Model.Game.Track.Track;
import org.dyn4j.geometry.Vector2;

public class Main {

    public static void main(String[] args) {
        Vector2[] controlPoints = new Vector2[] {
                new Vector2(0, 0),
                new Vector2(10, 20),
                new Vector2(32, 32),
                new Vector2(50, 32),
        };

        float[] nodes = new float[] {
                0, 0, 0, 0.5f, 1f, 1f, 1f
        };

        float[] weights = new float[] {
                1, 1, 1, 1
        };

        int n = 2;

        Track track = new Track(controlPoints, nodes, weights, n);

        System.out.println(track);
    }
}
