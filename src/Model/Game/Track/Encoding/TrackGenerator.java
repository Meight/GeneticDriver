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
    private static final float SLOPE_DEVIATION = 5f;

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

        int i = 0;
        ControlPoint previousPoint;
        ControlPoint nextPoint;

        for(ControlPoint controlPoint : controlPoints) {
            int previousIndex = (i == 0) ? (controlPoints.size() - 1) : (i - 1);
            int nextIndex = (i + 1 == controlPoints.size()) ? 0 : (i + 1);
            previousPoint = controlPoints.get(previousIndex);
            nextPoint = controlPoints.get(nextIndex);

            i++;
        }

        return slopeRanges;
    }
}
