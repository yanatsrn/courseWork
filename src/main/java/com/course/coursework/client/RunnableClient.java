package com.course.coursework.client;

import com.course.coursework.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RunnableClient implements Runnable{

    static Socket socket;
    public static String command = " ";

    public RunnableClient() {
        try {

            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket("localhost", 3345);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

//        HelloApplication.main();
        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            oos.writeUTF("start");
            while(!socket.isClosed()) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
