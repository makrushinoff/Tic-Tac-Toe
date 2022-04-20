package tic.tac.toe.dto;

import tic.tac.toe.constant.CellState;

import java.io.Serial;
import java.io.Serializable;

public class StartFieldDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -29113215822234430L;

    private Cell[][] cells;
    private CellState state;

    public StartFieldDto(Cell[][] cells, CellState state) {
        this.cells = cells;
        this.state = state;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }
}
