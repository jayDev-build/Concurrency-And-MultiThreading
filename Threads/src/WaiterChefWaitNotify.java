import static java.lang.Thread.sleep;

public class WaiterChefWaitNotify {

    static class Chef extends Thread{

        private Object lock;

        public Chef(Object lock){
            this.lock = lock;
        }

        @Override
        public void run(){
            try{
                synchronized (lock){
                    System.out.println("Chef Is Preparing The Food");
                    lock.notify();
                    System.out.println("Chef HandOvered the Food to Waiter");
                }
            }catch (Exception e){
                System.out.println("Thread Interrupted: " + e.getMessage());
            }
        }
    }

    static class Waiter extends Thread{

        private Object lock;

        public Waiter(Object lock){
            this.lock = lock;
        }

        @Override
        public void run(){
            try{
                synchronized (lock){
                    System.out.println("Waiter is waiting for chef to prepare food");
                    lock.wait();
                    System.out.println("Waiter Received the food");
                }
            }catch (Exception e){
                System.out.println("Thread Interrupted: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Object lock = new Object();
        Waiter waiter = new Waiter(lock);
        Chef chef = new Chef(lock);


        try {
            waiter.start();
            sleep(1000); // so that waiter waits first than chef starts preparing
            chef.start();
        } catch (Exception e) {
            System.out.println("Thread Interrupted: " + e.getMessage());
        }

    }
}
