package tic.tac.toe.constant;

import java.util.Map;

public enum ResponseAnswer {

    CONNECTION_ESTABLISHED,
    GO_PLAY,

    CROSS_WIN,
    CIRCLE_WIN,
    GAME_CONTINUE;

    public static ResponseAnswer whoWins(CellState state) {
        return Map.of(CellState.CROSS, CROSS_WIN,
                      CellState.CIRCLE, CIRCLE_WIN,
                      CellState.EMPTY, GAME_CONTINUE)
                      .get(state);
    }
}
