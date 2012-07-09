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

if (!window['ice']) window.ice = {};
if (!window.ice['ace']) window.ice.ace = {};
if (!window.ice.ace['jq']) ice.ace.jq = jQuery;
jQuery.noConflict(true);
// After core is loaded after / as part of combined.js core will need to have
// this object pulled apart so as no to overwrite existing members of ice.ace that
// may come before it. Alternatively the generator could explictly add core.js earlier
// in the merged script than most.


// Useful prototypes
// TODO: find better home for this and other util functions s
Array.prototype.diff = function(a) {
    return this.filter(function(i) {return !(a.indexOf(i) > -1);});
};


ice.ace.escapeClientId = function(id) {
    return "#" + id.replace(/:/g,"\\:");
};

ice.ace.cleanWatermarks = function(){
    ice.ace.jq.watermark.hideAll();
};

ice.ace.showWatermarks = function(){
    ice.ace.jq.watermark.showAll();
};

ice.ace.addSubmitParam = function(parent, name, value) {
    ice.ace.jq(this.escapeClientId(parent)).append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    return this;
};

ice.ace.submit = function(formId) {
    ice.ace.jq(this.escapeClientId(formId)).submit();
};

ice.ace.attachBehaviors = function(element, behaviors) {
    for (var event in behaviors)
        element.bind(event, function() { ice.ace.ab.call(element, behaviors[event]); });
};

ice.ace.getCookie = function(name) {
    return ice.ace.jq.cookie(name);
};

ice.ace.setCookie = function(name, value) {
    ice.ace.jq.cookie(name, value);
};

ice.ace.skinInput = function(input) {
    input.hover(
        function() {
            ice.ace.jq(this).addClass('ui-state-hover');
        },
        function() {
            ice.ace.jq(this).removeClass('ui-state-hover');
        }
    ).focus(function() {
            ice.ace.jq(this).addClass('ui-state-focus');
    }).blur(function() {
            ice.ace.jq(this).removeClass('ui-state-focus');
    });
};

ice.ace.bind = function(context, method) {
    return function() {
        return method.apply(context, arguments);
    }
};

ice.ace.eachExtension = function(responseXML, iterator) {
    var xmlDoc = responseXML.documentElement;
    var extensions = xmlDoc.getElementsByTagName("extension");
    for (var i = 0, l = extensions.length; i < l; i++) {
        iterator(extensions[i]);
    }
};

ice.ace.selectCustomUpdates = function(responseXML, iterator) {
    ice.ace.eachExtension(responseXML, function(extension) {
        if (extension.getAttributeNode('ice.customUpdate')) {
            var id = extension.attributes.getNamedItem("id").nodeValue;
            var content = extension.firstChild.data;
            iterator(id, content);
        }
    });
};

ice.ace.removeExecuteRenderOptions = function(options) {
    options['execute'] = undefined;
    options['render'] = undefined;
    return options;
}

ice.ace.clone = function(obj) {
    // Handle the 3 simple types, and null or undefined
    if (null == obj || "object" != typeof obj) return obj;

    // Handle Date
    if (obj instanceof Date) {
        var copy = new Date();
        copy.setTime(obj.getTime());
        return copy;
    }

    // Handle Array
    if (obj instanceof Array) {
        var copy = [];
        var len;
        for (var i = 0, len = obj.length; i < len; ++i) {
            copy[i] = ice.ace.clone(obj[i]);
        }
        return copy;
    }

    // Handle Object
    if (obj instanceof Object) {
        var copy = {};
        for (var attr in obj) {
            if (obj.hasOwnProperty(attr)) copy[attr] = ice.ace.clone(obj[attr]);
        }
        return copy;
    }

    throw new Error("Unable to copy obj! Its type isn't supported.");
}

ice.ace.extend = function(targetObject, sourceObject) {
    for (var attrname in sourceObject) { targetObject[attrname] = sourceObject[attrname]; }
}

