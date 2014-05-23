/**
 * Class:    ContainerInputStream<br/>
 * <br/>
 * Created:  20.04.2013<br/>
 * Filename: ContainerInputStream.java<br/>
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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ContainerInputStream
 * 
 * @param <code>T</code> any type extending {@link Frame}
 */
public abstract class ContainerInputStream<F extends Frame, S extends Segment<F>>
	extends DataInputStream {

    /**
     * ContainerInputStream constructor.
     */
    public ContainerInputStream(final InputStream innerIsRef) {
	super(innerIsRef);
    }

    /**
     * readFrame.
     * 
     * @return <code>T</code>
     * @throws IOException
     */
    public abstract F readFrame() throws IOException;

    // /**
    // * readFrames.
    // *
    // * @param frames
    // * <code>T[]</code> to store frames into.
    // * @param off
    // * <code>int</code> offset to start writing from.
    // * @param len
    // * <code>int</code> number of frames to be read.
    // * @return <code>int</code> the number of frames read or <code>-1</code>
    // if
    // * end of stream was reached.
    // * @throws IOException
    // */
    // public abstract int readFrames(F[] frames, int off, int len)
    // throws IOException;

    // /**
    // * readSegment.
    // *
    // * @param numberOfFrames
    // * <code>int</code>
    // * @return {@link Segment}
    // * @throws IOException
    // */
    // public abstract S readSegment(int numberOfFrames) throws IOException;

    /**
     * readSegment.
     * 
     * @return {@link Segment}
     * @throws IOException
     */
    public abstract S readSegment() throws IOException;

}
// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================