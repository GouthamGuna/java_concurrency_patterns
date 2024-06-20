package in.dev.gmsk.features.java_memory_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public class MainMethod {

    static Collection<Integer> syncCollection =
            Collections.synchronizedCollection(new ArrayList<>());

    static Runnable listOperations = () -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));

    public static void main(String[] args) {

        OutOfOrderExecution.exampleOne();
        OutOfOrderExecution.exampleTwo();

        Thread thread = new Thread(new FieldVisibility());
        thread.start();

        FieldVisibility fieldVisibility = new FieldVisibility();
        fieldVisibility.writeThread();
        fieldVisibility.readerThread("obj ref invoked...");

        Thread threadOne = new Thread(new SynchronizedFieldVisibility());
        threadOne.start();

        SynchronizedFieldVisibility svf = new SynchronizedFieldVisibility();
        svf.writerThread();
        svf.readerThread(System.out::println, "obj ref invoked...");

        testSyncCollection();
    }

    private static void testSyncCollection() {
        try {
            Thread thread = new Thread(listOperations);
            thread.start();
            thread.join();

            System.out.println("syncCollection.size() : " + syncCollection.size());
            syncCollection.forEach((e) -> System.out.println("e = " + e));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SynchronizedFieldVisibility implements Runnable {

    int a = 0, b = 0, c = 0;
    volatile int x = 0;

    public void writerThread() {
        a = 1;
        b = 1;
        c = 1;

        synchronized (this) {
            x = 1;
        }
    }

    public void readerThread(Consumer<String> consumer, String msg) {

        synchronized (this) {
            int r = x;
            consumer.accept(msg);
            System.out.println("From : SFV : = " + r);
        }

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
    }

    @Override
    public void run() {
        readerThread(System.out::println, "run method invoked...");
    }
}