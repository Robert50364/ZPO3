package com.robson.lab3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

public class ServerThread implements Runnable
{
    //Obsługa przesyłu danych
    private static byte[] incomingData = new byte[512];
    private static final int PORT = 8000;
    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket(PORT);
        }catch (SocketException e)
        {
            throw new RuntimeException(e);
        }
    }

    private BlockingQueue<Anwser> queue;
    private Anwser anwser = new Anwser();
    ServerThread(BlockingQueue<Anwser> queue)
    {
        this.queue = queue;
    }

    public void run()
    {
        Thread current = Thread.currentThread();
        System.out.println("Start nasłuchu.");
        while (!current.isInterrupted())
        {
            System.out.println("Nasłuch trwa...");
            DatagramPacket packet = new DatagramPacket(incomingData, incomingData.length);
            try {
                socket.receive(packet);
            }catch (IOException e){
                throw new RuntimeException(e);
            }

            String anwserString = new String(packet.getData(),0, packet.getLength());
            anwser.setText(anwserString);
            queue.add(anwser);
        }
    }
}
