package com.company.listing4_14and15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListHelper_NotThreadSafe<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    /**
     * Error! Client class ListHelper uses incorrect block: it blocks element x, but we need synchronize the whole list!
     * this operation uses different blocks (i.e. objects x) it this method is called from sevaral threads
     */
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }

        return absent;
    }
}
