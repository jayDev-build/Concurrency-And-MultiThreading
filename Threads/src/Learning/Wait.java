package Learning;

public class Wait {
    static class SharedResource extends Thread{

        @Override
        public synchronized void run(){
            System.out.println(Thread.currentThread().getName() + " going to wait");
            try {
                wait();
                System.out.println(Thread.currentThread().getName() + " in action");

            }catch (Exception e){
                System.out.println("Thread Interruption: " + e.getMessage());
            }
        }

        public synchronized void NotifyExample(){
            System.out.println("Notifying a waiting thread...");
            notify();
        }

        public synchronized void NotifyAllExample(){
            System.out.println("Notifying all waiting thread...");
            notifyAll();
        }
    }

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(() -> resource.run(), "Thread-1");

        Thread t2 = new Thread(() -> {
            try {
                resource.run();
                Thread.sleep(2000); // Ensure Thread-1 goes to wait state
                resource.NotifyExample();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-2");


        t1.start();
        t2.start();
    }
}
