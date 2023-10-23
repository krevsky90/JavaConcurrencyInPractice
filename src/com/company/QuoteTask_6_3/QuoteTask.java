package com.company.QuoteTask_6_3;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

public class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany company;
    private final TravelInfo travelInfo;

    private final static TravelQuote dummyTravelQuote = new TravelQuote();

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }

    public TravelQuote getFailureQuote(Throwable cause) {
        System.out.println(cause.getCause());
        return dummyTravelQuote;
    }

    public TravelQuote getTimeoutQuote(CancellationException ex) {
        System.out.println(ex);
        return dummyTravelQuote;
    }

}
