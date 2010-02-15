/*
 * Created on 05.02.2005
 */
package de.gliderpilot.igc;

import junit.framework.TestCase;

public class IRecordTest extends TestCase {

    public void testENL() {
        String s = "I013640FXA3940SIU4143ENL";
        IRecord record = new IRecord(s);
        ExtensionIndex index = record.getIndex(IRecord.Extension.ENL);
        assertNotNull(index);
        // start index in igc is 1 base, but we are zero based!
        assertEquals(40, index.getStart());
        assertEquals(43, index.getEnd());

        index = record.getIndex(IRecord.Extension.SIU);
        assertNotNull(index);
        // start index in igc is 1 base, but we are zero based!
        assertEquals(38, index.getStart());
        assertEquals(40, index.getEnd());
    }

}
