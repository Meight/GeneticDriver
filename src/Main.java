import Controller.LobbyController;
import Model.Game.Lobby;
import Model.Game.Player;
import Model.Game.Track.Track;
import View.LobbyView;
import org.dyn4j.geometry.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Main extends JFrame {

    static LobbyController controller;
    static LobbyView lobbyView;

    public Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = networkPanel();
        setContentPane(panel);
        setBounds(0, 0, 400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    public static JPanel networkPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        JButton newRoom = new JButton("Create Room");
        final JTextField pseudo = new JTextField(20);
        panel.add(newRoom,BorderLayout.SOUTH);
        panel.add(pseudo,BorderLayout.NORTH);
        newRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pseudo.getText().length() > 0){
                    controller = new LobbyController(new Lobby(new Player(pseudo.getText())));
                    lobbyView = new LobbyView(controller);
                    JComponent b = (JComponent) e.getSource();
                    JFrame frame = (JFrame)SwingUtilities.getRoot(b);
                    frame.setVisible(false);
                }
            }
        });
        return panel;
    }

    public static JPanel trackPanel(){
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

        return panel;
    }
}
