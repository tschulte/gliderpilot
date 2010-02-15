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

    /**
     * The 0 based start index.
     */
    private int _start;

    /**
     * The 0 based end index.
     */
    private int _end;

    /**
     * Specifies an extension that is defined in the record at index start to
     * end. Start and end will be directly used for the
     * {@link String#substring(int, int)} Method.
     */
    public ExtensionIndex(int pStart, int pEnd) {
        _start = pStart;
        _end = pEnd;
    }

    public int getEnd() {
        return _end;
    }

    public int getStart() {
        return _start;
    }

}
