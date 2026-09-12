package Learning;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ShutDownVsShutDownNow {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor1 = Executors.newFixedThreadPool(2);

        System.out.println("Executor1 : ShutDown");
        for(int i = 1; i <= 5; i++){
            int finalI = i;
            executor1.submit(() -> {
                try{
                    System.out.println("Started Task: " + finalI);
                    sleep(2000);
                    System.out.println("Completed Task: " + finalI);
                }catch (Exception e){
                    System.out.println("Interrupted: " + e.getMessage());
                }
            });
        }

        sleep(1000);

        System.out.println("Calling shutdown()...");
        executor1.shutdown();

        System.out.println("Is shutdown: " + executor1.isShutdown());
        System.out.println("Is terminated: " + executor1.isTerminated());
        System.out.println("Can submit new tasks? " + !executor1.isShutdown());

        // Wait for tasks to complete
        boolean tasksCompleted = executor1.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("All tasks completed: " + tasksCompleted);
        System.out.println("Is terminated now: " + executor1.isTerminated());


        System.out.println("<=====================================>");

        System.out.println("Executor2 : ShutDownNow");

        ExecutorService executor2 = Executors.newFixedThreadPool(2);
        for(int i = 1; i <= 5; i++){
            int finalI = i;
            executor2.submit(() -> {
                try {
                    System.out.println("Started Task: " + finalI);
                    Thread.sleep(2000);
                    System.out.println("Completed Task: " + finalI);
                }catch (Exception e){
                    System.out.println(finalI + " Interrupted: " + e.getMessage());
                }
            });
        }

        Thread.sleep(1000);

        System.out.println("Calling shutDownNow()...");
        List<Runnable> pendingTasks = executor2.shutdownNow();

        System.out.println("Is shutdown: " + executor2.isShutdown());
        System.out.println("Number of pending tasks that never started: " + pendingTasks.size());

        // Wait for executing tasks to respond to interruption
        executor2.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Is terminated now: " + executor2.isTerminated());
    }
}
