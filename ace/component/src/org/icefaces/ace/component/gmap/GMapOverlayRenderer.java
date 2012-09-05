package org.icefaces.ace.component.gmap;
/*
 * Copyright 2004-2012 ICEsoft Technologies Canada Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import org.icefaces.ace.renderkit.CoreRenderer;
import org.icefaces.render.MandatoryResourceComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

@MandatoryResourceComponent(tagName="gMap", value="org.icefaces.ace.component.gmap.GMap")
public class GMapOverlayRenderer extends CoreRenderer {


    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        GMapOverlay overlay = (GMapOverlay) component;
        ResponseWriter writer = context.getResponseWriter();
        String clientId = overlay.getClientId(context);
		writer.startElement("span", null);
		writer.writeAttribute("id", clientId + "_overlay", null);
        writer.startElement("script", null);
        writer.writeAttribute("type", "text/javascript", null);
        writer.write("ice.ace.jq(function() {");
        if (overlay.getPoints() != null && overlay.getShape() != null){
            writer.write("ice.ace.gMap.removeGOverlay('" + overlay.getParent().getClientId(context) + "', '" + clientId + "');");
            writer.write("ice.ace.gMap.gOverlay('" + overlay.getParent().getClientId(context) + "', '" + clientId + "' , '" + overlay.getShape() + "' , '" + overlay.getPoints() + "' , \"" + overlay.getOptions() + "\");");
        }
        writer.write("});");
        writer.endElement("script");
		writer.endElement("span");
    }
}