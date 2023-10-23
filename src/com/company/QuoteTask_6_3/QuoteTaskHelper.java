package com.company.QuoteTask_6_3;

import java.util.*;
import java.util.concurrent.*;

public class QuoteTaskHelper {
    private final ExecutorService exec = Executors.newFixedThreadPool(5);

    public List<TravelQuote> getRankedTravelQuotes(
            TravelInfo travelInfo, Set<TravelCompany> companies, Comparator<TravelQuote> ranking, long time, TimeUnit unit)
            throws InterruptedException {

        List<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company, travelInfo));
        }

        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
        List<TravelQuote> quotes = new ArrayList<>(tasks.size());
        Iterator<QuoteTask> taskIter = tasks.iterator();
        for (Future<TravelQuote> f : futures) {
            QuoteTask task = taskIter.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                //not sure about implementation of getFailureQuote (to add dummy quote)
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                //not sure about implementation (to add dummy quote)
                quotes.add(task.getTimeoutQuote(e));
            }
        }

        Collections.sort(quotes, ranking);
        return quotes;
    }
}
