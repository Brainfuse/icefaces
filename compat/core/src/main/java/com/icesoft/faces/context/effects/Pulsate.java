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

package com.icesoft.faces.context.effects;

/**
 * Pulsate or flash an HTML element
 */
public class Pulsate extends Effect {
    private float duration;

    /**
     * Pulsate for 5 seconds
     */
    public Pulsate() {
        setDuration(5.0f);
    }

    /**
     * Pulsate for a givin duration (In seconds)
     *
     * @param duration
     */
    public Pulsate(float duration) {
        setDuration(duration);
    }

    /**
     * Get the duration (Seconds)
     *
     * @return
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Set the duration (Seconds)
     *
     * @param duration
     */
    public void setDuration(float duration) {
        this.duration = duration;
        ea.add("duration", duration);
    }


    /**
     * Get the Javascript function name
     *
     * @return
     */
    public String getFunctionName() {
        return "Ice.Scriptaculous.Effect.Pulsate";
    }

    public int hashCode() {
        return EffectHashCode.PULSTATE * ((int) duration * 100);
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Pulsate)) {
            return false;
        }
        Pulsate effect = (Pulsate) obj;
        if (duration != effect.duration) {
            return false;
        }
        return true;
    }
}
