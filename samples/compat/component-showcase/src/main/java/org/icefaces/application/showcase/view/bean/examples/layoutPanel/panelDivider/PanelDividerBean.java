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

package org.icefaces.application.showcase.view.bean.examples.layoutPanel.panelDivider;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;

/**
 * Simple model bean for the panelDivider example.  The bean maintains the
 * position and orientation of the divider.
 *
 * @since 1.7
 */
@ManagedBean(name = "panelDividerBean")
@ViewScoped
public class PanelDividerBean  implements Serializable {

    public static final String ORIENTATION_HOR = "horizontal";
    public static final String ORIENTATION_VER = "vertical";

    private static final int POSITION_DEFAULT = 40;

    private String orientation = ORIENTATION_VER;
    private int position = POSITION_DEFAULT;

    public String getOrientation() {
        return orientation;
    }

    public int getPosition() {
        return position;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Listener method called when the orientation is changed
     * This is useful to allow us to reset the position of the divider to
     * the default value
     *
     * @param event of the change jsf event
     */
    public void orientationChanged(ValueChangeEvent event) {
        this.position = POSITION_DEFAULT;
    }
}
