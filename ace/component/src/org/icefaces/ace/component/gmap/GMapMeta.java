/*
 * Copyright 2004-2013 ICEsoft Technologies Canada Corp.
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

package org.icefaces.ace.component.gmap;

import org.icefaces.ace.meta.annotation.Component;
import org.icefaces.ace.meta.annotation.Property;
import org.icefaces.ace.meta.baseMeta.UIPanelMeta;

import org.icefaces.resources.ICEResourceDependencies;
import org.icefaces.resources.ICEResourceDependency;

@Component(
        tagName = "gMap",
        componentClass = "org.icefaces.ace.component.gmap.GMap",
        rendererClass = "org.icefaces.ace.component.gmap.GMapRenderer",
        generatedClass = "org.icefaces.ace.component.gmap.GMapBase",
        extendsClass = "javax.faces.component.UIPanel",
        componentType = "org.icefaces.ace.component.GMap",
        rendererType = "org.icefaces.ace.component.GMapRenderer",
        componentFamily = "org.icefaces.ace.component",
        tlddoc = "The base component for the ACE gMap (Google Maps API) set of components." +
                " This component is how to define and control the map proper, as well as serving as a parent for the other gMap subcomponents." +
                " Important note: To function properly, you must define the property 'org.icefaces.ace.gmapKey' in your web.xml." +
                " You can get an API key at http://code.google.com/apis/maps/signup.html." +
                " For more information, see the <a href=\"http://wiki.icefaces.org/display/ICE/GMap\">gMap</a> Wiki Documentation."
)

@ICEResourceDependencies({
	@ICEResourceDependency(library="icefaces.ace", name="gmap/api.js"),
	@ICEResourceDependency(library="icefaces.ace", name="util/combined.css"),
	@ICEResourceDependency(library="icefaces.ace", name="jquery/ui/jquery-ui.css"),
	@ICEResourceDependency(library="icefaces.ace", name="util/ace-jquery.js"),
	@ICEResourceDependency(library="icefaces.ace", name="util/ace-components.js")
})

public class GMapMeta extends UIPanelMeta {

    @Property(tlddoc = "The starting longitude for the map. Will be overridden if an address is provided.", defaultValue = "-114.08538937568665")
    private String longitude;

    @Property(tlddoc = "The starting latitude for the map. Will be overridden if an address is provided.", defaultValue = "51.06757388616548")
    private String latitude;

    @Property(tlddoc = "Starting zoom of the map element.", defaultValue = "5")
    private String zoomLevel;

    @Property(tlddoc = "Additional options to be sent to the map. Check google maps API at https://developers.google.com/maps/documentation/javascript/reference#MapOptions for more specifics. Form is attribute:'value'.")
    private String options;

    @Property(tlddoc = "Whether the map should be locating the specified address. Default is false.", defaultValue = "false")
    private boolean locateAddress;

    @Property(tlddoc = "Specifies whether the map has been initialized or not.", defaultValue = "false")
    private boolean intialized;

    @Property(tlddoc = "Address to locate.")
    private String address;

    @Property(tlddoc = "Map type to display by default. Possible values are 'HYBRID', 'ROADMAP', 'SATELLITE' and 'TERRAIN', case insensitive.", defaultValue = "ROADMAP")
    private String type;

    @Property(tlddoc = "Styling for the main gMap div.")
    private String style;

    @Property(tlddoc = "The classname for the main gMap div.")
    private String styleClass;
}
