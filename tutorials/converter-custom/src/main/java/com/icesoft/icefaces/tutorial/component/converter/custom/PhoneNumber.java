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

package com.icesoft.icefaces.tutorial.component.converter.custom;

/**
 * Backing bean for the converter example PhoneNumber object.
 */
public class PhoneNumber {
    
    private String countryCode;
    private String areaCode;
    private String prefix;
    private String number;
    
    public String getCountryCode(){
        return countryCode;
    }
    
    public void setCountryCode(String countryCode){
        this.countryCode = countryCode;
    }
    
    public String getAreaCode(){
        return areaCode;
    }
    
    public void setAreaCode(String areaCode){
        this.areaCode = areaCode;
    }
    
    public String getPrefix(){
        return prefix;
    }
    
    public void setPrefix(String prefix){
        this.prefix = prefix;
    }
    
    public String getNumber(){
        return number;
    }
    
    public void setNumber(String number){
        this.number = number;
    }
    
    public String toString(){
        if(countryCode.equals("1")){
            return countryCode + "-" + areaCode + "-" + prefix + "-" + number;
        }
        else{
            return number;
        }
    }
    
    
}
