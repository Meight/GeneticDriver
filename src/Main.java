import Model.Game.Track.Track;
import org.dyn4j.geometry.Vector2;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Vector2[] controlPoints = new Vector2[] {
                new Vector2(50, 50),
                new Vector2(300, 50),
                new Vector2(300, 300),
                new Vector2(50, 300),
                new Vector2(50, 50),
        };

        float[] nodes = new float[] {
                0, 0, 0.2f, 0.4f, 0.6f, 0.8f, 1f, 1f
        };

        float[] weights = new float[] {
                1, 1, 1, 1, 1
        };

        int n = 2;

        final Track track = new Track(controlPoints, nodes, weights, n);

        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                int i = 0;
                for(Vector2 point : track.getPoints()) {
                    Vector2 nextPoint = i == track.getPoints().length - 1 ? track.getPoints()[0] : track.getPoints()[i + 1];
                    g.drawLine((int) point.x, (int) point.y, (int) nextPoint.x, (int) nextPoint.y);
                    i++;
                }
            }
        };

        setContentPane(panel);
        setBounds(0, 0, 400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
