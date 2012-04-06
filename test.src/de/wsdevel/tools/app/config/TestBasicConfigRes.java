package de.wsdevel.tools.app.config;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;

import junit.framework.TestCase;

/**
 * Created on 22.08.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music </a>
 * @version $Author: sweiss $ -- $Revision: 1.6 $ -- $Date: 2005/08/22
 *          15:13:37 $<br>
 *          (c) dawn of music 2005 - All rights reserved.
 * 
 */
public class TestBasicConfigRes extends TestCase implements ConfigResource {

    /**
     * {@link String} COMMENT.
     */
    private static final String TESTBOOLEAN = Boolean.toString(true);

    /**
     * {@link String} COMMENT.
     */
    private static final String KEY_BOOLEAN = "BOOLEAN";

    /**
     * {@link String} COMMENT.
     */
    private static final String TESTSTRING = "teststring";

    /**
     * {@link String} COMMENT.
     */
    private static final String KEY_STRING = "STRING";

    /**
     * @param arg0
     *            {@link String}[]
     */
    public TestBasicConfigRes(final String arg0) {
	super(arg0);
    }

    /**
     * {@link boolean} COMMENT.
     */
    private static boolean allreadySetUp = false;

    /**
     * @throws Exception
     *             COMMENT
     * @see TestCase#setUp()
     */
    @Override
    protected final void setUp() throws Exception {
	if (!allreadySetUp) {
	    ConfigRes.add(TestBasicConfigRes.class, new BasicConfigRes("TEST") {
		/**
		 * @see de.wsdevel.tools.app.config.BasicConfigRes#initDefaultProperties(java.util.Properties)
		 */
		@Override
		public void initDefaultProperties(
			final Properties defaultPropertiesVal) {
		    defaultPropertiesVal.put(KEY_STRING, TESTSTRING);
		    defaultPropertiesVal.put(KEY_BOOLEAN, TESTBOOLEAN);
		}
	    });
	    allreadySetUp = true;
	}
    }

    /**
     * testing default properties.
     */
    public final void testDefaults() {
	assertTrue("not the right string", ConfigRes.getInstance(
		TestBasicConfigRes.class).getString(KEY_STRING).equals(
		TESTSTRING));
	assertTrue("not the right boolean", ConfigRes.getInstance(
		TestBasicConfigRes.class).getBoolean(KEY_BOOLEAN) == Boolean
		.valueOf(TESTBOOLEAN).booleanValue());
    }

    /**
     * testing setting of properties during run.
     */
    public final void testPropertySetting() {
	ConfigRes.getInstance(TestBasicConfigRes.class).setBoolean(KEY_BOOLEAN,
		false);
	assertTrue("changed boolean is not correct!", !ConfigRes.getInstance(
		TestBasicConfigRes.class).getBoolean(KEY_BOOLEAN));
	ConfigRes.getInstance(TestBasicConfigRes.class).setBoolean(KEY_BOOLEAN,
		true);
	assertTrue("changed boolean is not correct!", ConfigRes.getInstance(
		TestBasicConfigRes.class).getBoolean(KEY_BOOLEAN));
    }

    public Enumeration<?> elementsOfAdaptedProperties() {
	return null;
    }

    public boolean getBoolean(String key) {
	return false;
    }

    public Color getColor(String key) {
	return null;
    }

    public double getDouble(String key) {
	return 0;
    }

    public float getFloat(String key) {
	return 0;
    }

    public int getInt(String key) {
	return 0;
    }

    public Object getProperty(String key) {
	return null;
    }

    public Serializable getSerializable(String key) {
	return null;
    }

    public String getString(String key) {
	return null;
    }

    public Enumeration<?> keysOfAdaptedProperties() {
	return null;
    }

    public void readPropertiesFromFile(File propertiesFile) throws IOException {
    }

    public void savePropertiesToFile(File propertiesFile) throws IOException {
    }

    public void setBoolean(String key, boolean value) {
    }

    public void setColor(String key, Color c) {
    }

    public void setDouble(String key, double d) {
    }

    public void setFloat(String key, float f) {
    }

    public void setInt(String key, int i) {
    }

    public void setProperty(String key, Object prop) {
    }

    public void setSerializable(String key, Serializable object) {
    }

    public void setString(String key, String string) {
    }

}
/*
 * $Log: TestBasicConfigRes.java,v $
 * Revision 1.6  2009-02-09 13:15:16  sweiss
 * clean up of stuff
 * Revision 1.5 2006/07/21 12:58:39 sweissTFH
 * comment clean up
 * 
 * Revision 1.4 2006/05/23 10:45:47 sweissTFH using class as key
 * 
 * Revision 1.3 2006/05/23 10:26:05 sweissTFH added functionality to config and
 * cleaned up a bit
 * 
 * Revision 1.2 2006/05/12 15:36:43 sweissTFH cleanup
 * 
 * Revision 1.1 2006/05/02 16:06:01 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.5 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * Revision 1.4 2005/10/13 15:42:21 sweissTFH some enhancements concerning
 * BasicConfigRes, as well cleaned up projects package
 * 
 * Revision 1.3 2005/08/22 15:13:37 sweissTFH fixed bug in getBoolean
 */
