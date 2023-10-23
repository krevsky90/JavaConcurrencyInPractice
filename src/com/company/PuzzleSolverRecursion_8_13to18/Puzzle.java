package com.company.PuzzleSolverRecursion_8_13to18;

import java.util.Set;

/**
 * Листинг 8.13 Абстракция для головоломки, подобной “головоломка на перемещение элементов”
 *
 * @param <P>
 * @param <M>
 */
public interface Puzzle<P, M> {
    P initialPosition();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P move(P position, M move);
}
