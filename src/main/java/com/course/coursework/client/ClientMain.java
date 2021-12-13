package com.course.coursework.client;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        // запустим пул нитей в которых колличество возможных нитей ограничено -
        // 10-ю.


        ExecutorService exec = Executors.newFixedThreadPool(1);

        int i=1;
        // стартуем цикл в котором с паузой в 10 милисекунд стартуем Runnable
        // клиентов,
        // которые пишут какое-то количество сообщений
        exec.execute(new RunnableClient());

        exec.shutdown();

        // закрываем фабрику

    }
}
