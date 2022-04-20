package tic.tac.toe.constant;

import java.util.Map;

public enum CellState {
    CROSS("X"), CIRCLE("O"), EMPTY(" ");

    private final String sign;

    CellState(String sign) {
        this.sign = sign;
    }

    public static CellState getOtherState(CellState state) {
        return Map.of(CROSS, CIRCLE, CIRCLE, CROSS).get(state);
    }

    public String getSign() {
        return sign;
    }
}
