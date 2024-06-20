package in.dev.gmsk.mastering_threads;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * It`s integrated into application`s request processing pipeline.
 */

public class ConcurrentRequestFilter implements Filter {

    private ExecutorService executorService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        // Initialize the ExecutorService with a fixed thread pool size
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Warp the request processing in a runnable task
        Runnable task = () -> {
            try {

                // Process the request...

                // Proceed with the rest of the filter chain
                chain.doFilter(request, response);

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        };

        // submit the task for execution
        executorService.submit(task);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        // Shutdown the ExecutorService
        executorService.shutdown();
    }
}
