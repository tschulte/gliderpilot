/*
 * Created on 05.02.2005
 */
package de.gliderpilot.igc;

/**
 * An extension index specifies the index of an extension in for example the
 * B-record.
 * 
 * @author tobias
 * 
 */
class ExtensionIndex {

    private int start;

    private int end;

    /**
     * Specifies an extension that is defined in the record at index start to
     * end. Start and end will be directly used for the String#substring(int,
     * int) Method.
     */
    public ExtensionIndex(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

}
