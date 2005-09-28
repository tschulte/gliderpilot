/*
 * Created on 05.02.2005
 */
package de.gliderpilot.igc;

import java.util.HashMap;
import java.util.Map;

/**
 * The I-Record contain information about additional fields that can be encoded
 * in the G-Record.
 */
class IRecord {

    enum Extension {
        FXA, ENL, SIU,
    }

    Map<Extension, ExtensionIndex> extensions;

    public IRecord(String record) {
        int numExtensions = Integer.parseInt(record.substring(1, 3));
        extensions = new HashMap<Extension, ExtensionIndex>(numExtensions);
        for (Extension extension : Extension.values()) {
            int index = record.indexOf(extension.toString()) - 4;
            if (index > 2) {
                // start index in igc is 1 base, but we are zero based!
                int start = Integer
                        .parseInt(record.substring(index, index + 2)) - 1;
                int end = Integer.parseInt(record.substring(index + 2,
                        index + 4));
                extensions.put(extension, new ExtensionIndex(start, end));
            }
        }
    }

    /**
     * Get the extensionIndex for the given extension or null if the extension
     * is not defined.
     */
    public ExtensionIndex getIndex(Extension extension) {
        return extensions.get(extension);
    }
}
