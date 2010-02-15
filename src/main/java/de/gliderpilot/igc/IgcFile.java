/*
 * Created on 03.02.2005
 */

package de.gliderpilot.igc;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jscience.geography.coordinates.Coordinates;

import de.gliderpilot.geo.FlightCoordinate;

/**
 * Parse a given IGC file and make the content available as appropriate objects.
 * 
 * @author tobias
 */
public class IgcFile {

    private final Log _log = LogFactory.getLog(getClass());

    private SimpleDateFormat _dateFormat;

    /**
     * After reading the HFDTE record this calendar is initialized to the date
     * of the flight's first point. After each G record it's value will contain
     * the last points date.
     */
    private Calendar _cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    /**
     * This list will contain all the coordinates of the flight.
     */
    private List<FlightCoordinate> _coordinates = new ArrayList<FlightCoordinate>(
            5000);

    /**
     * If there were any errors parsing the IGC-File, this map contains the
     * exceptions that where caught.
     */
    private Map<Integer, String> _errors = new TreeMap<Integer, String>();

    private LineNumberReader _asciiReader;

    /**
     * If not null, this one holds the indexes of additional information in
     * G-Records.
     */
    private IRecord _iRecord;

    private List<Coordinates> _task = new ArrayList<Coordinates>();

    public IgcFile(File file) throws FileNotFoundException, IOException {
        this(new FileInputStream(file));
    }

    public IgcFile(InputStream is) throws IOException {
        _dateFormat = new SimpleDateFormat("ddMMyy");
        _dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        _asciiReader = new LineNumberReader(new InputStreamReader(is));
        parse();
    }

    /**
     * DOCUMENT ME!
     * 
     * @throws IOException
     */
    private void parse() throws IOException {
        String line;
        while ((line = _asciiReader.readLine()) != null) {
            try {
                if (line.startsWith("B")) {
                    parseBRecord(line);
                } else if (line.startsWith("H")) {
                    parseHRecord(line);
                } else if (line.startsWith("I")) {
                    _iRecord = new IRecord(line);
                } else if (line.startsWith("C")) {
                    parseCRecord(line);
                } else if (line.startsWith("AOLC")) {
                    // TODO: OLC file handling
                }
            } catch (Exception e) {
                _log.warn(new Formatter().format(
                        "Error parsing line number %d (\"%s\")",
                        Integer.valueOf(_asciiReader.getLineNumber()), line)
                        .toString());
                _log.info(e, e);
                _errors
                        .put(Integer.valueOf(_asciiReader.getLineNumber()),
                                line);
            }
        }
    }

    public boolean hasParseErrors() {
        return !_errors.isEmpty();
    }

    /**
     * @return the coordinates
     */
    public List<FlightCoordinate> getCoordinates() {
        return _coordinates;
    }

    /**
     * The C-Record defines a point of the declared task.
     */
    private void parseCRecord(String line) {
        CRecord cRecord = new CRecord(line);
        if (cRecord.getCoordinate() != null) {
            _task.add(cRecord.getCoordinate());
        }
    }

    /**
     * The B-Record contains one recorded track point
     * 
     * @param line
     * @throws ParseException
     */
    private void parseBRecord(String line) throws ParseException {
        BRecord record = new BRecord(line, _cal, _iRecord);
        _coordinates.add(record.getCoordinate());
    }

    /**
     * The H-Record contains information about the used glider, date, etc.
     */
    private void parseHRecord(String line) throws ParseException {
        if (line.startsWith("HFDTE") || line.startsWith("HPDTE")) {
            int i = line.indexOf(":");
            if (i > 0) {
                _cal.setTime(_dateFormat.parse(line.substring(i + 1).trim()));
            } else {
                _cal.setTime(_dateFormat.parse(line.substring(5, 11)));
            }
        } else if (line.startsWith("HFPLT") || line.startsWith("HPPLT")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                String name = line.substring(i, line.length()).trim();
                @SuppressWarnings("unused")
                String givenName = null;
                int j = name.indexOf(',');
                if (j >= 0) {
                    givenName = name.substring(j + 1).trim();
                    name = name.substring(0, j).trim();
                } else {
                    j = name.lastIndexOf(' ');
                    if (j > 0) {
                        givenName = name.substring(0, j).trim();
                        name = name.substring(j + 1);
                    }
                }
            }
        } else if (line.startsWith("HFG") || line.startsWith("HFC")
                || line.startsWith("HP")) {
            parseGliderInfo(line);
        }
    }

    private void parseGliderInfo(String line) {
        if (line.startsWith("HFGID") || line.startsWith("HPGID")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                @SuppressWarnings("unused")
                String callSign = line.substring(i, line.length()).trim();
            }
        } else if (line.startsWith("HFGTY") || line.startsWith("HPGTY")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                @SuppressWarnings("unused")
                String type = line.substring(i, line.length()).trim();
            }
        } else if (line.startsWith("HFCID") || line.startsWith("HPGID")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                @SuppressWarnings("unused")
                String competitionSign = line.substring(i, line.length())
                        .trim();
            }
        } else if (line.startsWith("HFCCL")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                @SuppressWarnings("unused")
                String competitionClass = line.substring(i, line.length())
                        .trim();
            }
        }
    }

}
