package com.company.PuzzleSolverRecursion_8_13to18;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Листинг 8.18 Решатель, с механизмом распознавания отсутствия решения
 * <p>
 * ИДЕЯ: сохранение количества активных задач решателя и присвоение решению значения
 * null при уменьшении количества активных задач до нуля
 *
 * @param <P>
 * @param <M>
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {
    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        super(puzzle, exec, seen);
    }

    class CountingSolverTask extends SolverTask {
        CountingSolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0)
                    solution.setValue(null);
            }
        }
    }
}
