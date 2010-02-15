/*
 * Created on 06.02.2005
 */
package de.gliderpilot.igc;

import java.io.IOException;

import junit.framework.TestCase;

public class IgcFileTest extends TestCase {

    public void test45FA0Y73() throws Exception {
        IgcFile igcFile = parseIgcFile("45FA0Y73");
        assertFalse(igcFile.hasParseErrors());
    }

    public void testParseErrors() throws Exception {
        IgcFile igcFile = parseIgcFile("parseErrors");
        assertTrue(igcFile.hasParseErrors());
    }

    private IgcFile parseIgcFile(String name) throws IOException {
        Class clazz = getClass();
        Package p = clazz.getPackage();
        String packageName = p.getName().replace('.', '/');
        ClassLoader cl = clazz.getClassLoader();
        IgcFile igcFile = new IgcFile(cl.getResourceAsStream(packageName + "/"
                + name + ".igc"));
        return igcFile;
    }

}
