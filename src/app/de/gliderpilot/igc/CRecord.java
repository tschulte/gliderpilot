/*
 * Created on 06.02.2005
 */
package de.gliderpilot.igc;

import de.gliderpilot.geo.GeoCoordinate;

class CRecord extends AbstractIgcRecord {

    private GeoCoordinate coord;
    
    public CRecord(String record) {
        if (record.indexOf('N') < 0 && record.indexOf('S') < 0
                || record.indexOf('W') < 0 && record.indexOf('E') < 0
                || record.indexOf("0000000") == 1) {
            return;
        }
        coord = parsePoint(record, 1);
    }
    
    public GeoCoordinate getCoordinate() {
        return coord;
    }

}
