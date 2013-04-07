package de.wsdevel.tools.awt.model;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ListModel;

import de.wsdevel.tools.awt.model.ListDataListenerSupport.ObserverType;

/**
 * Created on 08.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.3 $ -- $Date: 2005/10/26 16:56:23
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @param <K>
 * @param <V>
 * 
 */
public class MapWithListModelImpl<K, V> extends AbstractMap<K, V> implements
		MapWithListModel<K, V> {

	/**
	 * Created on 26.10.2005.
	 * 
	 * for project: tools
	 * 
	 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss -
	 *         Weiss und Schmidt, Mediale Systeme GbR</a>
	 * @version $Author: sweiss $ -- $Revision: 1.3 $ -- $Date: 2005/10/26
	 *          16:56:23 $
	 * 
	 * <br>
	 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
	 *          reserved.
	 * 
	 */
	private class MyEntrySet extends AbstractSet<Entry<K, V>> implements
			Set<Entry<K, V>> {

		/**
		 * @return {@link Iterator}
		 * @see java.util.Collection#iterator()
		 */
		@Override
		public Iterator<Entry<K, V>> iterator() {
			final Iterator<Entry<K, V>> it = MapWithListModelImpl.this.delegateMap
					.entrySet().iterator();
			return new Iterator<Entry<K, V>>() {
				private Entry<K, V> last;

				public boolean hasNext() {
					return it.hasNext();
				}

				public Entry<K, V> next() {
					this.last = it.next();
					return this.last;
				}

				public void remove() {
					it.remove();
					MapWithListModelImpl.this.referenceList.remove(this.last
							.getValue());
				}
			};
		}

		/**
		 * @return <code>int</code>
		 * @see java.util.Collection#size()
		 */
		@Override
		public int size() {
			return MapWithListModelImpl.this.delegateMap.entrySet().size();
		}

	}

	/**
	 * {@link Map} COMMENT.
	 */
	private Map<K, V> delegateMap;

	/**
	 * {@link ListWithListModel} COMMENT.
	 */
	private ListWithListModel<V> referenceList;

	/**
	 * {@link Set} COMMENT.
	 */
	private MyEntrySet entrySet;

	/**
	 * COMMENT.
	 * 
	 */
	public MapWithListModelImpl() {
		this(new HashMap<K, V>());
	}

	/**
	 * COMMENT.
	 * 
	 * @param map
	 *            {@link Map}
	 */
	public MapWithListModelImpl(final Map<K, V> map) {
		this.delegateMap = map;
		this.referenceList = new ListWithListModelImpl<V>();
		this.entrySet = new MyEntrySet();
	}

	/**
	 * COMMENT.
	 * 
	 * @param map
	 *            {@link Map}
	 */
	public MapWithListModelImpl(final Map<K, V> map,
			final ObserverType observerType) {
		this.delegateMap = map;
		this.referenceList = new ListWithListModelImpl<V>(observerType);
		this.entrySet = new MyEntrySet();
	}

	/**
	 * COMMENT.
	 * 
	 */
	public MapWithListModelImpl(final ObserverType observerType) {
		this(new HashMap<K, V>(), observerType);
	}

	/**
	 * @return {@link Set}
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public final Set<Entry<K, V>> entrySet() {
		return this.entrySet;
	}

	/**
	 * @return {@link ListModel}
	 * @see de.wsdevel.tools.awt.model.MapWithListModel#getListModel()
	 */
	public final ListModel<V> getListModel() {
		return this.referenceList.getListModel();
	}

	/**
	 * @param key
	 *            K
	 * @param value
	 *            V
	 * @return V
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public final V put(final K key, final V value) {
		final V oldValue = this.delegateMap.put(key, value);
		this.referenceList.add(value);
		return oldValue;
	}

}
/*
 * $Log: MapWithListModelImpl.java,v $ Revision 1.3 2009-02-09 13:15:13 sweiss
 * clean up of stuff Revision 1.2 2006/06/10 13:00:32 sweissTFH cleanup and
 * smaller changes due to new compiler settings
 * 
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.5 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.4 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 * Revision 1.3 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 */
