package com.robson.lab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private Pane background;

    @FXML
    private Button btnSent;

    @FXML
    private TextField tfAnwser;

    @FXML
    private TextField tfNick;

    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket();
        }catch (SocketException e)
        {
            throw new RuntimeException(e);
        }
    }
    private static final InetAddress adresess;
    static {
        try {
            adresess = InetAddress.getByName("localhost");
        }catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static final int SERWER_PORT = 8000;
    @FXML
    public void btnSendAction(ActionEvent event) {
        if(!(tfNick.getText().equals("")) && !(tfAnwser.getText().equals("")))
        {
            String message = tfNick.getText() + "_-_" + tfAnwser.getText();
            byte[] outData = message.getBytes();
            DatagramPacket send = new DatagramPacket(outData, message.length(), adresess, SERWER_PORT);
            try {
                socket.send(send);
            }catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }
}
