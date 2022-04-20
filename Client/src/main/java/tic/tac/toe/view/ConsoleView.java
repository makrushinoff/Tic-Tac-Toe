package tic.tac.toe.view;

import tic.tac.toe.constant.CellState;
import tic.tac.toe.constant.ResponseAnswer;
import tic.tac.toe.dto.Cell;

public class ConsoleView {

    public void printGreetings() {
        System.out.println("Hello! It`s Tic-tac-toe Online");
    }

    public void printStartMenu() {
        System.out.println("Choose one of the next points:");
        System.out.println("1. Create new room");
        System.out.println("2. Connect to existing room");
        System.out.print("\nEnter the number[1,2]: ");
    }

    public void printConnectionToServerMessage(boolean successful) {
        if(successful) {
            System.out.println("Can not connect to the server");
        } else {
            System.out.println("Connection established");
        }
    }

    public void printCreateNewRoomMessage(boolean successful) {
        if(successful) {
            System.out.println("New room created successfully");
        } else {
            System.out.println("This room code already exists.");
        }
    }

    public void printField(Cell[][] field) {
        System.out.println();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(field[i][j].getState().getSign());
                if(j != 2) {
                    System.out.print("|");
                }
            }
            if(i != 2) {
                System.out.println("\n_____");
            }
        }
        System.out.println();
    }

    public void printChooseCell() {
        System.out.println("\nChoose cell[2 numbers, divided by space]:");
    }

    public void printEndGameMessage(boolean win) {
        if(win) {
            System.out.println("You win! Congratulations!");
        } else {
            System.out.println("You lose!");
        }
    }
}
