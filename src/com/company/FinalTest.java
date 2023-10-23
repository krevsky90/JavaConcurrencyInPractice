package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the idea is: final means 'unable to  set other reference to this variable'
 */
public class FinalTest {
    public static void main(String[] args) {
        final Person p1 = new Person(1);
        final Person p2 = new Person(2);
//        p1 = p2;      ERROR

        //ok. despite p1 is final we can change state of this object
        p1.setI(5);

        final List<Person> finalList = new ArrayList<>(Arrays.asList(p1, p2));

        //ok
        finalList.remove(p1);


//        finalList = new ArrayList<>();        ERROR




    }
}
