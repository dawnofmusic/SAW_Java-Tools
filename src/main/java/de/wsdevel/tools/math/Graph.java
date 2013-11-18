package de.wsdevel.tools.math;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 27.03.2009.
 * 
 * (c) 2008, Sebastian A. Weiss - All rights reserved.
 * 
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author$ -- $Revision$ -- $Date$
 */
public class Graph {

    /**
     * {@link int} DEFAULT_MAX_NUMBER_OF_VALUES
     */
    private static final int DEFAULT_MAX_NUMBER_OF_VALUES = 1000;

    /**
     * {@link Log} the logger.
     */
    private static final Log LOG = LogFactory.getLog(Graph.class);

    /**
     * {@link GraphListenerSupport} COMMENT.
     */
    private final GraphListenerSupport gls = new GraphListenerSupport();

    /**
     */
    private double maxA = 0;

    /**
     */
    private double maxB = 0;

    /**
     * {@link int} maxNumberOfValues
     */
    private int maxNumberOfValues = Graph.DEFAULT_MAX_NUMBER_OF_VALUES;

    /**
     */
    private double minB = 0;

    /**
     * {@link ArrayBlockingQueue<ValueTuple>} COMMENT.
     */
    private ArrayBlockingQueue<ValueTuple> tuples = new ArrayBlockingQueue<ValueTuple>(
	    Graph.DEFAULT_MAX_NUMBER_OF_VALUES);

    /**
     * Graph constructor.
     */
    public Graph() {
    }

    /**
     * @param listener
     *            {@link GraphListener}
     * @see de.wsdevel.tools.math.GraphListenerSupport#addListener(de.wsdevel.tools.math.GraphListener)
     */
    public final void addListener(final GraphListener listener) {
	this.gls.addListener(listener);
    }

    /**
     * @param tuple
     *            {@link ValueTuple}
     */
    @SuppressWarnings("nls")
    public final void addTuple(final ValueTuple tuple) {
	synchronized (this.tuples) {
	    if (this.tuples.offer(tuple)) {
		if (Graph.LOG.isDebugEnabled()) {
		    Graph.LOG.debug("tuple added: " + tuple);
		}
		if (tuple.getA() > this.maxA) {
		    this.maxA = tuple.getA();
		}
		if (tuple.getB() > this.maxB) {
		    this.maxB = tuple.getB();
		}
		if (tuple.getB() < this.minB) {
		    this.minB = tuple.getB();
		}
		if (this.tuples.remainingCapacity() < 1) {
		    // (20131104 saw) assume a is always increasing, remove
		    // first
		    // tuple
		    this.tuples.poll();
		}
	    }
	}
	this.gls.fireGraphChanged();
    }

    /**
     * COMMENT.
     * 
     * @return <code>double</code>
     */
    public final double getDeltaA() {
	return getMaxA() - getMinA();
    }

    /**
     * COMMENT.
     * 
     * @return <code>double</code>
     */
    public final double getDeltaB() {
	return getMaxB() - getMinB();
    }

    /**
     * @return <code>double</code>
     */
    public final double getMaxA() {
	return this.maxA;
    }

    /**
     * @return <code>double</code>
     */
    public final double getMaxB() {
	return this.maxB;
    }

    /**
     * Returns the maxNumberOfValues.
     * 
     * @return {@link int}
     */
    public int getMaxNumberOfValues() {
	return this.maxNumberOfValues;
    }

    /**
     * @return <code>double</code>
     */
    public final double getMinA() {
	synchronized (this.tuples) {
	    final ValueTuple peekFirst = this.tuples.peek();
	    if (peekFirst != null) {
		return peekFirst.getA();
	    }
	}
	return 0;
    }

    /**
     * @return <code>double</code>
     */
    public final double getMinB() {
	return this.minB;
    }

    /**
     * @return {@link ArrayBlockingQueue<ValueTuple>} the tuples.
     */
    public final ArrayBlockingQueue<ValueTuple> getTuples() {
	return this.tuples;
    }

    /**
     * @param listener
     *            {@link GraphListener}
     * @see de.wsdevel.tools.math.GraphListenerSupport#removeListener(de.wsdevel.tools.math.GraphListener)
     */
    public final void removeListener(final GraphListener listener) {
	this.gls.removeListener(listener);
    }

    /**
     * Sets the maxNumberOfValues.
     * 
     * @param maxNumberOfValues
     *            {@link int}
     */
    public void setMaxNumberOfValues(final int maxNumberOfValues) {
	if (this.tuples != null) {
	    synchronized (this.tuples) {
		final ArrayBlockingQueue<ValueTuple> oldTuples = this.tuples;
		this.tuples = new ArrayBlockingQueue<ValueTuple>(
			maxNumberOfValues);
		this.tuples.addAll(oldTuples);
		this.maxNumberOfValues = maxNumberOfValues;
	    }
	} else {
	    synchronized (this.tuples) {
		this.tuples = new ArrayBlockingQueue<ValueTuple>(
			maxNumberOfValues);
		this.maxNumberOfValues = maxNumberOfValues;
	    }
	}
    }

    /**
     * @param tuplesRef
     *            {@link ArrayBlockingQueue<ValueTuple>} the tuples to set.
     */
    public final void setTuples(final ArrayBlockingQueue<ValueTuple> tuplesRef) {
	if (this.tuples != null) {
	    synchronized (this.tuples) {
		this.tuples = tuplesRef;
		this.maxNumberOfValues = this.tuples.size()
			+ this.tuples.remainingCapacity();
	    }
	} else {
	    this.tuples = tuplesRef;
	    this.maxNumberOfValues = this.tuples.size()
		    + this.tuples.remainingCapacity();
	}
    }

}