/**
 * Class:	BlackBoxFrame<br/>
 * <br/>
 * Created:	06.05.2013<br/>
 * Filename: BlackBoxFrame.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.container;

/**
 * BlackBoxFrame
 */
public class BlackBoxFrame extends AbstractFrame {

    /** {@link byte[]} The data. */
    private final byte[] data;

    /**
     * BlackBoxFrame constructor.
     * 
     * @param dataRef
     */
    public BlackBoxFrame(final byte[] dataRef) {
	this.data = dataRef;
    }

    /**
     * @see de.wsdevel.tools.streams.container.Frame#getSize()
     * @return
     */
    @Override
    public int getSize() {
	return this.data.length;
    }

    /**
     * @see de.wsdevel.tools.streams.container.Frame#toBytes()
     * @return
     */
    @Override
    public byte[] toBytes() {
	return this.data;
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================