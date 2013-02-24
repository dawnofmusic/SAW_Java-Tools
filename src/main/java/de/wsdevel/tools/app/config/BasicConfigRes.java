package de.wsdevel.tools.app.config;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 08.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.8 $ -- $Date: 2005/10/26 16:56:23
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public class BasicConfigRes implements ConfigResource {

    /**
     * {@link Log}.
     */
    private static final Log LOG = LogFactory.getLog(BasicConfigRes.class);

    /**
     * {@link Properties} COMMENT.
     */
    private Properties properties = new Properties();

    /**
     * {@link Properties} default this.properties used if property is not set
     * manually.
     */
    private Properties defaultProperties = new Properties();

    /**
     * header to be printed in first row of config file.
     */
    private String header = "";

    /**
     * {@link SerializableEnDecoder}the en-/decoder used for serializables.
     */
    private SerializableEnDecoder serializableEnDecoder = null;

    /**
     * Generates a new instance.
     * 
     * @param headerVal
     *            {@link java.lang.String}to be written in config file
     */
    public BasicConfigRes(final String headerVal) {
	this(headerVal, new BasicSerializableEnDecoder());
    }

    /**
     * @param headerVal
     *            {@link java.lang.String}to be written in config file
     * @param serializableEnDecoderVal
     *            en-/decoder used for Serializables
     */
    public BasicConfigRes(final String headerVal,
	    final SerializableEnDecoder serializableEnDecoderVal) {
	this.serializableEnDecoder = serializableEnDecoderVal;
	this.initDefaultProperties(this.defaultProperties);
	this.header = headerVal;
    }

    /**
     * to be overwritten, to initialize default this.properties.
     * 
     * @param defaultPropertiesVal
     *            {@link java.util.Properties}
     */
    public void initDefaultProperties(final Properties defaultPropertiesVal) {
    }

    /**
     * @param propertiesFile
     *            {@link File}
     * @throws IOException
     *             COMMENT
     * @see de.wsdevel.tools.app.config.ConfigResource#readPropertiesFromFile(java.io.File)
     */
    public final void readPropertiesFromFile(final File propertiesFile)
	    throws IOException {
	LOG.debug("reading this.properties from file " + propertiesFile
		+ " ...");
	InputStream in = propertiesFile.toURI().toURL().openStream();
	this.properties.loadFromXML(in);
	in.close();
	if (LOG.isDebugEnabled()) {
	    LOG.debug("... ok!");
	    LOG.debug("_properties is " + this.properties);
	    LOG.debug("_properties contains " + this.properties.size()
		    + " elements");
	    Enumeration<?> keys = this.properties.keys();
	    while (keys.hasMoreElements()) {
		String key = (String) keys.nextElement();
		LOG.debug("key: " + key + "  value: "
			+ this.properties.getProperty(key));
	    }
	}
    }

    /**
     * @param propertiesFile
     *            {@link File}
     * @throws IOException
     *             COMMENT
     * @see de.wsdevel.tools.app.config.ConfigResource#savePropertiesToFile(java.io.File)
     */
    public final void savePropertiesToFile(final File propertiesFile)
	    throws IOException {
	savePropertiesToFile(propertiesFile, this.properties);
    }

    /**
     * @param propertiesFile
     *            File
     * @param props
     *            Properties
     * @throws IOException
     *             while problems during storing
     */
    protected final void savePropertiesToFile(final File propertiesFile,
	    final Properties props) throws IOException {
	OutputStream out = new FileOutputStream(propertiesFile);
	try {
	    props.storeToXML(out, this.header);
	} finally {
	    out.close();
	}
    }

    /**
     * @param url
     *            URL
     * @throws IOException
     *             if url could not be opened
     */
    public final void readPropertiesFromURL(final URL url) throws IOException {
	InputStream in = url.openStream();
	this.properties.loadFromXML(in);
	in.close();
	in = null;
    }

    /**
     * Saves the current this.properties to the specified file; all
     * this.properties are saved, individually set ones as well as
     * this.properties with default settings.
     * 
     * @param propertiesFile
     *            the file to save to
     * @throws IOException
     *             if file could not be stored
     */
    public final void saveAllPropertiesToFile(final File propertiesFile)
	    throws IOException {
	savePropertiesToFile(propertiesFile, getAllProperties());
    }

    /**
     * COMMENT.
     * 
     * @param file
     *            {@link File}
     */
    public final void init(final File file) {
	try {
	    readPropertiesFromFile(file);
	} catch (IOException e1) {
	    LOG
		    .warn(e1.getLocalizedMessage(), LOG.isDebugEnabled() ? e1
			    : null);
	    try {
		saveAllPropertiesToFile(file);
	    } catch (IOException e) {
		LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e
			: null);
	    }
	}
    }

    /**
     * @return Properties of this config resource
     */
    protected final Properties getProperties() {
	return this.properties;
    }

    /**
     * Returns all this.properties of this configuration including default
     * settings.
     * 
     * @return all this.properties in as a {@link java.util.Properties}
     *         collection
     */
    public final Properties getAllProperties() {
	Properties allProperties = (Properties) this.properties.clone();

	// copy default this.properties to this.properties and perform a save
	// then
	Enumeration<?> propertiesInDefaultsEnum = this.defaultProperties
		.propertyNames();
	while (propertiesInDefaultsEnum.hasMoreElements()) {
	    String propertyName = (String) propertiesInDefaultsEnum
		    .nextElement();
	    LOG.debug("found property in default this.properties:"
		    + propertyName);
	    LOG.debug("value is:"
		    + this.defaultProperties.getProperty(propertyName));
	    if (allProperties.getProperty(propertyName) == null) {
		LOG
			.debug("copying default property to this.properties to save:"
				+ propertyName);
		LOG.debug("value is:"
			+ this.defaultProperties.getProperty(propertyName));
		allProperties.setProperty(propertyName, this.defaultProperties
			.getProperty(propertyName));
	    }
	}
	return allProperties;
    }

    /**
     * @param key
     *            {@link String}
     * @return {@link Object}
     * @see de.wsdevel.tools.app.config.ConfigResource#getProperty(java.lang.String)
     */
    public final Object getProperty(final String key) {
	Object prop = this.properties.getProperty(key);
	if (prop == null) {
	    prop = this.defaultProperties.getProperty(key);
	}
	// if (prop == null) {
	// throw new IllegalArgumentException("No Property set for " + key);
	// }
	return prop;
    }

    /**
     * @param key
     *            String
     * @param prop
     *            Object
     */
    public final void setProperty(final String key, final Object prop) {
	this.properties.put(key, prop);
    }

    /**
     * @param key
     *            String
     * @return String value for key
     */
    public final String getString(final String key) {
	Object property = getProperty(key);
	return (property == null) ? null : property.toString();
    }

    /**
     * @param key
     *            String
     * @param string
     *            String
     */
    public final void setString(final String key, final String string) {
	setProperty(key, string);
    }

    /**
     * @param key
     *            {@link String}
     * @return <code>boolean</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#getBoolean(java.lang.String)
     */
    public final boolean getBoolean(final String key) {
	return Boolean.valueOf(getString(key).trim()).booleanValue();
    }

    /**
     * @param key
     *            {@link String}
     * @param value
     *            <code>boolean</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#setBoolean(java.lang.String,
     *      boolean)
     */
    public final void setBoolean(final String key, final boolean value) {
	setString(key, encodeBooleanToString(value));
    }

    /**
     * COMMENT.
     * 
     * @param value
     *            <code>boolean</code>
     * @return {@link String}
     */
    protected final String encodeBooleanToString(final boolean value) {
	return Boolean.toString(value);
    }

    /**
     * @param key
     *            {@link String}
     * @return <code>int</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#getInt(java.lang.String)
     */
    public final int getInt(final String key) {
	return decodeIntFromString(getString(key));
    }

    /**
     * Decodes a <code>int</code> value from a simple <code>String</code>
     * encoding.
     * 
     * @param string
     *            {@link String}
     * @return <code>int</code>
     */
    protected final int decodeIntFromString(final String string) {
	return Integer.parseInt(string);
    }

    /**
     * @param key
     *            {@link String}
     * @param i
     *            <code>int</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#setInt(java.lang.String,
     *      int)
     */
    public final void setInt(final String key, final int i) {
	setString(key, encodeIntToString(i));
    }

    /**
     * Generates a simple <code>String</code> encoding for a <code>int</code>
     * value. Different from the encoding a serialization might produce, this
     * encoding is not meant to be general. Instead, it is a readable encoding
     * suitable for editing by a user, and, thus, better suited to be utilized
     * in a this.properties file.
     * 
     * @param i
     *            <code>int</code>
     * @return {@link String}
     */
    protected static final String encodeIntToString(final int i) {
	return Integer.toString(i);
    }

    /**
     * @param key
     *            {@link String}
     * @return <code>double</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#getDouble(java.lang.String)
     */
    public final double getDouble(final String key) {
	double value = decodeDoubleFromString(getString(key));
	LOG.debug("returning " + value + " for key " + key);
	return value;
    }

    /**
     * Decodes a <code>double</code> value from a simple <code>String</code>
     * encoding.
     * 
     * @param encodedDouble
     *            String
     * @return the <code>double</code> value
     */
    protected double decodeDoubleFromString(final String encodedDouble) {
	return Double.parseDouble(encodedDouble);
    }

    /**
     * @param key
     *            {@link String}
     * @param d
     *            <code>double</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#setDouble(java.lang.String,
     *      double)
     */
    public final void setDouble(final String key, final double d) {
	setString(key, encodeDoubleToString(d));
    }

    /**
     * Generates a simple <code>String</code> encoding for a <code>double</code>
     * value. Different from the encoding a serialization might produce, this
     * encoding is not meant to be general. Instead, it is a readable encoding
     * suitable for editing by a user, and, thus, better suited to be utilized
     * in a this.properties file.
     * 
     * @param d
     *            the <code>double</code> value to encode
     * @return a <code>String</code> representing the encoding
     */
    protected final String encodeDoubleToString(final double d) {
	return Double.toString(d);
    }

    /**
     * @param key
     *            {@link String}
     * @return <code>float</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#getFloat(java.lang.String)
     */
    public final float getFloat(final String key) {
	if (LOG.isDebugEnabled()) {
	    LOG.debug("key to get float for: [" + key + "]");
	}
	return decodeFloatFromString(getString(key));
    }

    /**
     * COMMENT.
     * 
     * @param string
     *            {@link String}
     * @return <code>float</code>
     */
    protected final float decodeFloatFromString(final String string) {
	if (LOG.isDebugEnabled()) {
	    LOG.debug("string to be decoded: [" + string + "]");
	}
	return Float.parseFloat(string);
    }

    /**
     * @param key
     *            {@link String}
     * @param f
     *            <code>float</code>
     * @see de.wsdevel.tools.app.config.ConfigResource#setFloat(java.lang.String,
     *      float)
     */
    public final void setFloat(final String key, final float f) {
	setString(key, encodeFloatToString(f));
    }

    /**
     * COMMENT.
     * 
     * @param f
     *            <code>float</code>
     * @return {@link String}
     */
    protected final String encodeFloatToString(final float f) {
	return Float.toString(f);
    }

    /**
     * Extracts a {@link java.awt.Color}for the specified key from the
     * this.properties.
     * 
     * @param key
     *            {@link String}
     * @return a {@link java.awt.Color}
     */
    public final Color getColor(final String key) {
	try {
	    Color col = decodeColorFromString(getString(key));
	    LOG.debug("returning " + col + " for key " + key);
	    return col;
	} catch (IllegalArgumentException ipve) {
	    LOG
		    .warn("could not extract color value from this.properties for key "
			    + key + ": " + ipve);
	}
	LOG.debug("returning null for key " + key);
	return null;
    }

    /**
     * Inserts a {@link java.awt.Color}value with the given key into the
     * this.properties.
     * 
     * @param key
     *            String
     * @param col
     *            {@link java.awt.Color}
     */
    public final void setColor(final String key, final Color col) {
	setString(key, encodeColorToString(col));
    }

    /**
     * Generates a simple <code>String</code> encoding for a
     * {@link java.awt.Color}value. Different from the encoding a serialization
     * might produce, this encoding is not meant to be general. Instead, it is a
     * readable encoding suitable for editing by a user, and, thus, better
     * suited to be utilized in a this.properties file.
     * 
     * @param col
     *            {@link java.awt.Color}
     * @return the <code>String</code> encoding
     */
    protected final String encodeColorToString(final Color col) {
	return "[" + col.getRed() + "," + col.getGreen() + "," + col.getBlue()
		+ "," + col.getAlpha() + "]";
    }

    /**
     * Decodes a {@link java.awt.Color}value from a simple <code>String</code>
     * encoding.
     * 
     * @param encoded
     *            String
     * @return the {@link java.awt.Color}value
     * @throws IllegalArgumentException
     *             if <code>encoded</code> does not contain a valid simple
     *             <code>String</code> encoding.
     */
    protected final Color decodeColorFromString(final String encoded) {
	StringTokenizer strtok = new StringTokenizer(encoded.trim(), "[], ",
		true);
	String token = strtok.nextToken();
	if ((token == null) || (!token.trim().equals("["))) {
	    throw new IllegalArgumentException(
		    "incorrect color specifier in this.properties: " + encoded);
	}
	try {
	    // decode r value
	    token = strtok.nextToken();
	    int r = decodeIntFromString(token);

	    token = strtok.nextToken();
	    if ((token == null) || (!token.trim().equals(","))) {
		throw new IllegalArgumentException(
			"incorrect color specifier in this.properties: "
				+ encoded);
	    }

	    // decode g value
	    token = strtok.nextToken();
	    int g = decodeIntFromString(token);

	    token = strtok.nextToken();
	    if ((token == null) || (!token.trim().equals(","))) {
		throw new IllegalArgumentException(
			"incorrect color specifier in this.properties: "
				+ encoded);
	    }

	    // decode b value
	    token = strtok.nextToken();
	    int b = decodeIntFromString(token);

	    token = strtok.nextToken();
	    if ((token == null) || (!token.trim().equals(","))) {
		throw new IllegalArgumentException(
			"incorrect color specifier in this.properties: "
				+ encoded);
	    }

	    // decode alpha value
	    token = strtok.nextToken();
	    int alpha = decodeIntFromString(token);

	    token = strtok.nextToken();
	    if ((token == null) || (!token.trim().equals("]"))) {
		LOG.warn("color specifier in this.properties incomplete: "
			+ encoded);
	    }

	    return new Color(r, g, b, alpha);
	} catch (NumberFormatException nfe) {
	    throw new IllegalArgumentException(
		    "incorrect color specifier in this.properties: " + encoded);
	}

    }

    /**
     * @param key
     *            String
     * @param object
     *            Serializable
     */
    public final void setSerializable(final String key,
	    final Serializable object) {
	try {
	    setString(key, encodeSerializableToString(object));
	} catch (IOException e) {
	    LOG.error("Couldn't write object", e);
	}
    }

    /**
     * COMMENT.
     * 
     * @param object
     *            {@link Serializable}
     * @return {@link String}
     * @throws IOException
     *             COMMENT
     */
    protected final String encodeSerializableToString(final Serializable object)
	    throws IOException {
	return this.serializableEnDecoder.encodeSerializableToString(object);
    }

    /**
     * @param key
     *            {@link String}
     * @return {@link Serializable}
     * @see de.wsdevel.tools.app.config.ConfigResource#getSerializable(java.lang.String)
     */
    public final Serializable getSerializable(final String key) {
	String o = getString(key);
	if (o == null) {
	    return null;
	}
	try {
	    return this.serializableEnDecoder.decodeSerializableFromString(o);
	} catch (IOException e) {
	    LOG.error("Couldn't read object", e);
	} catch (ClassNotFoundException e1) {
	    LOG.error("no class found for object", e1);
	}
	return null;
    }

    /**
     * Returns a {java.util.Enumeration} of all keys of this.properties having a
     * non default value.
     * 
     * @return a {java.util.Enumeration} of <code>String</code> values
     */
    public final Enumeration<?> keysOfAdaptedProperties() {
	LOG.debug("this.properties is " + this.properties);
	LOG.debug("this.properties contains " + this.properties.size()
		+ " elements");
	return this.properties.keys();
    }

    /**
     * Returns a {java.util.Enumeration} of all property values having a non
     * default value.
     * 
     * @return a {java.util.Enumeration} of {@link Object}s
     */
    public final Enumeration<?> elementsOfAdaptedProperties() {
	return this.properties.elements();
    }

    /**
     * @return String the header.
     */
    public final String getHeader() {
	return this.header;
    }

    /**
     * @param headerVal
     *            String The header to set.
     */
    public final void setHeader(final String headerVal) {
	this.header = headerVal;
    }

    /**
     * @param file
     *            {@link File}
     */
    public final void initializeConfigRes(final File file) {
	try {
	    this.readPropertiesFromFile(file);
	} catch (IOException e1) {
	    LOG.info("no this.properties file found", LOG.isDebugEnabled() ? e1
		    : null);
	    try {
		this.saveAllPropertiesToFile(file);
	    } catch (IOException e3) {
		LOG
			.fatal(
				"could neather read from nor write to this.properties file!",
				e3);
		e3.printStackTrace();
	    }
	}
    }

}
//
// $Log: BasicConfigRes.java,v $
// Revision 1.8  2009-02-09 16:54:26  sweiss
// bug fixing and cleanup
//
// Revision 1.7 2009-02-09 13:15:15 sweiss
// clean
// up of stuff
// 
// Revision 1.6 2007-08-06 17:33:39 ischmidt
// switched to commmons
// logging
// 
// Revision 1.5 2006/05/26 22:09:37 sweissTFH
// small cleanup
// 
// Revision 1.4 2006/05/23 12:58:32 sweissTFH
// new xml properties file
// 
// Revision 1.3 2006/05/23 10:58:25 sweissTFH
// created default init method for
// BasicConfigRes
// 
// Revision 1.2 2006/05/23 10:26:05 sweissTFH
// added functionality to config and
// cleaned up a bit
// 
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned up tools and moved
// everything to appropriate new packages
// 
// Revision 1.25 2006/04/05 18:19:34 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.24 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big
// clean up!
// 
// Revision 1.23 2005/10/26 16:56:23 mschneiderTFH
// start of very big clean up
// and commenting! (sw)
// 
// Revision 1.22 2005/10/13 15:42:21 sweissTFH
// some enhancements concerning
// BasicConfigRes, as well cleaned up projects package
// 
// Revision 1.21 2005/10/13 11:28:10 sweissTFH
// fixes concerning compatibility
// with jdk 5.0
// 
// Revision 1.20 2005/08/26 18:07:43 mschneiderTFH
// added alpha channel to colors
// 
// Revision 1.19 2005/08/22 15:13:37 sweissTFH
// fixed bug in getBoolean
// 
// Revision 1.18 2005/08/22 14:32:20 sweissTFH
// added boolean
// 
// Revision 1.17 2005/08/17 16:05:01 sweissTFH
// some debugging stuff
// 
// Revision 1.16 2005/08/17 13:53:40 sweissTFH
// empty log message
// 
// Revision 1.15 2005/08/17 13:51:45 sweissTFH
// added float support Revision 1.14
// 2005/06/16 15:30:18 sweissTFH empty log message
// 
// Revision 1.13 2005/06/16 12:31:06 sweissTFH
// empty log message
// 
// Revision 1.12 2005/04/07 17:43:27 sweiss
// cleaned up a lot, new delegate for
// serializable encoding
// 
// Revision 1.11 2005/03/01 14:48:18 sweiss
// cleaned up a bit
// 
// Revision 1.10 2005/03/01 14:21:36 sweiss
// empty log message
// 
// Revision 1.8 2004/10/27 10:21:45 sweiss
// empty log message
//
