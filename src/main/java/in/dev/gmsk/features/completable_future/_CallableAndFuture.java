package in.dev.gmsk.features.completable_future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static java.lang.System.err;
import static java.lang.System.out;

public class _CallableAndFuture {

    public static Callable<String> exampleForCallable() {
        // Callable interface has a single method call()
        Callable<String> callable = () -> {
            getThreadDetails(() -> "Invoking Method ::: exampleForCallable()");
            Thread.sleep(5000);
            return "Hello World! From Callable interface invoking call method...";
        };

        return callable;
    }

    private static void getThreadDetails(Supplier<String> ss) {
        System.out.printf("Thread Name := %s := %s\n", Thread.currentThread(), ss.get());
    }

    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            /*
            Future<String> holdSingleFuture = executorService.submit(exampleForCallable());

            // Future.get() blocks until the result is available
            String result = holdSingleFuture.get();
            out.println(STR."holdSingleFuture result ::: \{result}");
             */

            Callable<String> submitTask_1 = exampleForCallable();

            List<Callable<String>> listOfFutureTask = Arrays.asList(submitTask_1);

            List<Future<String>> futuresList = executorService.invokeAll(listOfFutureTask);

            for (Future<String> future : futuresList) {
                out.println(future.get());
            }

            executorService.shutdown();
        } catch (Throwable e) {
            err.println(e.getMessage());
        }
    }
}
