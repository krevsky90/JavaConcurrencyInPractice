package com.company.listing4_12;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс PublishingVehicleTracker наследует потокобезопасность путём
 * делегирования базовому классу ConcurrentHashMap, но на этот раз содержимым
 * класса Map являются потокобезопасные изменяемые точки, а не неизменяемые (в отличие от listing4_7)
 */
public class PublishingVehicleTracker {
    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String, SafePoint> locations) {
        this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException("invalid vehicle name:" + id);
        }

        locations.get(id).set(x, y);
    }
}
