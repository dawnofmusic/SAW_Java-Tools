package de.wsdevel.tools.app.config;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Created on 08.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public interface ConfigResource {
    /**
     * @param key
     *            {@link String}
     * @return {@link Object}
     */
    Object getProperty(String key);

    /**
     * @param key
     *            {@link String}
     * @param prop
     *            {@link Object}
     */
    void setProperty(String key, Object prop);

    /**
     * @param key
     *            {@link String}
     * @return {@link String}
     */
    String getString(String key);

    /**
     * @param key
     *            {@link String}
     * @param string
     *            {@link String}
     */
    void setString(String key, String string);

    /**
     * @param key
     *            {@link String}
     * @return <code>boolean</code>
     */
    boolean getBoolean(String key);

    /**
     * @param key
     *            {@link String}
     * @param value
     *            <code>boolean</code>
     */
    void setBoolean(String key, boolean value);

    /**
     * @param key
     *            {@link String}
     * @return int
     */
    int getInt(String key);

    /**
     * @param key
     *            {@link String}
     * @param i
     *            int
     */
    void setInt(String key, int i);

    /**
     * @param key
     *            String
     * @return double
     */
    double getDouble(String key);

    /**
     * @param key
     *            String
     * @param d
     *            double
     */
    void setDouble(String key, double d);

    /**
     * @param key
     *            String
     * @return <code>float</code>
     */
    float getFloat(String key);

    /**
     * @param key
     *            String
     * @param f
     *            <code>float</code>
     */
    void setFloat(String key, float f);

    /**
     * @param key
     *            String
     * @return Color
     */
    Color getColor(String key);

    /**
     * @param key
     *            String
     * @param c
     *            Color
     */
    void setColor(String key, Color c);

    /**
     * @param key
     *            {@link String}
     * @param object
     *            {@link Serializable}
     */
    void setSerializable(String key, Serializable object);

    /**
     * @param key
     *            {@link String}
     * @return {@link Serializable}
     */
    Serializable getSerializable(String key);

    /**
     * reads the properties from a given file.
     * 
     * @param propertiesFile
     *            {@link File}
     * @throws IOException
     *             if problems reading config file occur
     */
    void readPropertiesFromFile(File propertiesFile) throws IOException;

    /**
     * saves the properties to a given file.
     * 
     * @param propertiesFile
     *            {@link File}
     * @throws IOException
     *             if problems writing config file occur
     */
    void savePropertiesToFile(File propertiesFile) throws IOException;

    /**
     * Returns a {@link java.util.Enumeration}of keys of those properties which
     * have been adapted and take a different value from the default value.
     * 
     * @return a {@link java.util.Enumeration}of <code>Strings</code>
     */
    Enumeration<?> keysOfAdaptedProperties();

    /**
     * Returns a {@link java.util.Enumeration}of values of those properties
     * which have been adapted and take a different value from the default
     * value.
     * 
     * @return a {@link java.util.Enumeration}of <code>Strings</code>
     *         representing the encoded element values. Note that these values
     *         have to be decoded manually!
     */
    Enumeration<?> elementsOfAdaptedProperties();

}
/*
 * $Log: ConfigResource.java,v $
 * Revision 1.2  2009-02-09 13:15:14  sweiss
 * clean up of stuff
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 * 
 * Revision 1.12 2006/04/10 15:34:12 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.11 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * Revision 1.10 2005/10/26 16:56:23 mschneiderTFH start of very big clean up
 * and commenting! (sw)
 */
