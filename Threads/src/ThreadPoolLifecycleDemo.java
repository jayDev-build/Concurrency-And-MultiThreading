import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolLifecycleDemo {

    static class Task implements Runnable{

        private final int taskId;

        Task(int id){
            taskId = id;
        }

        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName() + " - STARTING Task " + taskId);
            try {
                Thread.sleep(2000);
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName() + " - WAITING on Task " + taskId);
                    this.wait(1000);
                }
                System.out.println(Thread.currentThread().getName() + " - Task " + taskId + " COMPLETED");

            }catch (Exception e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        System.out.println("Thread Pool LifeCycle");

        for (int i = 1; i <= 5; i++) {
            pool.execute(new Task(i)); // Threads pick tasks and move to RUNNABLE
        }

        pool.shutdown();

        try {
            if(!pool.awaitTermination(10, TimeUnit.SECONDS)){
                pool.shutdownNow();
                System.out.println("Forcing Shutdown! 🚧");
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }

        System.out.println("All Threads Terminated ✅");

    }
}
