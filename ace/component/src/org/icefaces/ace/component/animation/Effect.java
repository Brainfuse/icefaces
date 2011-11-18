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

package org.icefaces.ace.component.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.icefaces.ace.util.JSONBuilder;

public abstract class Effect{
 
	private AnimationBehavior effectBehavior;
	private Map<String, Object> properties = new HashMap<String, Object>(); 
	private String sourceElement;
	
    String getSourceElement() {
		return sourceElement;
	}

	void setSourceElement(String sourceElement) {
		this.sourceElement = sourceElement;
		properties.put("node", "#"+ sourceElement);
	}

	public Map getProperties() {
		return properties;
	}

	public void setProperties(Map properties) {
		this.properties = properties;
	}
	
	public String getPropertiesAsJSON() {
		return convertMapToJSON(properties);
	}	
	
	private String convertMapToJSON(Map map) {
		JSONBuilder json = JSONBuilder.create();
		json.beginMap();
		Iterator<String> props = map.keySet().iterator();
		while (props.hasNext()) {
			String prop = props.next();
			Object value = map.get(prop);
			if (value == null) continue;
			if (value instanceof Map) {
				json.entry (prop, convertMapToJSON((Map)value), true);
			} else {
				String val = value.toString();
				try {
					Integer inVaue = Integer.parseInt(val);
					json.entry(prop, inVaue);
				} catch (Exception exception) {
					try {
						Float floatValue = Float.parseFloat(val);
						json.entry(prop, floatValue);
					} catch(Exception ex) {
						if ("true".equalsIgnoreCase(val) || "false".equalsIgnoreCase(val)) {
							Boolean bolValue = Boolean.parseBoolean(val);
							json.entry(prop, bolValue);
						} else {
							if (val.startsWith("{")) {
								json.entry(prop, value.toString(), true);
							} else {
								json.entry(prop, value.toString());
							}
						}
					}						
				}
			}
		}
        return json.endMap().toString();		
	}
	
	AnimationBehavior getEffectBehavior() {
		return effectBehavior;
	}

	void setEffectBehavior(AnimationBehavior effectBehavior) {
		this.effectBehavior = effectBehavior;
	}

 

	public String getName() {
    	return this.getClass().getSimpleName();
    }
	
	
	public void run() {
		if(null != getEffectBehavior()) {
			effectBehavior.setRun(true);
		}
	}
	
 
}
