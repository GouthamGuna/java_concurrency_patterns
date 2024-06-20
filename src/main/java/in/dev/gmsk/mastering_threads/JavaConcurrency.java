package in.dev.gmsk.mastering_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaConcurrency {

    /**
     * FixedThreadPool: The Predictable Performer
     * <p>
     * Scenario: A web server experiences a constant stream of user requests for web pages and data.
     * Best Fit: FixedThreadPool. We know the workload is relatively consistent,
     * so a fixed number of threads can efficiently handle requests without creating unnecessary overhead.
     */

    private static final int nThreads = Runtime.getRuntime().availableProcessors();

    public static void webServer() {

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Runnable task = () -> System.out.println("Handling the user request " + finalI);
            executorService.execute(task);
        }

        executorService.shutdown();
    }

    /**
     * CachedThreadPool: The Dynamic Duo
     * <p>
     * Scenario: A photo editing application allows users to resize multiple images concurrently.
     * Best Fit: CachedThreadPool. The number of images can vary, so creating threads on demand optimizes resource usage.
     */

    public static void photoEditor() {
        ExecutorService executor = Executors.newCachedThreadPool();

        // Simulate resizing images (replace with actual logic)
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Runnable task = () -> System.out.println("\nResizing image " + finalI);
            executor.execute(task);
        }

        // Graceful shutdown after processing images
        executor.shutdown();
    }
}
