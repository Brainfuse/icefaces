 /*
 * Copyright 2004-2012 ICEsoft Technologies Canada Corp.
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

package org.icefaces.samples.showcase.example.ace.tooltip; 

import org.icefaces.samples.showcase.metadata.annotation.*;
import org.icefaces.samples.showcase.metadata.context.ComponentExampleImpl;

import javax.annotation.PostConstruct;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;


@ComponentExample(
        parent = TooltipOverviewBean.BEAN_NAME,
        title = "example.ace.delegateTooltip.title",
        description = "example.ace.delegateTooltip.description",
        example = "/resources/examples/ace/tooltip/delegateTooltip.xhtml"
)
@ExampleResources(
        resources ={
            // xhtml
            @ExampleResource(type = ResourceType.xhtml,
                    title="delegateTooltip.xhtml",
                    resource = "/resources/examples/ace/tooltip/delegateTooltip.xhtml"),
            // Java Source
            @ExampleResource(type = ResourceType.java,
                    title="DelegateTooltipBean.java",
                    resource = "/WEB-INF/classes/org/icefaces/samples/showcase/example/ace/tooltip/DelegateTooltipBean.java")
        }
)

@ManagedBean(name= DelegateTooltipBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class DelegateTooltipBean extends ComponentExampleImpl<DelegateTooltipBean> implements Serializable {

    public static final String BEAN_NAME = "delegateTooltipBean";
    /////////////---- CONSTRUCTOR BEGIN
    public DelegateTooltipBean() {
        super(DelegateTooltipBean.class);
    }

    @PostConstruct
    public void initMetaData() {
        super.initMetaData();
    }
	
	private Object data;
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}