ice.ace.extendAjaxArguments = function(callArguments, options) {
    // Return a modified copy of the original arguments instead of modifying the original.
    // The cb arguments, being a configured property of the component will live past this request.
    callArguments = ice.ace.clone(callArguments);

    var params     = options.params,
        execute    = options.execute,
        render     = options.render,
        node       = options.node,
        onstart    = options.onstart,
        onerror    = options.onerror,
        onsuccess  = options.onsuccess,
        oncomplete = options.oncomplete;

    if (params) {
        if (callArguments['params'])
            ice.ace.extend(callArguments['params'], params);
        else
            callArguments['params'] = params;
    }

    if (execute) {
        if (callArguments['execute'])
            callArguments['execute'] = callArguments['execute'] + " " + execute;
        else
            callArguments['execute'] = execute;
    }

    if (render) {
        if (callArguments['render'])
            callArguments['render'] = callArguments['render'] + " " + render;
        else
            callArguments['render'] = render;
    }

    if (node) {
        callArguments['node'] = node;
    }

    if (onstart) {
        if (callArguments['onstart']) {
            var existingStartCall = callArguments['onstart'];
            callArguments['onstart'] = function(xhr) {
                existingStartCall(xhr);
                onstart(xhr);
            }
        } else {
            callArguments['onstart'] = onstart;
        }
    }

    if (onerror) {
        if (callArguments['onerror']) {
            var existingErrorCall = callArguments['onerror'];
            callArguments['onerror'] = function(xhr, status, error) {
                existingErrorCall(xhr, status, error);
                onerror(xhr, status, error);
            }
        } else {
            callArguments['onerror'] = onerror;
        }
    }

    if (onsuccess) {
        if (callArguments['onsuccess']) {
            var existingSuccessCall = callArguments['onsuccess'];
            callArguments['onsuccess'] = function(data, status, xhr, args) {
                existingSuccessCall(data, status, xhr, args);
                onsuccess(data, status, xhr, args);
            }
        } else {
            callArguments['onsuccess'] = onsuccess;
        }
    }

    if (oncomplete) {
        if (callArguments['oncomplete']) {
            var existingCompleteCall = callArguments['oncomplete'];
            callArguments['oncomplete'] = function(xhr, status, args) {
                existingCompleteCall(xhr, status, args);
                oncomplete(xhr, status, args);
            }
        } else {
            callArguments['oncomplete'] = oncomplete;
        }
    }
    
    return callArguments;
}

ice.ace.ab = function(cfg) { ice.ace.AjaxRequest(cfg); };
ice.ace.locales = {};
ice.ace.PARTIAL_REQUEST_PARAM = "javax.faces.partial.ajax";
ice.ace.PARTIAL_UPDATE_PARAM = "javax.faces.partial.render";
ice.ace.PARTIAL_PROCESS_PARAM = "javax.faces.partial.execute";
ice.ace.PARTIAL_SOURCE_PARAM = "javax.faces.source";
ice.ace. BEHAVIOR_EVENT_PARAM = "javax.faces.behavior.event";
ice.ace.PARTIAL_EVENT_PARAM = "javax.faces.partial.event";
ice.ace.VIEW_STATE = "javax.faces.ViewState";

ice.ace.AjaxUtils = {
    updateElement: function(id, content) {
        ice.ace.jq(ice.ace.escapeClientId(id)).replaceWith(content);

        //Mobile
        if(ice.ace.jq.mobile) {
            var controls = ice.ace.jq(ice.ace.escapeClientId(id)).parent().find("input, textarea, select, button, ul");

            //input and textarea
            controls
                .filter("input, textarea")
                .not("[type='radio'], [type='checkbox'], [type='button'], [type='submit'], [type='reset'], [type='image'], [type='hidden']")
                .textinput();

            //lists
            controls.filter("[data-role='listview']").listview();

            //buttons
            controls.filter("button, [type='button'], [type='submit'], [type='reset'], [type='image']" ).button();

            //slider
            controls.filter("input, select")
                    .filter("[data-role='slider'], [data-type='range']")
                    .slider();

            //selects
            controls.filter("select:not([data-role='slider'])" ).selectmenu();
        }
    }
};

