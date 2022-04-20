package tic.tac.toe.dto;

import tic.tac.toe.constant.CellState;

import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

public class Cell implements Serializable {

    @Serial
    private static final long serialVersionUID = -1033607364095561818L;

    private int verticalIndex;
    private int horizontalIndex;
    private CellState state;

    public Cell(int verticalIndex, int horizontalIndex, CellState state) {
        this.verticalIndex = verticalIndex;
        this.horizontalIndex = horizontalIndex;
        this.state = state;
    }

    public int getVerticalIndex() {
        return verticalIndex;
    }

    public void setVerticalIndex(int verticalIndex) {
        this.verticalIndex = verticalIndex;
    }

    public int getHorizontalIndex() {
        return horizontalIndex;
    }

    public void setHorizontalIndex(int horizontalIndex) {
        this.horizontalIndex = horizontalIndex;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cell.class.getSimpleName() + "[", "]")
                .add("verticalIndex=" + verticalIndex)
                .add("horizontalIndex=" + horizontalIndex)
                .add("state=" + state)
                .toString();
    }
}
