/**
 * Class:	BlackBoxSegment<br/>
 * <br/>
 * Created:	06.05.2013<br/>
 * Filename: BlackBoxSegment.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * BlackBoxSegment
 */
public class BlackBoxSegment extends Segment<BlackBoxFrame> {

    /** {@link Log} The LOG. */
    private static final Log LOG = LogFactory.getLog(BlackBoxSegment.class);

    /**
     * @see de.wsdevel.tools.streams.container.Segment#deserializeFrames(java.util.Map)
     * @param hints
     * @return
     * @throws IOException
     */
    @Override
    protected BlackBoxFrame[] deserializeFrames(final Map<String, Object> hints)
	    throws IOException {
	return new BlackBoxFrame[] { new BlackBoxFrame(getData()) };
    }

    /**
     * @see de.wsdevel.tools.streams.container.Segment#serializeFrames()
     * @return
     */
    @Override
    protected byte[] serializeFrames() {
	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	final BlackBoxFrame[] frames2 = getFrames();
	try {
	    for (final BlackBoxFrame blackBoxFrame : frames2) {
		baos.write(blackBoxFrame.toBytes());
	    }
	    baos.flush();
	} catch (final IOException e) {
	    BlackBoxSegment.LOG.error(e.getLocalizedMessage(), e);
	} finally {
	    try {
		baos.close();
	    } catch (final IOException e) {
		// don't care
	    }
	}
	return baos.toByteArray();
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================