ice.ace.AjaxRequest = function(cfg) {
    // If start events return false, cancel request
    if(cfg.onstart && !cfg.onstart.call(this))
       return;

    // Find the source DOM element
    var sourceElement;
    // If the source input is not a valid id ('@this', etc.), but an source node has been explicitly passed
    if (!document.getElementById(cfg.source) && cfg.node) {
        // Set the source element to the node
        sourceElement = cfg.node;
        // Set the source element id to the invalid source string as it will be used by the bridge as the source.
        sourceElement.id = cfg.source;
    } else
        // If the source input is a string attempt to find it as an id, else assume it is an element and assign it
        sourceElement = (typeof cfg.source == 'string') ? document.getElementById(cfg.source) : cfg.source;

    // If the attempts to find a source element from the input string failed, or there was no source input
    if (!sourceElement) {
        // Find form element to submit as event source
        var form = ice.ace.jq(ice.ace.escapeClientId(cfg.source)).parents('form:first');
        if(form.length == 0)
            form = ice.ace.jq(cfg.node).parents('form:first');
        if(form.length == 0)
            form = ice.ace.jq('form').eq(0);

        sourceElement = form;
    }

    // Defaults for execute and render
    var jsfExecute = cfg.execute || '@all';
    var jsfRender = cfg.render || '@all';

    // Create IF bridge request
    ice.fullSubmit(jsfExecute, jsfRender, null, sourceElement, function(parameter) {
        // Convert PF-style parameters to IF-style
        if(cfg.event) {
            parameter(ice.ace.BEHAVIOR_EVENT_PARAM, cfg.event);

            var domEvent = cfg.event;
            if(cfg.event == 'valueChange') {
                domEvent = 'change';
            } else if (cfg.event == 'action') {
                domEvent = 'click';
            }

            parameter(ice.ace.PARTIAL_EVENT_PARAM, domEvent);
        } else {
            parameter(cfg.source, cfg.source);
        }

        if(cfg.params) {
            var cfgParams = cfg.params;
            for(var p in cfgParams) {
                parameter(p, cfgParams[p]);
            }
        }
    }, function(onBeforeSubmit, onBeforeUpdate, onAfterUpdate, onNetworkError, onServerError) {
        // Define IF callbacks which map to PF/ace:ajax style callbacjs
        var context = {};
        onAfterUpdate(function(responseXML) {
            // Do onsuccess
            if (cfg.onsuccess && !cfg.onsuccess.call(context, responseXML, null /*status*/, null /*xhr*/))
                return;

            // Decode special customUpdate and callbackParam request portions
            ice.ace.AjaxResponse.call(context, responseXML);
        });

        if (cfg.oncomplete) {
            // Do oncomplete
            onAfterUpdate(function(responseXML) {
                cfg.oncomplete.call(context, null /*xhr*/, null /*status*/, context.args);
            });
        }

        if (cfg.onerror) {
            // Do onerror
            onNetworkError(function(responseCode, errorDescription) {
                cfg.onerror.call(context, null /*xhr*/, responseCode /*status*/, errorDescription /*error description*/)
            });
            onServerError(function(responseCode, responseText) {
                cfg.onerror.call(context, null /*xhr*/, responseCode /*status*/, responseText /*error description*/)
            });
        }
    });
};

ice.ace.AjaxResponse = function(responseXML) {
    var xmlDoc = responseXML.documentElement;
    var extensions = xmlDoc.getElementsByTagName("extension");

    this.args = {};
    for(var i = 0, l = extensions.length; i < l; i++) {
        var extension = extensions[i];
        if (extension.getAttributeNode('aceCallbackParam')) {
            var jsonObj = JSON.parse(extension.firstChild.data);
            for(var paramName in jsonObj) {
                if(paramName) {
                    this.args[paramName] = jsonObj[paramName];
                }
            }
        }
        if (extension.getAttributeNode('ice.customUpdate')) {
            var id = extension.attributes.getNamedItem("id").nodeValue;
            var content = extension.firstChild.data;
            ice.ace.AjaxUtils.updateElement(id, content);
        }
    }
};


ice.ace.getOpacity = function(elem) {
    var ori = ice.ace.jq(elem).css('opacity');
    var ori2 = ice.ace.jq(elem).css('filter');
    if (ori2) {
        ori2 = parseInt( ori2.replace(')','').replace('alpha(opacity=','') ) / 100;
        if (!isNaN(ori2) && ori2 != '') {
            ori = ori2;
        }
    }
    return ori;
}


/* General Utilities */

/**
 * Adding startsWith to String prototype
 */
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.slice(0, str.length) == str;
  };
}


/**
 * jQuery Cookie plugin
 *
 * Copyright (c) 2010 Klaus Hartl (stilbuero.de)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 */
