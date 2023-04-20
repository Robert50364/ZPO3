package com.robson.lab3;

import javafx.application.Platform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class AnwserCheckThread implements Runnable
{
    private BlockingQueue<Anwser> queue;
    private Anwser anwser;

    private ServerController sc;
    private File file;
    private Scanner reader;
    private List<String> questions = new ArrayList<>();
    private int actualQuestion = 0;

    public AnwserCheckThread(BlockingQueue<Anwser> queue, ServerController serverController) {
        this.sc = serverController;
        this.queue = queue;
        init();
    }

    private void init()
    {
        try {
            this.file = new File("C:\\Studia\\Java\\Zaawansowane\\Lab3\\src\\main\\resources\\com\\robson\\lab3\\Questions.txt");
            this.reader = new Scanner(this.file);
            readQuestions();
            writeBack(questions.get(actualQuestion));
        }catch (Exception e)
        {
            System.out.println("Brak pliku!");
        }
    }

    public void readQuestions()
    {
        while(reader.hasNextLine())
        {
            questions.add(reader.nextLine());
        }
        if(questions.size() %2 != 0)
        {
            questions.remove(questions.lastIndexOf(questions));
        }
    }

    private void writeBack(String text)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sc.writeNextLine(text);
            }
        });
    }

    @Override
    public void run() {
        try{
            while (!(anwser = queue.take()).getText().equals("") && !Thread.currentThread().isInterrupted())
            {
                String data[];
                data = anwser.getText().split("_-_", 2);

                if(data[1].equals(questions.get(actualQuestion+1)))
                {
                    writeBack("\n" + data[0] + " odpowiedział poprawnie: "+ data[1]+"\n");
                    actualQuestion+=2;

                    if(actualQuestion < questions.size())
                    {
                        writeBack(questions.get(actualQuestion));
                    }else {
                        writeBack("Koniec Gry");
                        sc.endGame();
                        Thread.currentThread().interrupt();
                    }
                }else {
                    writeBack("\n" + data[0] + " odpowiedział żle.");
                }
            }
        }catch (InterruptedException e)
        {}
    }
}
