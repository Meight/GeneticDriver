package Model.Game.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Matthieu Le Boucher
 */
public class Track {
    /**
     * Ordered list of the segments which constitute the track.
     */
    private List<Segment> segments;

    public Track() {
        this.segments = new ArrayList<Segment>();
    }

    public void addSegment(Segment segment) {
        this.segments.add(segment);
    }
}
