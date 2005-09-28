/*
 * Created on 06.02.2005
 */
package de.gliderpilot.igc;

import junit.framework.TestCase;

public class CRecordTest extends TestCase {

    public void testRecord() {
        String s = "C4947600N01108069EBURGFR";
        CRecord record = new CRecord(s);
        assertNotNull(record.getCoordinate());

        s = "C150504095343000000000102";
        record = new CRecord(s);
        assertNull(record.getCoordinate());

        s = "C0000000N00000000E";
        record = new CRecord(s);
        assertNull(record.getCoordinate());
    }

}
