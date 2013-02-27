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

package org.icefaces.ace.generator.artifacts;

import java.lang.reflect.Field;
import java.util.List;

import org.icefaces.ace.generator.context.ComponentContext;
import org.icefaces.ace.generator.utils.FileWriter;
import org.icefaces.ace.generator.utils.Utility;

import org.icefaces.ace.generator.utils.PropertyValues;
import org.icefaces.ace.meta.annotation.Component;
import org.icefaces.ace.meta.annotation.Expression;

public class ComponentHandlerArtifact extends Artifact{
    private StringBuilder generatedComponentHandlerClass;

	public ComponentHandlerArtifact(ComponentContext componentContext) {
		super(componentContext);
	}

	@Override
	public void build() {
        Component component = (Component) getComponentContext().getActiveClass().getAnnotation(Component.class);
        if(Utility.isManualHandlerClass(component)) return;
        if (!getComponentContext().isHasMethodExpression()) return;
        startComponentClass(getComponentContext().getActiveClass(), component);
        addRules(getComponentContext().getPropertyFieldsForComponentClassAsList());
        endComponentClass();
		
	}
    
    private void startComponentClass(Class clazz, Component component) {
        //initialize
        generatedComponentHandlerClass = new StringBuilder();
        
        generatedComponentHandlerClass.append("package ");
        generatedComponentHandlerClass.append(Utility.getPackageNameOfClass(Utility.getGeneratedClassName(component)));
        generatedComponentHandlerClass.append(";\n\n");
        generatedComponentHandlerClass.append("import javax.faces.view.facelets.ComponentHandler;\n");
        generatedComponentHandlerClass.append("import javax.faces.view.facelets.ComponentConfig;\n");
        generatedComponentHandlerClass.append("import javax.faces.view.facelets.MetaRuleset;\n");
        generatedComponentHandlerClass.append("import org.icefaces.facelets.tag.icefaces.core.MethodRule;\n\n");
        generatedComponentHandlerClass.append("import java.util.EventObject;\n");
        generatedComponentHandlerClass.append("/*\n * ******* GENERATED CODE - DO NOT EDIT *******\n */\n");
        
        generatedComponentHandlerClass.append("public class ");
        generatedComponentHandlerClass.append(Utility.getSimpleNameOfClass(Utility.getHandlerClassName(component)));
        generatedComponentHandlerClass.append(" extends ComponentHandler");
        generatedComponentHandlerClass.append("{\n\n");
        
        generatedComponentHandlerClass.append("\n\tpublic ");
        generatedComponentHandlerClass.append(Utility.getSimpleNameOfClass(Utility.getHandlerClassName(component)));
        generatedComponentHandlerClass.append("(ComponentConfig componentConfig) {\n");
        generatedComponentHandlerClass.append("\t\tsuper(componentConfig);\n");
        generatedComponentHandlerClass.append("\t}\n\n\n");
    }

    
    private void endComponentClass() {
        generatedComponentHandlerClass.append("\n}");
        createJavaFile();

    }

    private void createJavaFile() {
        Component component = (Component) getComponentContext().getActiveClass().getAnnotation(Component.class);
        String fileName = Utility.getSimpleNameOfClass(Utility.getHandlerClassName(component)) + ".java";
        String pack = Utility.getPackageNameOfClass(Utility.getHandlerClassName(component));
        String path = pack.replace('.', '/') + '/'; //substring(0, pack.lastIndexOf('.'));
        System.out.println("_________________________________________________________________________");
        System.out.println("File name "+ fileName);
        System.out.println("path  "+ path);        
        FileWriter.write("support", path, fileName, generatedComponentHandlerClass);        
    }

    private void addRules(List<Field> fields) {
        generatedComponentHandlerClass.append("\tprotected MetaRuleset createMetaRuleset(Class type) {\n");  
        generatedComponentHandlerClass.append("\t\tMetaRuleset metaRuleset = super.createMetaRuleset(type);\n");  
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
			PropertyValues prop = getComponentContext().getPropertyValuesMap().get(field);
			
            if (prop.expression == Expression.METHOD_EXPRESSION) {
                generatedComponentHandlerClass.append("\t\tmetaRuleset.addRule( new MethodRule(\"");
                generatedComponentHandlerClass.append(field.getName());
                generatedComponentHandlerClass.append("\", null, new Class[");
                if (prop.methodExpressionArgument.length() > 0) {
                    generatedComponentHandlerClass.append("] {");
                    generatedComponentHandlerClass.append(prop.methodExpressionArgument);
                    generatedComponentHandlerClass.append(".class}");   
                } else {
                    generatedComponentHandlerClass.append("0]");  
                }
                generatedComponentHandlerClass.append(") );\n");
            }

        }
        generatedComponentHandlerClass.append("\t\n\t\treturn metaRuleset;\n");
        generatedComponentHandlerClass.append("\t}");
    }
}
