import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class IOBoundExample {
    public static void main(String[] args) {
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            cachedPool.execute(() -> {
                simulateWebRequest();
                System.out.println(
                        Thread.currentThread().getName() + " completed I/O task.");
            });
        }
        cachedPool.shutdown();
    }

    private static void simulateWebRequest() {
        try {
            System.out.println(
                    Thread.currentThread().getName() + " is waiting for response...");
            sleep(100); // Simulating network delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
