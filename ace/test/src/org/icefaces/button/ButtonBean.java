/* * Version: MPL 1.1 * * The contents of this file are subject to the Mozilla Public License * Version 1.1 (the "License"); you may not use this file except in * compliance with the License. You may obtain a copy of the License at * http://www.mozilla.org/MPL/ * * Software distributed under the License is distributed on an "AS IS" * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the * License for the specific language governing rights and limitations under * the License. * * The Original Code is ICEfaces 1.5 open source software code, released * November 5, 2006. The Initial Developer of the Original Code is ICEsoft * Technologies Canada, Corp. Portions created by ICEsoft are Copyright (C) * 2004-2011 ICEsoft Technologies Canada, Corp. All Rights Reserved. * * Contributor(s): _____________________. */package showcase.test.src.org.icefaces.button;import javax.faces.bean.ViewScoped;import javax.faces.bean.ManagedBean;import javax.faces.event.ActionEvent;import javax.faces.event.ValueChangeEvent;import java.io.Serializable;@ManagedBean (name="button")@ViewScopedpublic class ButtonBean implements Serializable{    private int id=0;	private boolean checked = false;    private boolean checked2 = false;	private String label = "ActionListener";    private boolean rendered=false;	private String extraData="extra";	private String firstName="";	private String lastName="";	private String selectedRadio=null;	private String selectedRadio2=null;	private boolean selectedRadioCheck=false;	public boolean isSelectedRadioCheck() {		return selectedRadioCheck;	}	public void setSelectedRadioCheck(boolean selectedRadioCheck) {		this.selectedRadioCheck = selectedRadioCheck;	}	public String getSelectedRadio2() {		return selectedRadio2;	}	public void setSelectedRadio2(String selectedRadio2) {		this.selectedRadio2 = selectedRadio2;	}	public String getSelectedRadio() {		return selectedRadio;	}	public void setSelectedRadio(String selectedRadio) {		this.selectedRadio = selectedRadio;	}	private String checkButtonLabel="check";	public ButtonBean(){		//no arg constructor is required	}      public ButtonBean(int lId){      this.id=lId;   }       public boolean isChecked() {    	System.out.println("BUTTONBean: isChecked()="+checked);        return checked;    }    public void setChecked(boolean selected) {        this.checked = selected;       	System.out.println("backing bean setChecked = "+this.checked);    }	    public String getLabel() {        return label;    }        public String getCheckButtonLabel(){    	return this.checkButtonLabel;    }    public void setCheckButtonLabel(String inlabel){    	this.checkButtonLabel = inlabel;    }    public void setLabel(String label) {        this.label = label;    }        public String methodAction(){    	System.out.println("BB: methodAction() ");    	//should return another page for redirection?    	rendered=!rendered;    	return "action";    }    public void actionListenerMethod(ActionEvent e) {        System.out.println("BB: actionListenerMethod()  e: " + e);        rendered=!rendered;    }        public boolean isRendered(){    	return this.rendered;    }    public boolean isChecked2(){      	System.out.println("backing bean isChecked2 = "+this.checked2);    	return this.checked2;    }    public void setChecked2(boolean incheck){    	this.checked2 = incheck;      	System.out.println("BB setChecked2 = "+this.checked2);    }	public String getExtraData() {		return extraData;	}	public void setExtraData(String extraData) {		this.extraData = extraData;	}	public String getFirstName() {		return firstName;	}	public void setFirstName(String firstName) {		this.firstName = firstName;	}	public String getLastName() {		return lastName;	}	public void setLastName(String lastName) {		this.lastName = lastName;	}		public void showValues(ValueChangeEvent event){		System.out.println("Value changes from "+event.getOldValue()+" to:"+event.getNewValue());	}	public void setId(int newId){		this.id = newId;	}	public int getId(){		return this.id;	}}