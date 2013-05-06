/**
 * Class:	BufferBehaviour<br/>
 * <br/>
 * Created:	02.05.2013<br/>
 * Filename: BufferBehaviour.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.buffer;

/**
 * BufferBehaviour
 */
public enum BufferBehavior {

    /**
     * {@link BufferBehaviour} The shaping. Use this behavior, if the buffer
     * shall shape the outgoing traffic according to the set target bandwidth.
     * 
     * @see Buffer#setCurrentTargetBandwidth(long)
     */
    trafficShapingBlockingBuffer,

    /**
     * {@link BufferBehaviour} The fast. Use this behavior if the buffered data
     * should be accessed as fast as needed.
     */
    fastAccessRingBuffer;

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================