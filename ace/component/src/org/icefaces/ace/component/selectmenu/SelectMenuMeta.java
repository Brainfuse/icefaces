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

package org.icefaces.ace.component.selectmenu;

import org.icefaces.ace.meta.annotation.Component;
import org.icefaces.ace.meta.annotation.Property;
import org.icefaces.ace.meta.annotation.Field;
import org.icefaces.ace.meta.baseMeta.UISelectOneMeta;
import org.icefaces.ace.meta.annotation.Expression;
import org.icefaces.ace.meta.annotation.ClientBehaviorHolder;
import org.icefaces.ace.meta.annotation.ClientEvent;
import org.icefaces.ace.api.IceClientBehaviorHolder;

import org.icefaces.resources.ICEResourceDependencies;
import org.icefaces.resources.ICEResourceDependency;
import javax.el.ValueExpression;
import javax.el.MethodExpression;

import java.util.List;

@Component(
        tagName = "selectMenu",
        componentClass = "org.icefaces.ace.component.selectmenu.SelectMenu",
        generatedClass = "org.icefaces.ace.component.selectmenu.SelectMenuBase",
        extendsClass = "javax.faces.component.UISelectOne",
		rendererClass   = "org.icefaces.ace.component.selectmenu.SelectMenuRenderer",
        componentFamily = "org.icefaces.ace.SelectMenu",
        componentType = "org.icefaces.ace.component.SelectMenu",
		rendererType    = "org.icefaces.ace.component.SelectMenuRenderer",
        tlddoc = "" +
                 "<p>For more information, see the " +
                 "<a href=\"http://wiki.icefaces.org/display/ICE/SelectMenu\">SelectMenu Wiki Documentation</a>."
)
@ICEResourceDependencies({
	@ICEResourceDependency(library = "icefaces.ace", name = "util/ace-jquery.js"),
	@ICEResourceDependency(library = "icefaces.ace", name = "selectmenu/selectmenu.js")
})
@ClientBehaviorHolder(events = {
	@ClientEvent( name="valueChange",
		javadoc="Fired whenever the value of the component changes.",
		tlddoc="Fired whenever the value of the component changes.",
		defaultRender="@all", defaultExecute="@all" ),
	@ClientEvent( name="blur",
		javadoc="Fired any time the component loses focus.",
		tlddoc="Fired any time the component loses focus.",
		defaultRender="@all", defaultExecute="@all" )},
	defaultEvent="valueChange" )
public class SelectMenuMeta extends UISelectOneMeta {

    @Property(tlddoc = "Style class name of the container element.", defaultValue="")
    private String style;
	
    @Property(tlddoc = "Style class name of the container element.", defaultValue="")
    private String styleClass;
	
    @Property(tlddoc = "The maximum number of possible options to show to the user.", defaultValue="10")
    private int rows;
	
    @Property(tlddoc = "The width of the text input field, in pixels.", defaultValue="150")
    private int width;
	
    @Property(tlddoc = "Maximum height in pixels of the list of possible matches (if 0, then the size is automatically adjusted to show all possible matches).")
    private int height;

    @Property(tlddoc = "Variable name to use for referencing each data object in the list when rendering via a facet.")
    private String listVar;

    @Property(tlddoc = "When rendering via a facet, this attribute specifies the list of data objects that contains all possible options.")
    private List listValue;
	
	@Property(expression = Expression.VALUE_EXPRESSION,
            tlddoc="ValueExpression that specifies the property of the data object to use as the value of the item for this component if it gets selected. This only applies when listvar is used and the rendering is done by means of a facet.")
	private Object itemValue;
	
	@Property(expression = Expression.VALUE_EXPRESSION,
            tlddoc="ValueExpression that specifies the property of the data object to use for checking whether the item should be disabled or not. It must evaluate to a boolean value. This only applies when listvar is used and the rendering is done by means of a facet. When using f:selectItem(s), the disabled property on that object/tag is used for the same effect.")
	private Object itemDisabled;
	
	// disabled and readOnly

    @Property(tlddoc = "Indicator indicating that the user is required to provide a submitted value for this input component.")
    private String requiredIndicator;

    @Property(tlddoc = "Indicator indicating that the user is NOT required to provide a submitted value for this input component.")
    private String optionalIndicator;

    @Property(tlddoc = "Position of label relative to input field. Supported values are \"left/right/top/bottom/inField/none\". Default is \"none\".")
    private String labelPosition;

    @Property(tlddoc = "Position of input-required or input-optional indicator relative to input field or label. " +
            "Supported values are \"left/right/top/bottom/labelLeft/labelRight/none\". " +
            "Default is \"labelRight\" if labelPosition is \"inField\", \"right\" otherwise.")
    private String indicatorPosition;
	
    @Field()
    private List itemList;
}