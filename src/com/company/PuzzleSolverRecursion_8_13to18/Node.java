package com.company.PuzzleSolverRecursion_8_13to18;

import java.util.LinkedList;
import java.util.List;

/**
 * Листинг 8.14 Связующий узел в фреймворке решения загадок
 *
 * @param <P>
 * @param <M>
 */
public class Node<P, M> {
    final P pos;
    final M move;
    final Node<P, M> prev;

    public Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    public List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (Node<P, M> n = this; n.move != null; n = n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }
}
