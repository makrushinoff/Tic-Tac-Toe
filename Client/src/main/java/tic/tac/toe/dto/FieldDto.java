package tic.tac.toe.dto;

import tic.tac.toe.constant.ResponseAnswer;

import java.io.Serial;
import java.io.Serializable;

public class FieldDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 378871622243862771L;

    private Cell[][] cells;
    private ResponseAnswer gameState;

    public FieldDto(Cell[][] cells, ResponseAnswer gameState) {
        this.cells = cells;
        this.gameState = gameState;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public ResponseAnswer getGameState() {
        return gameState;
    }

    public void setGameState(ResponseAnswer gameState) {
        this.gameState = gameState;
    }
}
