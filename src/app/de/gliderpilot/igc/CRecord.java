/*
 * Created on 06.02.2005
 */
package de.gliderpilot.igc;

import de.gliderpilot.geo.GeoCoordinateImpl;

/**
 * The C Record is used to specify tasks and to make flight declarations. It is
 * placed in the IGC file before the first fix (B) record and after the H, I and
 * J records. The first line contains the UTC-date and UTC-time of the
 * declaration, the local date of the intended day of flight, the task ID, the
 * number of turn points of the task and a textstring which can be used to
 * describe the task (500k triangle, etc). Pilot input of intended flight date
 * must be the flight date in local time, not the UTC date which will be
 * different in countries with large time offsets from UTC (The Three-Letter
 * Code for Time Zone Offset is TZN, see the list in para 7).
 * <p>
 * The other lines contain the WGS84 lat/long coordinates and a textstring for
 * the place or point concerned. These include the take-off field, start point,
 * turn points, finish point and landing field. The format of C Record is as
 * follows, using N latitude and E longitude:
 * 
 * <pre>
 *  C D D M M Y Y H H M M S S F D F M F Y I I I I T T T E X T S T R I N G CR LF
 *  C D D M M M M M N D D D M M M M M E TAKEOFF FIELD CR LF
 *  C D D M M M M M N D D D M M M M M E START POINT CR LF
 *  C D D M M M M M N D D D M M M M M E TURN POINT CR LF
 *  C D D M M M M M N D D D M M M M M E TURN POINT CR LF
 *  C D D M M M M M N D D D M M M M M E FINISH POINT CR LF
 *  C D D M M M M M N D D D M M M M M E LANDING FIELD CR LF
 * </pre>
 * 
 * @author tobias
 */
class CRecord extends AbstractIgcRecord {

    private GeoCoordinateImpl coord;

    public CRecord(String record) {
        if (record.indexOf('N') < 0 && record.indexOf('S') < 0
                || record.indexOf('W') < 0 && record.indexOf('E') < 0
                || record.indexOf("0000000") == 1) {
            return;
        }
        coord = parsePoint(record, 1);
    }

    public GeoCoordinateImpl getCoordinate() {
        return coord;
    }

}
