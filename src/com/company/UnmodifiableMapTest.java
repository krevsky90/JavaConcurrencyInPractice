package com.company;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UnmodifiableMapTest {
    public static void main(String[] args) {
        Map<Integer, Person> map = new HashMap<>();
        Person person1 = new Person(1);
        Person person2 = new Person(2);
        map.put(person1.getI(), person1);
        map.put(person2.getI(), person2);

        Map<Integer, Person> unmodifiableMap = Collections.unmodifiableMap(map);

        Person person3 = new Person(3);

        map.put(person3.getI(), person3);
        //now map and unmodifiableMap contain 3 elements!

//        unmodifiableMap.put(person3.getI(), person3);   //throws UnsupportedOperationException

        //ok
        person3.setI(34);
    }
}
