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

package org.icefaces.test.invalidate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionInvalidationFilter implements Filter {

    private FilterConfig config;

    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        Object sessInvalidationAttribute = null; //servletRequest.getAttribute(EnvUtils.SESSION_INVALIDATION);
        System.out.println("SessionInvalidationFilter.doFilter: " + sessInvalidationAttribute);
        if (sessInvalidationAttribute != null) {
            if (servletRequest instanceof HttpServletRequest) {
                HttpSession sess = ((HttpServletRequest) servletRequest).getSession(false);
                if (sess != null) {
                    sess.invalidate();
                    System.out.println("SessionInvalidationFilter.doFilter: INVALIDATING!!!!");
                } else {
                    System.out.println("SessionInvalidationFilter.doFilter: no active session");
                }
            } else {
                System.out.println("SessionInvalidationFilter.doFilter: servlet request is not HttpServletRequest");
            }
        }
    }

    public void destroy() {
    }
}
