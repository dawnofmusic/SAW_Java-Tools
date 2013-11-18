package de.wsdevel.tools.math;

/**
 * Created on 27.03.2009.
 * 
 * (c) 2008, Sebastian A. Weiss - All rights reserved.
 * 
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author$ -- $Revision$ -- $Date$
 */
public class ValueTuple {

    /**
     * <code>double</code> a.
     */
    private double a = 0;

    /**
     * <code>double</code> b.
     */
    private double b = 0;

    /**
     * Default constructor.
     */
    public ValueTuple() {
	// nothing to do;
    }

    /**
     * @param aVal
     *            <code>double</code>
     * @param bVal
     *            <code>double</code>
     */
    public ValueTuple(final double aVal, final double bVal) {
	setA(aVal);
	setB(bVal);
    }

    /**
     * @return {@link double} the a.
     */
    public final double getA() {
	return this.a;
    }

    /**
     * @return {@link double} the b.
     */
    public final double getB() {
	return this.b;
    }

    /**
     * @param aVal
     *            {@link double} the a to set.
     */
    public final void setA(double aVal) {
	this.a = aVal;
    }

    /**
     * @param bVal
     *            {@link double} the b to set.
     */
    public final void setB(double bVal) {
	this.b = bVal;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("nls")
    @Override
    public final String toString() {
	return ValueTuple.class.getSimpleName() + "[a: " + getA() + ", b: "
		+ getB() + "]";
    }

}
//
// $Log$
// Revision 1.1 2009-03-28 16:09:24 sweiss
// added a lot of graph stuff
//
//
