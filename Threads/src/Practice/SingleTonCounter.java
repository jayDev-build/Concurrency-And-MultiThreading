package Practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleTonCounter {

    static class Counter implements Runnable{

        private int counter = 0;
        private static Counter instance;

        private Counter(){}

        public static synchronized Counter getInstance(){
            if(instance == null) return instance = new Counter();
            return instance;
        }

        public synchronized int increment() {return counter++;}

        public int getCounter() {return counter;}

        @Override
        public void run() {
            for(int i = 0; i < 1000; i++) increment();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = Counter.getInstance();

        ExecutorService pool = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 10; i++){
            pool.submit(counter);
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        pool.shutdownNow();

        System.out.println(counter.getCounter());
    }
}

