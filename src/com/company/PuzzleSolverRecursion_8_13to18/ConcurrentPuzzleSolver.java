package com.company.PuzzleSolverRecursion_8_13to18;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/**
 * Листинг 8.16 Параллельная версия решателя головоломок
 *
 * ПРОБЛЕМА: если нет решений, то ожидание будет вечно!
 * РЕШЕНИЕ: PuzzleSolver
 *
 * ЗАКОММЕНТИЛ проблемные (из-за Generic) куски кода
 *
 * @param <P>
 * @param <M>
 */
public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentMap<P, Boolean> seen;
    final ValueLatch<Node<P, M>> solution = new ValueLatch<Node<P, M>>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        this.puzzle = puzzle;
        this.exec = exec;
        this.seen = seen;
    }

    public List<M> solve() throws InterruptedException {
        try {
            P p = puzzle.initialPosition();
            exec.execute(newTask(p, null, null));
            // block until solution found
            //ПРОБЛЕМА: если нет решений, то ожидание будет вечно!
            Node solnNode = solution.getValue();
            return (solnNode == null) ? null : solnNode.asMoveList();
        } finally {
            exec.shutdown();
        }
    }

    protected Runnable newTask(P p, M m, Node n) {
        return new SolverTask(p, m, n);
    }

    class SolverTask<P, M> extends Node<P, M> implements Runnable {
        public SolverTask(P pos, M move, Node prev) {
            super(pos, move, prev);
        }

        public void run() {
//            if (solution.isSet() || seen.putIfAbsent(pos, true) != null) {
//                return; // already solved or seen this position
//            }
//
//            if (puzzle.isGoal(pos)) {
//                solution.setValue(this);
//            } else {
//                for (M m : (Set<M>) puzzle.legalMoves(pos)) {
//                    exec.execute(newTask(puzzle.move(pos, m), m, this));
//                }
//            }
        }
    }

    class Node<P, M> {
        final P pos;
        final M move;
        final Node prev;

        public Node(P pos, M move, Node prev) {
            this.pos = pos;
            this.move = move;
            this.prev = prev;
        }

        public List<M> asMoveList() {
            List<M> solution = new LinkedList<>();
//            for (Node n = this; n.move != null; n = n.prev) {
//                solution.add(0, n.move);
//            }
            return solution;
        }
    }
}