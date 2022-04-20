package tic.tac.toe.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringJoiner;

import tic.tac.toe.constant.CellState;
import tic.tac.toe.constant.ResponseAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tic.tac.toe.dto.FieldDto;
import tic.tac.toe.dto.StartFieldDto;
import tic.tac.toe.dto.Cell;
import tic.tac.toe.service.GameService;

public class ClientHandler extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private final Socket socket;
    private final GameService gameService = new GameService();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            LOGGER.info("Start to handle");
            CellState serverPlayState = gameService.getRandomState();
            LOGGER.info("Server state: {}", serverPlayState);

            Cell[][] field = gameService.createField();
            LOGGER.info("Create Field");
            outputStream.writeObject(new StartFieldDto(field, CellState.getOtherState(serverPlayState)));
            LOGGER.info("");
            CellState firstPlay = (CellState) inputStream.readObject();
            boolean endGame = false;
            boolean myTurn = firstPlay.equals(serverPlayState);
            while(!endGame) {
                if(myTurn) {
                    outputStream.reset();
                    field = gameService.makeRandomMove(field, serverPlayState);
                    final CellState cellState = gameService.checkGameFin(field);
                    FieldDto dto = new FieldDto(field, ResponseAnswer.whoWins(cellState));
                    outputStream.writeObject(dto);
                    if(!dto.getGameState().equals(ResponseAnswer.GAME_CONTINUE)) {
                        endGame = true;
                        LOGGER.info("I win client {}:{}", socket.getInetAddress(), socket.getPort());
                    }
                }
                else {
                    FieldDto dto = (FieldDto) inputStream.readObject();
                    if(!dto.getGameState().equals(ResponseAnswer.GAME_CONTINUE)) {
                        LOGGER.info("I lost to client: {}:{}", socket.getInetAddress(), socket.getPort());
                        endGame = true;
                    } else {
                        field = dto.getCells();
                    }
                }
                myTurn = !myTurn;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClientHandler.class.getSimpleName() + "[", "]")
                .add("socket=" + socket)
                .toString();
    }
}
