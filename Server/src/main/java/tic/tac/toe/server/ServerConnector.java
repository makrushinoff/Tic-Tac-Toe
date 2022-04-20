package tic.tac.toe.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tic.tac.toe.constant.NetworkConstant;
import tic.tac.toe.controller.ClientHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConnector.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    public void connect() {
        try (ServerSocket serverSocket = new ServerSocket(NetworkConstant.PORT)) {
            LOGGER.info("Server has started on port {}", NetworkConstant.PORT);
            Socket socket = null;
            while(true) {
                InetAddress inetAddress = null;
                try {
                    socket = serverSocket.accept();
                    inetAddress = socket.getInetAddress();
                    LOGGER.info("Received connection from: {}, port:{}", inetAddress, socket.getPort());
                    ClientHandler clientHandler = new ClientHandler(socket);
                    executorService.execute(clientHandler);
                    LOGGER.info("ExecutorService started new task");
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    LOGGER.info("Client from address {} disconnected", inetAddress);
                    socket.close();
                }
            }
        } catch (Exception e) {
            Arrays.stream(e.getStackTrace())
                    .forEach(stackTraceElement -> LOGGER.error(String.valueOf(stackTraceElement)));
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                LOGGER.error("Executor tic.tac.toe.service can not close!");
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}
