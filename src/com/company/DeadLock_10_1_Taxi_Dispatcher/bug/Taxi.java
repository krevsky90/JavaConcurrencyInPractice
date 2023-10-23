package com.company.DeadLock_10_1_Taxi_Dispatcher.bug;

import com.company.DeadLock_10_1_Taxi_Dispatcher.Point;

public class Taxi {
    private Point location;
    private Point destination;

    private final Dispatcher dispatcher;
    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    public synchronized Point getLocation() {
        return location;
    }
    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination))
            dispatcher.notifyAvailable(this);
    }
}
