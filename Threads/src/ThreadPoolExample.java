import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {

    static class WorkerThread implements Runnable{
        private int taskId;

        public WorkerThread(int id){
            this.taskId = id;
        }

        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName() + " is processing task: " + taskId);
            try {
                Thread.sleep(2000); // Simulate task execution time
            } catch (InterruptedException e) {
                System.out.println("Task interrupted: " + e.getMessage());
            }
            System.out.println(Thread.currentThread().getName() + " finished task: " + taskId);
        }
    }

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 10; i++){
            threadPool.submit(new WorkerThread(i));
        }

        threadPool.shutdown();
    }
}
