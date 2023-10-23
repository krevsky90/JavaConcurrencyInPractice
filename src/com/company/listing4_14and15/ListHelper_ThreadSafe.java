package com.company.listing4_14and15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ListHelper_ThreadSafe<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    /**
     * correct block on client (i.e. Helper-class) side
     */
    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }

            return absent;
        }
    }
}
