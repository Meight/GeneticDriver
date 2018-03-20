package Model.Game.Track.Encoding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Genotype {
    private List<ControlPoint> controlPoints;

    public Genotype() {
        this.controlPoints = new ArrayList<ControlPoint>();
    }

    public void addControlPoint(ControlPoint controlPoint) {
        this.controlPoints.add(controlPoint);
    }
}
