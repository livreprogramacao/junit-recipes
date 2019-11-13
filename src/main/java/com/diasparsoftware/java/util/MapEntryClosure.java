package com.diasparsoftware.java.util;

import org.apache.commons.collections.Closure;

import java.util.Map;

public abstract class MapEntryClosure implements Closure {
    public final void execute(Object each) {
        Map.Entry eachEntry = (Map.Entry) each;
        eachMapEntry(eachEntry.getKey(), eachEntry.getValue());
    }

    public abstract void eachMapEntry(Object key, Object value);
}
