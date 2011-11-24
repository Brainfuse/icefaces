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

package org.icefaces.samples.showcase.example.compat.autocomplete;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.icesoft.faces.component.selectinputtext.TextChangeEvent;

import org.icefaces.samples.showcase.metadata.annotation.ComponentExample;
import org.icefaces.samples.showcase.metadata.annotation.ExampleResource;
import org.icefaces.samples.showcase.metadata.annotation.ExampleResources;
import org.icefaces.samples.showcase.metadata.annotation.Menu;
import org.icefaces.samples.showcase.metadata.annotation.MenuLink;
import org.icefaces.samples.showcase.metadata.annotation.ResourceType;
import org.icefaces.samples.showcase.metadata.context.ComponentExampleImpl;

@ComponentExample(
        title = "example.compat.autocomplete.title",
        description = "example.compat.autocomplete.description",
        example = "/resources/examples/compat/autocomplete/autocomplete.xhtml"
)
@ExampleResources(
        resources ={
            // xhtml
            @ExampleResource(type = ResourceType.xhtml,
                    title="autocomplete.xhtml",
                    resource = "/resources/examples/compat/"+
                               "autocomplete/autocomplete.xhtml"),
            // Java Source
            @ExampleResource(type = ResourceType.java,
                    title="AutocompleteBean.java",
                    resource = "/WEB-INF/classes/org/icefaces/samples/"+
                               "showcase/example/compat/autocomplete/AutocompleteBean.java")
        }
)
@Menu(
	title = "menu.compat.autocomplete.subMenu.title",
	menuLinks = {
            @MenuLink(title = "menu.compat.autocomplete.subMenu.main",
                    isDefault = true,
                    exampleBeanName = AutocompleteBean.BEAN_NAME),
            @MenuLink(title = "menu.compat.autocomplete.subMenu.simple",
                    exampleBeanName = AutocompleteSimple.BEAN_NAME),
            @MenuLink(title = "menu.compat.autocomplete.subMenu.complex",
                    exampleBeanName = AutocompleteComplex.BEAN_NAME),
            @MenuLink(title = "menu.compat.autocomplete.subMenu.rows",
                    exampleBeanName = AutocompleteRows.BEAN_NAME)
})
@ManagedBean(name= AutocompleteBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class AutocompleteBean extends ComponentExampleImpl<AutocompleteBean> implements Serializable {
	
	public static final String BEAN_NAME = "autocomplete";
	
	private List<SelectItem> availableCities = AutocompleteData.wrapList();
	private String selectedText = null; // Text the user is typing in
	private String selectedCity;       // Entry the user selected
	private int results = 0;
	
	public AutocompleteBean() {
		super(AutocompleteBean.class);
	}
	
	public List<SelectItem> getAvailableCities() { return availableCities; }
	public String getSelectedText() { return selectedText; }
	public String getSelectedCity() { return selectedCity; }
	public int getResults() { return results; }
	
	public void setAvailableCities(List<SelectItem> availalbeCities) { this.availableCities = availableCities; }
	public void setSelectedText(String selectedText) { this.selectedText = selectedText; }
	public void setSelectedCity(String selectedCity) { this.selectedCity = selectedCity; }
	public void setResults(int results) { this.results = results; }
	
	public void textChanged(TextChangeEvent event) {
	    // Filter the list of cities based on what the user has typed so far
	    availableCities = AutocompleteData.wrapList(event.getNewValue().toString());
	    // Track the number of results
	    results = availableCities.size();
	}
	
	public void submitText(ActionEvent event) {
	    setSelectedCity(getSelectedText());
	    
	    // Clear out filtered list now that we have made a choice
	    availableCities = null;
	    results = 0;
	}
}
