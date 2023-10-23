package com.company.DeadLock_10_1_Taxi_Dispatcher.bug;

import com.company.DeadLock_10_1_Taxi_Dispatcher.Point;

public class TestDispatcherTaxi {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        Taxi taxi = new Taxi(dispatcher);

        //since getImage is synchronized method -> it blocks dispatcher object
        //since getImage calls Taxi#getLocation (which is also synchronized method) -> it blocks Taxi object
        dispatcher.getImage();

        //since setLocation is synchronized method -> it blocks Taxi object
        //since setLocation calls Dispatcher#notifyAvailable() method (which is also synchronized method) -> it blocks Dispatcher object
        taxi.setLocation(new Point());

        //Summary: we have two locks in different sequence -> it may cause deadlock
    }
}
