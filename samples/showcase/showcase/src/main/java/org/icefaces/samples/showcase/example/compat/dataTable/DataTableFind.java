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

package org.icefaces.samples.showcase.example.compat.dataTable;

import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.context.effects.Pulsate;
import org.icefaces.samples.showcase.metadata.annotation.ComponentExample;
import org.icefaces.samples.showcase.metadata.annotation.ExampleResource;
import org.icefaces.samples.showcase.metadata.annotation.ExampleResources;
import org.icefaces.samples.showcase.metadata.annotation.ResourceType;
import org.icefaces.samples.showcase.metadata.context.ComponentExampleImpl;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import org.icefaces.samples.showcase.dataGenerators.utilityClasses.DataTableData;

@ComponentExample(
        parent = DataTableBean.BEAN_NAME,
        title = "example.compat.dataTable.find.title",
        description = "example.compat.dataTable.find.description",
        example = "/resources/examples/compat/dataTable/dataTableFind.xhtml"
)
@ExampleResources(
        resources ={
                // xhtml
                @ExampleResource(type = ResourceType.xhtml,
                        title="dataTableFind.xhtml",
                        resource = "/resources/examples/compat/"+
                                "dataTable/dataTableFind.xhtml"),
                // Java Source
                @ExampleResource(type = ResourceType.java,
                        title="DataTableFind.java",
                        resource = "/WEB-INF/classes/org/icefaces/samples/"+
                                "showcase/example/compat/dataTable/DataTableFind.java")
        }
)
@ManagedBean(name= DataTableFind.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class DataTableFind extends ComponentExampleImpl<DataTableFind> implements Serializable {
    public static final String BEAN_NAME = "dataTableFind";
    private ArrayList<Car> cars;

    public DataTableFind() {
        super(DataTableFind.class);
        cars = new ArrayList<Car>(DataTableData.getDefaultData());
    }

    @PostConstruct
    public void initMetaData() {
        super.initMetaData();
    }

    public String selectedEffectType = "default";
    public String selectedSearchMode = "contains";
    public String searchQuery = "";
    public HtmlDataTable iceTable = null;
    public String[] selectedColumns = new String[]{"name", "id", "chassis", "weight", "acceleration", "mpg", "cost"};
    public int lastFoundIndex = -1;
    private boolean caseSensitive;

    public final SelectItem[] SEARCH_MODES = {new SelectItem("startsWith", "Starts With"),
            new SelectItem("endsWith", "Ends With"),
            new SelectItem("contains", "Contains"),
            new SelectItem("exact", "Exact Match")};

    public final SelectItem[] COLUMNS = {new SelectItem("id", "Id"),
            new SelectItem("name", "Name"),
            new SelectItem("chassis", "Chassis"),
            new SelectItem("weight", "Weight"),
            new SelectItem("acceleration", "Acceleration"),
            new SelectItem("mpg", "MPG"),
            new SelectItem("cost", "Cost")};

    public final SelectItem[] EFFECT_TYPES = {new SelectItem("none", "None"),
            new SelectItem("default", "Default (Highlight)"),
            new SelectItem("pulsate", "Pulsate")};

    public void find(javax.faces.event.ActionEvent e) {
        HtmlDataTable.SearchType type = null;
        if (selectedSearchMode.equals("contains"))
            type = HtmlDataTable.SearchType.CONTAINS;
        else if (selectedSearchMode.equals("startsWith"))
            type = HtmlDataTable.SearchType.STARTS_WITH;
        else if (selectedSearchMode.equals("endsWith"))
            type = HtmlDataTable.SearchType.ENDS_WITH;
        else if (selectedSearchMode.equals("exact"))
            type = HtmlDataTable.SearchType.EXACT;
        else type = HtmlDataTable.SearchType.CONTAINS;

        int newFoundIndex = iceTable.findRow(searchQuery, selectedColumns, lastFoundIndex + 1, type, caseSensitive);

        if (newFoundIndex < 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(iceTable.getClientId(context),
                    new FacesMessage("Search starting at index " + (lastFoundIndex + 1) + " for \"" + searchQuery + "\" did not return a result."));
            return;
        }

        lastFoundIndex = newFoundIndex;

        if (selectedEffectType.equals("default"))
            iceTable.navigateToRow(lastFoundIndex);
        else if (selectedEffectType.equals("pulsate"))
            iceTable.navigateToRow(lastFoundIndex, new Pulsate());
        else if (selectedEffectType.equals("none"))
            iceTable.navigateToRow(lastFoundIndex, null);
    }

    public void reset(javax.faces.event.ActionEvent e) {
        lastFoundIndex = -1;
    }

    public String getSearchQuery() {return searchQuery;}
    public HtmlDataTable getIceTable() {return iceTable;}
    public boolean isCaseSensitive() {return caseSensitive;}
    public SelectItem[] getSEARCH_MODES() {return SEARCH_MODES;}
    public String getSelectedSearchMode() {return selectedSearchMode;}
    public String[] getSelectedColumns() {return selectedColumns;}
    public SelectItem[] getCOLUMNS() {return COLUMNS;}
    public SelectItem[] getEFFECT_TYPES() {return EFFECT_TYPES;}
    public String getSelectedEffectType() {return selectedEffectType;}
    public ArrayList<Car> getCars() {return cars; }
    
    
    public void setSelectedSearchMode(String selectedSearchMode) {this.selectedSearchMode = selectedSearchMode;}
    public void setSelectedColumns(String[] selectedColumns) {this.selectedColumns = selectedColumns;}
    public void setSelectedEffectType(String selectedEffectType) {this.selectedEffectType = selectedEffectType;}
    public void setIceTable(HtmlDataTable iceTable) {this.iceTable = iceTable;}
    public void setCaseSensitive(boolean caseSensitive) {this.caseSensitive = caseSensitive;}
    public void setSearchQuery(String searchQuery) {this.searchQuery = searchQuery;}
}
