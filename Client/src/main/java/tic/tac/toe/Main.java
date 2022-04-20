package tic.tac.toe;

import tic.tac.toe.constant.CellState;
import tic.tac.toe.constant.ResponseAnswer;
import tic.tac.toe.dto.Cell;
import tic.tac.toe.dto.FieldDto;
import tic.tac.toe.dto.StartFieldDto;
import tic.tac.toe.service.GameService;
import tic.tac.toe.view.ConsoleView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static ConsoleView consoleView = new ConsoleView();
    private static GameService gameService = new GameService();

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());


            StartFieldDto startFieldDto = (StartFieldDto) inputStream.readObject();
            System.out.println("Your sign is " + startFieldDto.getState());
            System.out.println("Who will start[1.Cross / 2.Circle](input number): ");
            final Scanner scanner = new Scanner(System.in);
            int whoStartsChoice = scanner.nextInt();
            CellState whoStarts = whoStartsChoice == 1 ? CellState.CROSS : CellState.CIRCLE;
            outputStream.writeObject(whoStarts);
            final CellState myState = startFieldDto.getState();
            Cell[][] field = startFieldDto.getCells();
            boolean myTurn = myState.equals(whoStarts);
            boolean endGame = false;
            if(myTurn) {
                consoleView.printField(field);
            }
            while (!endGame) {
                if(myTurn) {
                    outputStream.reset();
                    System.out.print("Input vertical index: ");
                    int verticalIndex = scanner.nextInt();
                    System.out.print("Input horizontal index: ");
                    int horizontalIndex = scanner.nextInt();
                    field[verticalIndex][horizontalIndex].setState(myState);
                    final CellState cellState = gameService.checkGameFin(field);
                    final ResponseAnswer gameState;
                    if(cellState.equals(myState)) {
                        consoleView.printField(field);
                        System.out.println("You win! Congrats!");
                        endGame = true;
                        gameState = ResponseAnswer.valueOf(myState.name() + "_WIN");
                    } else {
                        gameState = ResponseAnswer.GAME_CONTINUE;
                    }
                    outputStream.writeObject(new FieldDto(field, gameState));
                }
                else {
                    FieldDto dto = (FieldDto) inputStream.readObject();
                    field = dto.getCells();
                    consoleView.printField(field);
                    endGame = fin(dto.getGameState(), myState);
                    if(endGame) {
                        System.out.println("You lose!");
                    }
                }
                myTurn = !myTurn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean fin(ResponseAnswer answer, CellState myState) {
        if(answer.equals(ResponseAnswer.CIRCLE_WIN) && myState.equals(CellState.CROSS)) {
            return true;
        }
        return answer.equals(ResponseAnswer.CROSS_WIN) && myState.equals(CellState.CIRCLE);
    }
}
