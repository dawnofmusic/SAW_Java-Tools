/**
 * Class:    Frame<br/>
 * <br/>
 * Created:  20.04.2013<br/>
 * Filename: Frame.java<br/>
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

package de.wsdevel.tools.streams.container;

/**
 * Frame.
 * 
 * Marker Interface for frames contained in a Container<I/O>Stream
 */
public interface Frame {

    /**
     * getDuration.
     * 
     * @return {@code long} duration of frame in nanoseconds or {@code -1} if
     *         duration is not given.
     */
    long getDurationNanos();

    /**
     * getSize.
     * 
     * @return <code>int</code>
     */
    int getSize();

    /**
     * toBytes.
     * 
     * @return <code>byte[]</code>. The serialized frame.
     */
    byte[] toBytes();

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================