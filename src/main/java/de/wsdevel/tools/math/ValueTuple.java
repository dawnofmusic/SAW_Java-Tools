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
     * {@link Double} a
     */
    private Double a = null;

    /**
     * {@link Double} b
     */
    private Double b = null;

    /**
     * Default constructor.
     */
    public ValueTuple() {
	// nothing to do;
    }

    /**
     * ValueTuple constructor.
     * 
     * @param aVal
     *            {@link Double}
     * @param bVal
     *            {@link Double}
     */
    public ValueTuple(final Double aVal, final Double bVal) {
	setA(aVal);
	setB(bVal);
    }

    /**
     * @return the {@link Double} a
     */
    public Double getA() {
	return this.a;
    }

    /**
     * @return the {@link Double} b
     */
    public Double getB() {
	return this.b;
    }

    /**
     * @param a
     *            {@link Double} the a to set
     */
    public void setA(final Double a) {
	this.a = a;
    }

    /**
     * @param b
     *            {@link Double} the b to set
     */
    public void setB(final Double b) {
	this.b = b;
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
