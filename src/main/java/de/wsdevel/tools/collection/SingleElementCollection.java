package de.wsdevel.tools.collection;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created on 20.06.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.4 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @param <A>
 *            type of this collection
 */
public class SingleElementCollection<A> extends AbstractCollection<A> implements
		Collection<A> {
	/**
	 * the single element.
	 */
	private A element = null;

	/**
	 * @param elementVal
	 *            {@link Object}
	 */
	public SingleElementCollection(final A elementVal) {
		super();
		this.element = elementVal;
	}

	/**
	 * @return <code>int</code>
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public final int size() {
		return 1;
	}

	/**
	 * @return {@link Iterator}
	 * @see java.util.AbstractCollection#iterator()
	 */
	@Override
	public final Iterator<A> iterator() {
		return new Iterator<A>() {

			private boolean hasAllreadyReturned = false;

			public void remove() {
				throw new UnsupportedOperationException();
			}

			public boolean hasNext() {
				return !this.hasAllreadyReturned;
			}

			public A next() {
				if (!this.hasAllreadyReturned) {
					this.hasAllreadyReturned = true;
					return SingleElementCollection.this.element;
				}
				throw new NoSuchElementException("allreday returned value");
			}
		};
	}

}
//
// $Log: SingleElementCollection.java,v $
// Revision 1.4  2009-02-09 16:54:26  sweiss
// bug fixing and cleanup
//
// Revision 1.3 2006-06-10 13:00:32 sweissTFH
// cleanup and smaller changes due to new compiler settings
// 
// Revision 1.2 2006/05/12 15:37:03 sweissTFH
// using typing now
// 
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned up tools and moved
// everything to appropriate new packages
// 
// Revision 1.4 2006/04/05 18:19:34 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.3 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.2 2005/10/26 16:56:23 mschneiderTFH
// start of very big clean up and
// commenting! (sw)
// 
// Revision 1.1 2005/06/20 16:22:14 sweissTFH
// empty log message
//
