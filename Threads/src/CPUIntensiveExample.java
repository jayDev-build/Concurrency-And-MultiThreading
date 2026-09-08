import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPUIntensiveExample {
    public static void main(String[] args) {
        int numCores = Runtime.getRuntime().availableProcessors();

        ExecutorService executors = Executors.newFixedThreadPool(numCores);
        for(int i = 0; i < 10; i++){
            executors.execute(() -> {
                int res = performComputation();
                System.out.println(Thread.currentThread().getName() + " -> " + res);
            });
        }

        executors.shutdown();
    }
    private static int performComputation() {
        int sum = 0;
        for (int i = 0; i < 1_000_000; i++) {
            sum += Math.sqrt(i); // Simulating heavy computation
        }
        return sum;
    }
}
