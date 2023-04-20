package com.robson.lab3;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.File;
import java.net.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerController implements Initializable {

    @FXML
    private Pane background;

    @FXML
    private Label labelMain;
    private BlockingQueue<Anwser> queue = new ArrayBlockingQueue<>(2);
    private ServerThread serverThread = new ServerThread(queue);

    private Thread listenThread;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.labelMain.setText("");
        this.labelMain.setFont(Font.font("Verdana", 16));
        this.labelMain.setWrapText(true);

        listenThread = new Thread(serverThread);
        listenThread.start();
        AnwserCheckThread anwserCheckThread = new AnwserCheckThread(queue, this);
        new Thread(anwserCheckThread).start();
    }

    public void writeNextLine(String text)
    {
        labelMain.setText(labelMain.getText() + text);
    }

    public void endGame()
    {
        listenThread.interrupt();
    }
}
