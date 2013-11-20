/**
 * Class:    DevNullSegmentCache<br/>
 * <br/>
 * Created:  18.11.2013<br/>
 * Filename: DevNullSegmentCache.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) 2013 Sebastian A. Weiss - All rights reserved.
 */

package de.wsdevel.tools.streams.buffer;

import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * DevNullSegmentCache
 */
public class DevNullSegmentCache<F extends Frame, S extends Segment<F>> extends
	SegmentCache<F, S> {

    /**
     * getSegmentForSequenceNumber.
     * 
     * @see de.wsdevel.tools.streams.buffer.SegmentCache#getSegmentForSequenceNumber(int)
     * @param sequenceNumber
     * @return
     */
    @Override
    public S getSegmentForSequenceNumber(final int sequenceNumber) {
	return null;
    }

    /**
     * getSegmentForTimestamp.
     * 
     * @see de.wsdevel.tools.streams.buffer.SegmentCache#getSegmentForTimestamp(java.lang.Long)
     * @param timestamp
     *            <code>long</code>F
     * @return null. Always.
     */
    @Override
    public S getSegmentForTimestamp(final Long timestamp) {
	return null;
    }

    /**
     * offer.
     * 
     * @see de.wsdevel.tools.streams.buffer.SegmentCache#offer(de.wsdevel.tools.streams.container.Segment,
     *      long)
     * @param e
     *            <code>S</code>
     * @param timestamp
     *            <code>long</code>
     * @return <code>boolean</code> <code>true</code>. Always.
     */
    @Override
    public boolean offer(final S e, final long timestamp) {
	return true;
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================