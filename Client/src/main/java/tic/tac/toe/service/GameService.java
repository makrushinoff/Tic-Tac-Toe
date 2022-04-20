package tic.tac.toe.service;

import tic.tac.toe.constant.CellState;
import tic.tac.toe.dto.Cell;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class GameService {
    private static final List<CellState> states = List.of(CellState.CIRCLE, CellState.CROSS);
    private static final Random random = new Random();

    public CellState getRandomState() {
        return states.get(random.nextInt() % 2);
    }

    public Cell[][] makeRandomMove(Cell[][] cells, CellState state) {
        boolean moved = false;
        while (!moved) {
            int horizontal = random.nextInt(2);
            int vertical = random.nextInt(2);
            if(cells[vertical][horizontal].getState().equals(CellState.EMPTY)) {
                cells[vertical][horizontal].setState(state);
                moved = true;
            }
        }
        return cells;
    }

    public Cell[][] createField() {
        Cell[][] cells = new Cell[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j] = new Cell(i, j, CellState.EMPTY);
            }
        }
        return cells;
    }

    public CellState checkGameFin(Cell[][] cells) {
        final CellState columns = checkColumns(cells);

        final CellState rows = checkRows(cells);
        final CellState diagonals = checkDiagonals(cells);
        return Stream.of(columns, rows, diagonals)
                .filter(state -> !CellState.EMPTY.equals(state))
                .findFirst()
                .orElse(CellState.EMPTY);
    }

    private CellState checkRows(Cell[][] matrix) {
        for(int i = 0; i < 3; i++) {
            if(matrix[i][0].getState().equals(CellState.EMPTY)){
                continue;
            }
            if(matrix[i][0].getState().equals(matrix[i][1].getState()) && matrix[i][1].getState().equals(matrix[i][2].getState())) {
                return matrix[i][0].getState();
            }
        }
        return CellState.EMPTY;
    }

    private CellState checkColumns(Cell[][] matrix) {
        for(int i = 0; i < 3; i++) {
            if(matrix[0][i].getState().equals(CellState.EMPTY)){
                continue;
            }
            if(matrix[0][i].getState().equals(matrix[1][i].getState()) && matrix[1][i].getState().equals(matrix[2][i].getState())) {
                return matrix[0][i].getState();
            }
        }
        return CellState.EMPTY;
    }

    private CellState checkDiagonals(Cell[][] matrix) {
        if(matrix[0][0].getState().equals(matrix[1][1].getState()) && matrix[1][1].getState().equals(matrix[2][2].getState())) {
            return matrix[1][1].getState();
        }
        if(matrix[0][2].getState().equals(matrix[1][1].getState()) && matrix[1][1].getState().equals(matrix[2][0].getState())) {
            return matrix[1][1].getState();
        }
        return CellState.EMPTY;
    }
}
