package io.adtrace.sdk;

import java.util.HashMap;
import java.util.Map;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */


public class SessionParameters {
    Map<String, String> callbackParameters;
    Map<String, String> partnerParameters;

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        SessionParameters otherSessionParameters = (SessionParameters) other;

        if (!Util.equalObject(callbackParameters, otherSessionParameters.callbackParameters)) return false;
        if (!Util.equalObject(partnerParameters, otherSessionParameters.partnerParameters)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 37 * hashCode + Util.hashObject(callbackParameters);
        hashCode = 37 * hashCode + Util.hashObject(partnerParameters);
        return hashCode;
    }

    public SessionParameters deepCopy() {
        SessionParameters newSessionParameters = new SessionParameters();
        if (this.callbackParameters != null) {
            newSessionParameters.callbackParameters = new HashMap<String, String>(this.callbackParameters);
        }
        if (this.partnerParameters != null) {
            newSessionParameters.partnerParameters = new HashMap<String, String>(this.partnerParameters);
        }
        return newSessionParameters;
    }
}
