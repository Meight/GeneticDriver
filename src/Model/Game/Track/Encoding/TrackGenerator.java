package Model.Game.Track.Encoding;

import Model.Game.Track.Segment;
import Model.Game.Track.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 *
 * Genotype to phenotype mapper.
 */
public class TrackGenerator {
    public static Track generateTrack(Genotype genotype) {
        Track track = new Track();

        List<SlopeRange> slopeRanges = generateSlopeRanges(genotype.getControlPoints());

        for(SlopeRange slopeRange : slopeRanges)
            if (slopeRange.isEmpty())
                track = null;

        return track;
    }

    private static Segment generateSegment(ControlPoint p_i, ControlPoint p_j, float slope) {
        return null;
    }

    private static List<SlopeRange> generateSlopeRanges(List<ControlPoint> controlPoints) {
        List<SlopeRange> slopeRanges = new ArrayList<SlopeRange>();

        for(ControlPoint controlPoint : controlPoints) {

        }

        return slopeRanges;
    }
}
