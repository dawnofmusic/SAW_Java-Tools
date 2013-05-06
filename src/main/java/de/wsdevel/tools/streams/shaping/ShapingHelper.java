/**
 * Class:	ShapingHelper<br/>
 * <br/>
 * Created:	06.05.2013<br/>
 * Filename: ShapingHelper.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.shaping;

/**
 * ShapingHelper
 */
public class ShapingHelper {

    /** {@link int} The ONE_MILLION. */
    public static final int ONE_MILLION = 1000000;

    /**
     * getMillisPartFromNanos.
     * 
     * @param nanos
     * @return
     */
    public static long getMillisPartFromNanos(long nanos) {
        return nanos > 999999 ? nanos / ONE_MILLION : 0;
    }

    /**
     * getNanosRestFromNanos.
     * 
     * @param nanos
     * @return
     */
    public static int getNanosRestFromNanos(long nanos) {
        return (int) (nanos % ONE_MILLION);
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================