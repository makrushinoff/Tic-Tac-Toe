package tic.tac.toe;

import tic.tac.toe.server.ServerConnector;

public class Main {

    public static void main(String[] args) {
        new ServerConnector().connect();
    }
}
