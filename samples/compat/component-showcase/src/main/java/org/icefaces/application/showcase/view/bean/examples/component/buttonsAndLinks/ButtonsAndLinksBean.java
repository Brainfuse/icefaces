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

package org.icefaces.application.showcase.view.bean.examples.component.buttonsAndLinks;

import org.icefaces.application.showcase.util.MessageBundleLoader;
import org.icefaces.application.showcase.view.bean.BaseBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 * <p>The ButtonsAndLinksBean class is the backing bean for the buttons and
 * links demonstration. It is used to store the input submitted by buttons and
 * links. It also has action listeners to record which button or link was clicked.</p>
 */
@ManagedBean(name = "buttonsAndLinks")
@ViewScoped
public class ButtonsAndLinksBean extends BaseBean {

    /**
     * Variables to store the button clicked and the input submitted.
     */
    private String clicked;
    private String inputText;

    /**
     * Gets the name of the button clicked.
     *
     * @return name of the button clicked.
     */
    public String getClicked() {
        return MessageBundleLoader.getMessage(clicked);
    }

    /**
     * Gets the value of the input submitted.
     *
     * @return the value of the input submitted.
     */
    public String getInputText() {
        return inputText;
    }

    /**
     * Sets the input text value.
     *
     * @param newValue input text value.
     */
    public void setInputText(String newValue) {
        inputText = newValue;
    }

    /**
     * Listener for the submit button click action.
     *
     * @param e click action event.
     */
    public void submitButtonListener(ActionEvent e) {
        clicked = "bean.buttonsAndLinks.submitButton";
        valueChangeEffect.setFired(false);
    }

    /**
     * Listener for the image button click action.
     *
     * @param e click action event.
     */
    public void imageButtonListener(ActionEvent e) {
        clicked = "bean.buttonsAndLinks.imageButton";
        valueChangeEffect.setFired(false);
    }

    /**
     * Listener for the command link click action.
     *
     * @param e click action event.
     */
    public void commandLinkListener(ActionEvent e) {
        clicked = "bean.buttonsAndLinks.commandLink";
        valueChangeEffect.setFired(false);
    }
}
