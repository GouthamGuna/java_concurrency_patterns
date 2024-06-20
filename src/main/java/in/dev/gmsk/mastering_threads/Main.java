package in.dev.gmsk.mastering_threads;

import static in.dev.gmsk.mastering_threads.JavaConcurrency.photoEditor;
import static in.dev.gmsk.mastering_threads.JavaConcurrency.webServer;

public class Main {
    public static void main(String[] args) {

        webServer();
        photoEditor();
    }
}
