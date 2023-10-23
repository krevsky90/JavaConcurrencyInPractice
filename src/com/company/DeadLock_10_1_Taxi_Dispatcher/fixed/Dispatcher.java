package com.company.DeadLock_10_1_Taxi_Dispatcher.fixed;

import com.company.DeadLock_10_1_Taxi_Dispatcher.Image;

import java.util.HashSet;
import java.util.Set;

/**
 * Листинг 10.6 Использование открытых вызовов для исключения взаимоблокировки между взаимодействующими
 * объектами
 */
public class Dispatcher {
    private final Set<Taxi> taxis;
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<>();
        availableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public Image getImage() {
        //fix: copy state of all taxis and use it insteade of original
        Set<Taxi> copy;
        synchronized (this) {
            copy = new HashSet<Taxi>(taxis);
        }

        Image image = new Image();
        for (Taxi t : copy)
            image.drawMarker(t.getLocation());
        return image;
    }
}
