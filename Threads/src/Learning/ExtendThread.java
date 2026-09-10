package Learning;

//Issue with extend Threads is that we can not extend to any other class
//Since java do not support Multiple Inheritance
public class ExtendThread {
    static class ThreadClass extends Thread{
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

    public static void main(String[] args) {
        ThreadClass t1 = new ThreadClass();
        ThreadClass t2 = new ThreadClass();

        t1.start();
        t2.start();
    }
}