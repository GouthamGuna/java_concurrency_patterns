package in.dev.gmsk.features.threads;

/**
 *  Threads
 *  </p>
 *  Java Supports for 0S - Threads.
 * */
public class UsingThreads {

    public static void main(String[] args) throws InterruptedException {

        var created = new Thread();
        created.start();
        // created.run(); // runs on main thread.

        // Assigning the task for running on a thread - we pass a Runnable instance
        var threadWithTask = new Thread(() -> System.out.printf("Inside Thread %s%n", Thread.currentThread().getName()));
        threadWithTask.start();

        // Interrupting the thread
        Runnable interruptByTask = () -> {
          while (!Thread.currentThread().isInterrupted()) {
              System.out.printf("Im not interrupted %s%n", Thread.currentThread().getName());
          }
            System.err.printf("Im Interrupted %s%n", Thread.currentThread().getName());
        };

        var interruptAble = new Thread(interruptByTask);
        interruptAble.start();
        Thread.sleep(3000);
        interruptAble.interrupt();
    }
}
