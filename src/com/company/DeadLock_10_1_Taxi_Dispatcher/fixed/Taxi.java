package com.company.DeadLock_10_1_Taxi_Dispatcher.fixed;

import com.company.DeadLock_10_1_Taxi_Dispatcher.Point;

/**
 * Листинг 10.6 Использование открытых вызовов для исключения взаимоблокировки между взаимодействующими
 * объектами
 */
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

    //fix: it is not necessary lock th ewhole method, only its part regarding to locations
    public void setLocation(Point location) {
        boolean reachedDestination;
        synchronized (this) {
            this.location = location;
            reachedDestination = location.equals(destination);
        }

        if (reachedDestination)
            dispatcher.notifyAvailable(this);
    }
}
