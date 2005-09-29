/*
 * Created on 03.02.2005
 */

package de.gliderpilot.igc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gliderpilot.geo.FlightCoordinate;
import de.gliderpilot.geo.GeoCoordinate;

/**
 * Parse a given IGC file and make the content available as appropriate objects.
 * 
 * @author tobias
 */
public class IgcFile {

    private final Log log = LogFactory.getLog(getClass());

    private SimpleDateFormat dateFormat;

    /**
     * After reading the HFDTE record this calendar is initialized to the date
     * of the flight's first point. After each G record it's value will contain
     * the last points date.
     */
    private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    /**
     * This list will contain all the coordinates of the flight.
     */
    private List<FlightCoordinate> coordinates = new ArrayList<FlightCoordinate>(
            5000);

    /**
     * If there were any errors parsing the IGC-File, this map contains the
     * exceptions that where caught.
     */
    private Map<Integer, String> errors = new TreeMap<Integer, String>();

    private LineNumberReader asciiReader;

    /**
     * If not null, this one holds the indexes of additional information in
     * G-Records.
     */
    private IRecord iRecord;

    private List<GeoCoordinate> task = new ArrayList<GeoCoordinate>();

    public IgcFile(File file) throws FileNotFoundException, IOException {
        this(new FileInputStream(file));
    }

    public IgcFile(InputStream is) throws IOException {
        dateFormat = new SimpleDateFormat("ddMMyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        asciiReader = new LineNumberReader(new InputStreamReader(is));
        parse();
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * @throws ParseException
     * @throws IOException
     */
    private void parse() throws IOException {
        String line;
        while ((line = asciiReader.readLine()) != null) {
            try {
                if (line.startsWith("B")) {
                    parseBRecord(line);
                } else if (line.startsWith("H")) {
                    parseHRecord(line);
                } else if (line.startsWith("I")) {
                    iRecord = new IRecord(line);
                } else if (line.startsWith("C")) {
                    parseCRecord(line);
                } else if (line.startsWith("AOLC")) {
                    // TODO: OLC file handling
                }
            } catch (Exception e) {
                log.warn(new Formatter().format(
                        "Error parsing line number %d (\"%s\")",
                        Integer.valueOf(asciiReader.getLineNumber()), line)
                        .toString());
                errors.put(Integer.valueOf(asciiReader.getLineNumber()), line);
            }
        }
    }

    public boolean hasParseErrors() {
        return !errors.isEmpty();
    }

    /**
     * The C-Record defines a point of the declared task.
     */
    private void parseCRecord(String line) {
        CRecord cRecord = new CRecord(line);
        if (cRecord.getCoordinate() != null) {
            task.add(cRecord.getCoordinate());
        }
    }

    /**
     * The B-Record contains one recorded track point
     * 
     * @param line
     * @throws ParseException
     */
    private void parseBRecord(String line) throws ParseException {
        BRecord record = new BRecord(line, cal, iRecord);
        coordinates.add(record.getCoordinate());
    }

    /**
     * The H-Record contains information about the used glider, date, etc.
     */
    private void parseHRecord(String line) throws ParseException {
        if (line.startsWith("HFDTE") || line.startsWith("HPDTE")) {
            int i = line.indexOf(":");
            if (i > 0) {
                cal.setTime(dateFormat.parse(line.substring(i + 1).trim()));
            } else {
                cal.setTime(dateFormat.parse(line.substring(5, 11)));
            }
        } else if (line.startsWith("HFPLT") || line.startsWith("HPPLT")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                String name = line.substring(i, line.length()).trim();
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
                String callSign = line.substring(i, line.length()).trim();
            }
        } else if (line.startsWith("HFGTY") || line.startsWith("HPGTY")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                String type = line.substring(i, line.length()).trim();
            }
        } else if (line.startsWith("HFCID") || line.startsWith("HPGID")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                String competitionSign = line.substring(i, line.length())
                        .trim();
            }
        } else if (line.startsWith("HFCCL")) {
            int i = line.indexOf(":") + 1;
            if (i > 0 && i < line.length()) {
                String competitionClass = line.substring(i, line.length())
                        .trim();
            }
        }
    }

}
