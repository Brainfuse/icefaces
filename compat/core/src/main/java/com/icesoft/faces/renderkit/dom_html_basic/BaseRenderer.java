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

package com.icesoft.faces.renderkit.dom_html_basic;

import com.icesoft.faces.context.effects.CurrentStyle;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.icesoft.faces.util.CoreUtils;
import com.icesoft.util.pooling.ClientIdPool;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import java.io.IOException;

public class BaseRenderer extends Renderer {
    public void decode(FacesContext facesContext, UIComponent uiComponent) {
        CurrentStyle.decode(facesContext, uiComponent);
        super.decode(facesContext, uiComponent);
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent)
            throws IOException {
        super.encodeBegin(facesContext, uiComponent);

    }

    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent)
            throws IOException {
        super.encodeChildren(facesContext, uiComponent);

    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException {
        super.encodeEnd(facesContext, uiComponent);
        CoreUtils.recoverFacesMessages(facesContext, uiComponent);
        JavascriptContext.fireEffect(uiComponent, facesContext);
    }

    public String getResourceURL(FacesContext context, String path) {
        return DomBasicRenderer.getResourceURL(context, path);
    }

    //This method should not be removed, it will be called by the UIInput class
    //for all input type of components.
    public Object getConvertedValue(FacesContext facesContext, UIComponent
            uiComponent, Object submittedValue) throws ConverterException {

        // get the converter (if any) registered with this component 
        Converter converter = null;
        if (uiComponent instanceof ValueHolder) {
            converter = ((ValueHolder) uiComponent).getConverter();
        }
        // if we didn't find a converter specifically registered with the component
        // then get the default converter for the type of the value binding,
        // if it exists
        ValueBinding valueBinding = uiComponent.getValueBinding("value");
        if (converter == null && valueBinding != null) {
            Class valueBindingClass = valueBinding.getType(facesContext);
            if (valueBindingClass != null) {
                converter = facesContext.getApplication()
                        .createConverter(valueBindingClass);
            }
        }

        if (converter != null) {
            return converter.getAsObject(facesContext, uiComponent,
                    (String) submittedValue);
        } else if (submittedValue != null) {
            return (String) submittedValue;
        } else {
            return null;
        }
    }

    public String convertClientId(FacesContext context, String clientId) {
        return ClientIdPool.get(clientId);
    }
}
