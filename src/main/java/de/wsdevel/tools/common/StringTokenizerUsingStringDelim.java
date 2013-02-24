package de.wsdevel.tools.common;

import java.util.Enumeration;

/**
 * Created on 19.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public class StringTokenizerUsingStringDelim implements Enumeration<String> {

    /**
     * {@link String} COMMENT.
     */
    private String stringToBeTokenized;

    /**
     * {@link String} COMMENT.
     */
    private String delim;

    /**
     * {@link String} COMMENT.
     */
    private String nextToken = null;

    /**
     * <code>boolean</code> COMMENT.
     */
    private boolean hasMoreElements = false;

    /**
     * <code>int</code> COMMENT.
     */
    private int minIndex = 0;

    /**
     * COMMENT.
     * 
     * @param stringToBeTokenizedVal
     *            {@link String}
     * @param delimVal
     *            {@link String}
     */
    public StringTokenizerUsingStringDelim(final String stringToBeTokenizedVal,
	    final String delimVal) {
	this.stringToBeTokenized = stringToBeTokenizedVal;
	this.delim = delimVal;
	initNextToken();
    }

    /**
     * @return <code>boolean</code>
     * @see java.util.Enumeration#hasMoreElements()
     */
    public final boolean hasMoreElements() {
	return this.hasMoreElements;
    }

    /**
     * COMMENT.
     */
    private void initNextToken() {
	int firstOccurance;
	if (this.minIndex >= this.stringToBeTokenized.length()) {
	    this.nextToken = null;
	    this.hasMoreElements = false;
	} else {
	    firstOccurance = this.stringToBeTokenized.indexOf(this.delim,
		    this.minIndex);
	    if (firstOccurance > this.minIndex) {
		this.nextToken = this.stringToBeTokenized.substring(
			this.minIndex, firstOccurance);
		increaseMinIndex(firstOccurance);
		this.hasMoreElements = true;
	    } else if (firstOccurance == this.minIndex) {
		increaseMinIndex(firstOccurance);
		initNextToken();
	    } else if (firstOccurance == -1) {
		this.nextToken = this.stringToBeTokenized.substring(
			this.minIndex, this.stringToBeTokenized.length());
		this.minIndex = this.stringToBeTokenized.length();
		this.hasMoreElements = true;
	    } else {
		this.nextToken = null;
		this.hasMoreElements = false;
	    }
	}
    }

    /**
     * COMMENT.
     * 
     * @param firstOccurance
     *            <code>int</code>
     */
    private void increaseMinIndex(final int firstOccurance) {
	this.minIndex = firstOccurance + this.delim.length();
    }

    /**
     * @return {@link String}
     * @see java.util.Enumeration#nextElement()
     */
    public final String nextElement() {
	String o = this.nextToken;
	initNextToken();
	return o;
    }

}
// 
// $Log: StringTokenizerUsingStringDelim.java,v $
// Revision 1.2  2009-02-09 13:15:15  sweiss
// clean up of stuff
//
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned up tools and moved everything to appropriate new
// packages
// 
// Revision 1.4 2006/04/05 18:19:35 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.3 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.2 2005/10/31 18:22:30 sweissTFH
// clean up and commenting
//
