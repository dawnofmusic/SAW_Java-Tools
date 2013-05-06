/**
 * Class:	AbstractFrame<br/>
 * <br/>
 * Created:	01.05.2013<br/>
 * Filename: AbstractFrame.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.container;

/**
 * AbstractFrame
 */
public abstract class AbstractFrame implements Frame {

    /** {@link int} The UNDEFINED_DURATION. */
    public static final int UNDEFINED_DURATION = -1;

    /** {@link long} The duration in milliseconds. */
    private long duration = AbstractFrame.UNDEFINED_DURATION;

    /**
     * @see de.wsdevel.tools.streams.container.Frame#getDurationNanos()
     * @return {@code long} the duration in milliseconds.
     */
    @Override
    public long getDurationNanos() {
	return this.duration;
    }

    /**
     * Sets the duration.
     * 
     * @param duration
     *            {@link long} in milliseconds.
     */
    public void setDuration(final long duration) {
	this.duration = duration;
    }

}
// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================