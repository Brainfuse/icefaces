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
package org.icefaces.ace.component.themeselect;

import org.icefaces.ace.renderkit.InputRenderer;
import org.icefaces.ace.util.Constants;
import org.icefaces.ace.util.JSONBuilder;
import org.icefaces.render.MandatoryResourceComponent;
import org.icefaces.util.EnvUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MandatoryResourceComponent(tagName = "themeSelect", value = "org.icefaces.ace.component.themeselect.ThemeSelect")
public class ThemeSelectRenderer extends InputRenderer {

    private FacesContext context;
    private ResponseWriter writer;
    private ThemeSelect component;
    private String clientId;
    private String selectedTheme;
    private Map<String, String> themeList;
    private boolean ariaEnabled;

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        this.context = context;
        this.component = (ThemeSelect) component;
        super.encodeEnd(context, component);
        if (!component.isRendered()) {
            return;
        }
        writer = context.getResponseWriter();
        clientId = component.getClientId(context);
        String selectId = "select_" + clientId;

        writer.startElement("div", component);
        writer.writeAttribute("id", clientId, "id");

        writer.startElement("select", component);
        writer.writeAttribute("id", selectId, "id");
        writer.writeAttribute("name", selectId, "id");
        if (this.component.isDisabled()) {
            writer.writeAttribute("disabled", "disabled", "disabled");
        }
        String styleClass = (styleClass = this.component.getStyleClass()) == null ? "" : " " + styleClass.trim();
        writer.writeAttribute("class", "ui-widget ui-corner-all ui-state-default" + getStateStyleClasses(this.component) + styleClass, null);
        writeAttributes("accesskey", "dir", "label", "lang", "style", "tabindex", "title", "alt");
        ariaEnabled = EnvUtils.isAriaEnabled(context);
        writerSelAriaAttrs();
        renderOptions();
        writer.endElement("select");

        renderScript();

        writer.startElement("span", null);
        writer.writeAttribute("class", "ui-helper-hidden", null);
        writer.write(Integer.toString(selectedTheme.hashCode()) + Integer.toString(themeList.hashCode()));
        writer.endElement("span");

        writer.endElement("div");
    }

    private void writerSelAriaAttrs() throws IOException {
        if (!ariaEnabled) return;

        writer.writeAttribute("role", "listbox", null);
        if (component.isRequired()) {
            writer.writeAttribute("aria-required", "true", "required");
        }
        if (component.isDisabled()) {
            writer.writeAttribute("aria-disabled", "true", "disabled");
        }
        if (!component.isValid()) {
            writer.writeAttribute("aria-invalid", "true", null);
        }
        String label = component.getLabel();
        if (label != null) {
            writer.writeAttribute("aria-label", label, "label");
        }
    }

    private void writeAttributes(String... keys) throws IOException {
        Object value;
        for (String key : keys) {
            value = component.getAttributes().get(key);
            if (value != null) {
                writer.writeAttribute(key, value, key);
            }
        }
    }

    private void renderOptions() throws IOException {
        getThemeList();
        selectedTheme = (String) component.getValue();
        selectedTheme = selectedTheme == null ? "" : selectedTheme.trim();
        if (selectedTheme.equals("")) {
            selectedTheme = getSelectedTheme();
        }
        if (themeList.get(selectedTheme) == null) {
            selectedTheme = "sam";
        }
        context.getExternalContext().getSessionMap().put(Constants.THEME_PARAM, selectedTheme);

        String theme, href;
        for (Map.Entry<String, String> entry : themeList.entrySet()) {
            theme = entry.getKey();
            href = entry.getValue();
            writer.startElement("option", null);
            writer.writeAttribute("value", theme, null);
            if (theme.equals(selectedTheme)) {
                writer.writeAttribute("selected", "selected", null);
            }
            writer.writeAttribute("data-href", href, null);
            writer.write(theme);
            writer.endElement("option");
        }
    }

    private void renderScript() throws IOException {
        JSONBuilder jb = JSONBuilder.create();
        encodeClientBehaviors(context, component, jb);

        writer.startElement("script", null);
        writer.writeAttribute("type", "text/javascript", null);
        writer.write("ice.ace.ThemeSelect.singleEntry(\"" + clientId + "\",{" + jb + "});");
        writer.endElement("script");
    }

    @SuppressWarnings("unchecked")
    private void getThemeList() throws IOException {
        Map<String, Object> appMap = context.getExternalContext().getApplicationMap();
        String THEME_LIST = Constants.THEME_PARAM + ".list";
        themeList = (Map<String, String>) appMap.get(THEME_LIST);
        if (themeList != null) {
            return;
        }
        themeList = new TreeMap<String, String>();
        themeList.put("none", "");

        ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
        Resource resource;
        for (String theme : new String[]{"rime", "sam"}) {
            resource = resourceHandler.createResource("themes/" + theme + "/theme.css", "icefaces.ace");
            if (resource != null) {
                themeList.put(theme, resource.getRequestPath());
            }
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls = classLoader.getResources("META-INF/resources");
        URL url;
        Matcher matcher = Pattern.compile("jar:.*/WEB-INF/lib/(.+)\\.jar!/META-INF/resources").matcher("");
        String theme;
        while (urls.hasMoreElements()) {
            url = urls.nextElement();
            if (matcher.reset(url.toString()).matches()) {
                theme = matcher.group(1);
                url = classLoader.getResource("META-INF/resources/ace-" + theme);
                if (url != null) {
                    resource = resourceHandler.createResource("theme.css", "ace-" + theme);
                    if (resource != null) {
                        themeList.put(theme, resource.getRequestPath());
                    }
                }
            }
        }
        appMap.put(THEME_LIST, themeList);
    }

    // Copied from org.icefaces.ace.renderkit.HeadRenderer.processEvent
    public String getSelectedTheme() {
        FacesContext context = FacesContext.getCurrentInstance();
        String theme = null;
        String themeParamValue = context.getExternalContext().getInitParameter(Constants.THEME_PARAM);

        if (themeParamValue != null) {
            ELContext elContext = context.getELContext();
            ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
            ValueExpression ve = expressionFactory.createValueExpression(elContext, themeParamValue, String.class);

            theme = (String) ve.getValue(elContext);
        }

        if (theme == null) theme = "";
        else theme = theme.trim();

        if ("".equals(theme) || theme.equalsIgnoreCase("sam")) {
            theme = "sam";
        }
        return theme;
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        Map<String, String> requestParameterMap = context.getExternalContext().getRequestParameterMap();
        String submittedValue = requestParameterMap.get("select_" + component.getClientId(context));
        if (submittedValue != null) {
            ((ThemeSelect) component).setSubmittedValue(submittedValue);
        }
        decodeBehaviors(context, component);
    }
}