var caretPositionAmp = {};
new function($) {
     $.fn.setCaretPosition = function(inputStr) {
        var input = this.jquery ? this[0] : this;
        var s;
        var re;
        var position;
        var number = 0;
        var minus = 0;
        var w;
        var obj = document.getElementsByTagName('TEXTAREA');
        var pos = 0;
        for (pos; pos < obj.length; pos++) {
            if (obj[pos] == input) {
                break;
            }
        }
        input.focus();
        if (parseInt(inputStr) == 0) {
            return this;
        }

        if ($(input).get(0).setSelectionRange) {
            $(input).get(0).setSelectionRange(pos, pos);
        } else if ($(input).get(0).createTextRange) {
            var range = $(input).get(0).createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        }

        if (parseInt(inputStr) > 0) {
            inputStr = parseInt(inputStr) - 1;
            if (document.selection && typeof(input.selectionStart) == "number" && input.selectionStart == input.selectionEnd) {
                if (input.value.match(/\n/g) != null) {
                    number = input.value.match(/\n/g).length;// number of EOL simbols
                }
                if (number > 0) {
                    for (var i = 0; i <= number; i++) {
                        w = input.value.indexOf("\n", position);
                        if (w != -1 && w <= inputStr) {
                            position = w + 1;
                            inputStr = parseInt(inputStr) + 1;
                        }
                    }
                }
            }
        }
        else if (parseInt(inputStr) < 0) {
            inputStr = parseInt(inputStr) + 1;
            if (document.selection && typeof(input.selectionStart) != "number") {
                inputStr = input.value.length + parseInt(inputStr);
                if (input.value.match(/\n/g) != null) {
                    number = input.value.match(/\n/g).length;// number of EOL simbols
                }
                if (number > 0) {
                    for (var i = 0; i <= number; i++) {
                        w = input.value.indexOf("\n", position);
                        if (w != -1 && w <= inputStr) {
                            position = w + 1;
                            inputStr = parseInt(inputStr) - 1;
                            minus += 1;
                        }
                    }
                    inputStr = inputStr + minus - number;
                }
            } else if (document.selection && typeof(input.selectionStart) == "number") {
                inputStr = input.value.length + parseInt(inputStr);
                if (input.value.match(/\n/g) != null) {
                    number = input.value.match(/\n/g).length;// number of EOL simbols
                }
                if (number > 0) {
                    inputStr = parseInt(inputStr) - number;
                    for (var i = 0; i <= number; i++) {
                        w = input.value.indexOf("\n", position);
                        if (w != -1 && w <= (inputStr)) {
                            position = w + 1;
                            inputStr = parseInt(inputStr) + 1;
                            minus += 1;
                        }
                    }
                }
            } else { inputStr = input.value.length + parseInt(inputStr); }
        } else { return this; }
        // IE
        if (document.selection && typeof(input.selectionStart) != "number") {
            s = document.selection.createRange();
            if (s.text != 0) {
                return this;
            }
            re = input.createTextRange();
            re.collapse(true);
            re.moveEnd('character', inputStr);
            re.moveStart('character', inputStr);
            re.select();
            caretPositionAmp[pos] = inputStr;
            return this;
        } else if (typeof(input.selectionStart) == "number" && // MOZILLA support
                input.selectionStart == input.selectionEnd) {
            input.setSelectionRange(inputStr, inputStr);
            return this;
        }
        return this;
    }
    $.fn.setCaretToEnd = function() { $(this).setCaretPosition(-1); }
}(ice.ace.jq);

ice.ace.jq.cookie = function (key, value, options) {

    // key and value given, set cookie...
    if (arguments.length > 1 && (value === null || typeof value !== "object")) {
        options = ice.ace.jq.extend({}, options);

        if (value === null) {
            options.expires = -1;
        }

        if (typeof options.expires === 'number') {
            var days = options.expires, t = options.expires = new Date();
            t.setDate(t.getDate() + days);
        }

        return (document.cookie = [
            encodeURIComponent(key), '=',
            options.raw ? String(value) : encodeURIComponent(String(value)),
            options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
            options.path ? '; path=' + options.path : '',
            options.domain ? '; domain=' + options.domain : '',
            options.secure ? '; secure' : ''
        ].join(''));
    }

    // key and possibly options given, get cookie...
    options = value || {};
    var result, decode = options.raw ? function (s) {return s;} : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

// Original code copied from http://stackoverflow.com/a/7329696
// See comments at http://jira.icesoft.org/browse/ICE-7824?focusedCommentId=39755&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#action_39755
ice.ace.findNextTabElement = function(currElement) {
            // if we haven't stored the tabbing order
            if (!currElement.form.tabOrder) {

                var els = currElement.form.elements,
                    ti = [],
                    rest = [];

                // store all focusable form elements with tabIndex > 0
                for (var i = 0, il = els.length; i < il; i++) {
                    if (els[i].tabIndex > 0 &&
                        !els[i].disabled &&
                        !els[i].hidden &&
                        !els[i].readOnly &&
                        els[i].type !== 'hidden') {
                        ti.push(els[i]);
                    }
                }

                // sort them by tabIndex order
                ti.sort(function(a,b){ return a.tabIndex - b.tabIndex; });

                // store the rest of the elements in order
                for (i = 0, il = els.length; i < il; i++) {
                    if (els[i].tabIndex == 0 &&
                        !els[i].disabled &&
                        !els[i].hidden &&
                        !els[i].readOnly &&
                        els[i].type !== 'hidden') {
                        rest.push(els[i]);
                    }
                }

                // store the full tabbing order
                currElement.form.tabOrder = ti.concat(rest);
            }

            // find the next element in the tabbing order and focus it
            // if the last element of the form then blur
            // (this can be changed to focus the next <form> if any)
            for (var j = 0, jl = currElement.form.tabOrder.length; j < jl; j++) {
                if (currElement === currElement.form.tabOrder[j]) {
                    if (j+1 < jl) {
//                        $(this.form.tabOrder[j+1]).focus();
                        return currElement.form.tabOrder[j+1];
                    } else {
//                        $(this).blur();
                    }
                }
            }
};
