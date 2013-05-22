/**
 * Class:	ContainerTeeOutputStream<br/>
 * <br/>
 * Created:	25.04.2013<br/>
 * Filename: ContainerTeeOutputStream.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.container;

import java.io.IOException;

/**
 * ContainerTeeOutputStream
 */
public class ContainerTeeOutputStream<F extends Frame, S extends Segment<F>>
	extends ContainerOutputStream<F, S> {

    /** {@link ContainerOutputStream<T>} The cos1. */
    private ContainerOutputStream<F, S> cos1;

    /** {@link ContainerOutputStream<T>} The cos2. */
    private ContainerOutputStream<F, S> cos2;

    /**
     * ContainerTeeOutputStream constructor.
     */
    public ContainerTeeOutputStream() {
    }

    /**
     * close
     * 
     * @throws IOException
     * @see de.wsdevel.tools.streams.container.ContainerOutputStream#close()
     */
    @Override
    public void close() throws IOException {
	if (this.cos1 != null) {
	    this.cos1.close();
	}
	if (this.cos2 != null) {
	    this.cos2.close();
	}
    }

    /**
     * flush
     * 
     * @throws IOException
     * @see de.wsdevel.tools.streams.container.ContainerOutputStream#flush()
     */
    @Override
    public void flush() throws IOException {
	if (this.cos1 != null) {
	    this.cos1.flush();
	}
	if (this.cos2 != null) {
	    this.cos2.flush();
	}
    }

    /**
     * Returns the cos1.
     * 
     * @return {@link ContainerOutputStream<T>}
     */
    public ContainerOutputStream<F, S> getCos1() {
	return this.cos1;
    }

    /**
     * Returns the cos2.
     * 
     * @return {@link ContainerOutputStream<T>}
     */
    public ContainerOutputStream<F, S> getCos2() {
	return this.cos2;
    }

    /**
     * Sets the cos1.
     * 
     * @param cos1
     *            {@link ContainerOutputStream<T>}
     */
    public void setCos1(final ContainerOutputStream<F, S> cos1) {
	this.cos1 = cos1;
    }

    /**
     * Sets the cos2.
     * 
     * @param cos2
     *            {@link ContainerOutputStream<T>}
     */
    public void setCos2(final ContainerOutputStream<F, S> cos2) {
	this.cos2 = cos2;
    }

    /**
     * write
     * 
     * @param b
     * @param off
     * @param len
     * @throws IOException
     * @see de.wsdevel.tools.streams.container.ContainerOutputStream#write(byte[],
     *      int, int)
     */
    @Override
    public void write(final byte[] b, final int off, final int len)
	    throws IOException {
	if (this.cos1 != null) {
	    this.cos1.write(b, off, len);
	}
	if (this.cos2 != null) {
	    this.cos2.write(b, off, len);
	}
    }

    /**
     * write
     * 
     * @param b
     * @throws IOException
     * @see de.wsdevel.tools.streams.container.ContainerOutputStream#write(int)
     */
    @Override
    public void write(final int b) throws IOException {
	if (this.cos1 != null) {
	    this.cos1.write(b);
	}
	if (this.cos2 != null) {
	    this.cos2.write(b);
	}
    }

    /**
     * writeFrame
     * 
     * @param frame
     * @throws IOException
     * @see de.wsdevel.tools.streams.container.ContainerOutputStream#writeFrame(de.wsdevel.tools.streams.container.Frame)
     */
    @Override
    public void writeFrame(final F frame) throws IOException {
	if (this.cos1 != null) {
	    this.cos1.writeFrame(frame);
	}
	if (this.cos2 != null) {
	    this.cos2.writeFrame(frame);
	}
    }

    /**
     * writeFrames
     * 
     * @param frames
     * @param off
     * @param len
     * @throws IOException
     * @see de.wsdevel.tools.streams.container.ContainerOutputStream#writeFrames(F[],
     *      int, int)
     */
    @Override
    public void writeFrames(final F[] frames, final int off, final int len)
	    throws IOException {
	if (this.cos1 != null) {
	    this.cos1.writeFrames(frames, off, len);
	}
	if (this.cos2 != null) {
	    this.cos2.writeFrames(frames, off, len);
	}
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================