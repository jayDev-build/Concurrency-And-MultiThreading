//This Runnable Implementation allows to extend also
//But this increases the code overhead of creating runnable to pass in threads

class ThreadClassRunnable implements Runnable{
    @Override
    public void run(){
        for(int i = 0; i < 5; i++){
            System.out.println("Running thread id: " + Thread.currentThread().getId() + " at iteration: " + i);
            try {
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}

public class ImplementRunnable {
    public static void main(String[] args) {
        ThreadClassRunnable runnable = new ThreadClassRunnable();

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        t2.start();
    }
}
