package com.company.PuzzleSolverRecursion_8_13to18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Листинг 8.15 Последовательная версия решателя головоломок
 *
 * @param <P>
 * @param <M>
 */
public class SequentialPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final Set<P> seen = new HashSet<>();

    public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solve() {
        P pos = puzzle.initialPosition();
        return search(new Node<P, M>(pos, null, null));
    }

    private List<M> search(Node<P, M> node) {
        P nodePos = node.pos;
        if (!seen.contains(nodePos)) {
            seen.add(nodePos);
            if (puzzle.isGoal(nodePos)) {
                return node.asMoveList();
            }

            for (M move : puzzle.legalMoves(nodePos)) {
                P pos = puzzle.move(nodePos, move);
                Node<P, M> child = new Node<>(pos, move, node);
                List<M> result = search(child);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }
}
