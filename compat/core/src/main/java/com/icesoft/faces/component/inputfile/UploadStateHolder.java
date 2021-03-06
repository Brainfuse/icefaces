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

package com.icesoft.faces.component.inputfile;

/**
 * The UploadServer can start a JSF lifecycle either in its own thread, or on 
 * another one. So there has to be a way of passing the state to the 
 * appropriate thread, where it will be put into a thread local field, so that 
 * the FileUploadPhaseListener can find it and
 * 
 * 
 * @author mcollette
 * @since 1.8
 */
public class UploadStateHolder implements Runnable {
    private static ThreadLocal holder = new ThreadLocal();
    
    private UploadConfig uploadConfig;
    private FileInfo fileInfo;
    private boolean asyncLifecycle;
    private String iframeContent;
    
    public UploadStateHolder(UploadConfig uploadConfig, FileInfo fileInfo) {
        this.uploadConfig = uploadConfig;
        this.fileInfo = fileInfo;
    }
    
    public UploadConfig getUploadConfig() {
        return uploadConfig;
    }
    
    public FileInfo getFileInfo() {
        return fileInfo;
    }
    
    public boolean isAsyncLifecycle() {
        return asyncLifecycle;
    }
    
    public void setAsyncLifecycle(boolean asyncLifecycle) {
        this.asyncLifecycle = asyncLifecycle;
    }
        
    public String getIframeContent() {
        return iframeContent;
    }
    
    public void setIframeContent(String iframeContent) {
        this.iframeContent = iframeContent;
    }

    /**
     * Save this state into the current thread's local storage
     */
    public void install() {
        holder.set(this);
    }

    /**
     * Retrieve the state from the current thread's local storage
     */
    public static UploadStateHolder take() {
        UploadStateHolder current = (UploadStateHolder) holder.get();
        holder.set(null);
        return current;
    }

    /**
     * Deferred install, for when we'll be running on another thread
     */
    public void run() {
        install();
    }
}
