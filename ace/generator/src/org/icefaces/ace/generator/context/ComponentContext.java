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

package org.icefaces.ace.generator.context;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


import org.icefaces.ace.generator.artifacts.ComponentArtifact;
import org.icefaces.ace.generator.artifacts.ComponentHandlerArtifact;
import org.icefaces.ace.generator.artifacts.TagArtifact;
import org.icefaces.ace.generator.behavior.Behavior;
import org.icefaces.ace.generator.utils.PropertyValues;
import org.icefaces.ace.meta.annotation.*;

public class ComponentContext extends MetaContext {
    private Map<String, Field> internalFieldsForComponentClass = new HashMap<String, Field>();
    private Map<String, Field> fieldsForFacet = new HashMap<String, Field>();    
    private List<Behavior> behaviors = new ArrayList<Behavior>();
    
    public List<Behavior> getBehaviors() {
		return behaviors;
	}

	public Map<String, Field> getInternalFieldsForComponentClass() {
		return internalFieldsForComponentClass;
	}

	public Map<String, Field> getFieldsForFacet() {
		return fieldsForFacet;
	}

	public ComponentContext(Class clazz) {
		super(clazz);
    }

    @Override
    protected void setupArtifacts() {
		artifacts.put(ComponentArtifact.class.getSimpleName(), new ComponentArtifact(this));
		artifacts.put(TagArtifact.class.getSimpleName(), new TagArtifact(this));
		artifacts.put(ComponentHandlerArtifact.class.getName(), new ComponentHandlerArtifact(this));
	}

    @Override
    protected boolean isRelevantClass(Class clazz) {
        return clazz.isAnnotationPresent(Component.class);
    }

    @Override
    protected void processAnnotation(Class clazz) {
        super.processAnnotation(clazz);
        processFacets(clazz);
        processBehaviors(clazz);
    }

    @Override
    protected boolean processPotentiallyIrrelevantField(Class clazz, Field field) {
        boolean relevant = super.processPotentiallyIrrelevantField(clazz,  field);
        if (!relevant) {
            if (field.isAnnotationPresent(org.icefaces.ace.meta.annotation.Field.class)) {
                internalFieldsForComponentClass.put(field.getName(), field);
                relevant = true;
            }
        }
        return relevant;
    }

    @Override
    protected void furtherProcessProperty(Class clazz, PropertyValues propertyValues) {
        if (propertyValues.isGeneratingProperty()) {
            if (propertyValues.expression == Expression.METHOD_EXPRESSION) {
                generateHandler = true;
            }
        }
    }

    private void processFacets(Class clazz){
        for (Class declaredClass : clazz.getDeclaredClasses()) {
            if (declaredClass.isAnnotationPresent(Facets.class)) {
                for (Field field : declaredClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Facet.class)) {
                        fieldsForFacet.put(field.getName(), field);
                    }
                }
            }
        }
    }

    private void processBehaviors(Class clazz) {
        for (Behavior behavior: GeneratorContext.getInstance().getBehaviors()) {
            if (behavior.hasBehavior(clazz)) {
                System.out.println("Behavior found ");
                //attach behavior to the component context
                getBehaviors().add(behavior);
                behavior.addProperties(this);
            }
        }
    }
}
