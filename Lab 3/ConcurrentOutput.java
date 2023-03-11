import java.io.PrintStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ConcurrentOutput extends Application {

    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // create Pane
        Pane pane = new HBox(10);
        Scene scene = new Scene(pane, 400, 160);

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(5);
        outputArea.setPrefColumnCount(40);
        outputArea.setWrapText(true);
        pane.getChildren().add(outputArea);

        // redirect console output to TextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(outputArea));
        System.setOut(printStream);
        System.setErr(printStream);

        Runnable printA = new PrintChar('a', 100);
        Runnable printB = new PrintChar('b', 100);
        Runnable print100 = new PrintNum(100);

        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(print100);

        thread3.start(); // Start the thread that prints numbers first
        try {
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Start the thread that prints b's
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Start the thread that prints a's
        thread1.start();

        // set properties of stackpane
        primaryStage.setScene(scene);
        primaryStage.setTitle("Concurrent Output");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // custom OutputStream that writes to a TextArea
    private class TextAreaOutputStream extends java.io.OutputStream {
        private TextArea textArea;

        public TextAreaOutputStream(TextArea textArea) {
            this.textArea = textArea;
        }

        public void write(int b) {
            textArea.appendText(String.valueOf((char) b));
        }
    }
}

class PrintChar implements Runnable {
    private char charToPrint;
    private int times;

    public PrintChar(char c, int t) {
        charToPrint = c;
        times = t;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < times; i++) {
            System.out.print(charToPrint);
        }
    }
}

class PrintNum implements Runnable {
    private int lastNum;

    public PrintNum(int n) {
        lastNum = n;
    }

    @Override
    public synchronized void run() {
        for (int i = 1; i <= lastNum; i++) {
            System.out.print(i + " ");
        }
    }
}
