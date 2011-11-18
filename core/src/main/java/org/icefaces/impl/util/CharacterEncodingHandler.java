/*
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations under
 * the License.
 *
 * The Original Code is ICEfaces 1.5 open source software code, released
 * November 5, 2006. The Initial Developer of the Original Code is ICEsoft
 * Technologies Canada, Corp. Portions created by ICEsoft are Copyright (C)
 * 2004-2011 ICEsoft Technologies Canada, Corp. All Rights Reserved.
 *
 * Contributor(s): _____________________.
 */

package org.icefaces.impl.util;

import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This resource handler is a work around for setting character encoding before
 * the other resource handlers run.  It's necessary if you want to be able to
 * query request parameters in the resource handlers - particularly in Glassfish
 * which actively prevents setting the encoding if the request paramaters have
 * already been read (Tomcat seems to be more forgiving). The CharacterEncodingHandler
 * needs to be at the end of the chain of other ICEfaces resource handlers to ensure
 * that it runs first.  This is done by putting it in it's own META-INF/extra.faces-config.xml
 * and using the <ordering> mechanism of JSF to ensure that the configuration is read
 * after the faces-config that holds the rest of the ICEfaces Core configuration.
 * <p/>
 * <ul>
 * <li>http://jira.icefaces.org/browse/ICE-5977</li>
 * <li>https://javaserverfaces.dev.java.net/issues/show_bug.cgi?id=1767</li>
 * </ul>
 * <p/>
 * The character encoding detection code is essentially the same logic as is used in
 * the ViewHandler of the Mojarra implementation which is where the character encoding
 * is currently detected and applied, albeit too late for our purposes.
 */
public class CharacterEncodingHandler extends ResourceHandlerWrapper {

    private static Logger log = Logger.getLogger(CharacterEncodingHandler.class.getName());

    private ResourceHandler wrapped;

    public CharacterEncodingHandler(ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    public ResourceHandler getWrapped() {
        return wrapped;
    }

    @Override
    public boolean isResourceRequest(FacesContext context) {
        setCharacterEncoding(context);
        return wrapped.isResourceRequest(context);
    }

    private void setCharacterEncoding(FacesContext context) {
        String calculatedEncoding = calculateCharacterEncoding(context);
        if (null != calculatedEncoding) {
            ExternalContext ec = context.getExternalContext();
            String currentEncoding = ec.getRequestCharacterEncoding();
            try {
                if (!calculatedEncoding.equals(currentEncoding)) {
                    ec.setRequestCharacterEncoding(calculatedEncoding);
                }
            } catch (UnsupportedEncodingException e) {
                if (log.isLoggable(Level.WARNING)) {
                    log.warning("can't set encoding to [" + calculatedEncoding + "], current encoding is [" + currentEncoding + "]");
                }
            }
        } else {
            if (log.isLoggable(Level.FINE)) {
                log.fine("character encoding could not be determined");
            }
        }
    }

    private String calculateCharacterEncoding(FacesContext context) {
        ExternalContext extContext = context.getExternalContext();
        Map<String, String> headerMap = extContext.getRequestHeaderMap();
        String contentType = headerMap.get("Content-Type");
        String charEnc = null;

        // look for a charset in the Content-Type header first.
        if (null != contentType) {
            // see if this header had a charset
            String charsetStr = "charset=";
            int len = charsetStr.length();

            //It's possible for Content-Type header to have multiple charset entries
            int idx = contentType.lastIndexOf(charsetStr);

            // if we have a charset in this Content-Type header AND it
            // has a non-zero length.
            if (idx != -1 && idx + len < contentType.length()) {
                charEnc = contentType.substring(idx + len);
            }
        }

        // failing that, look in the session for a previously saved one
        if (null == charEnc) {
            if (null != extContext.getSession(false)) {
                charEnc = (String) extContext.getSessionMap().get(ViewHandler.CHARACTER_ENCODING_KEY);
            }
        }

        return charEnc;
    }

}
