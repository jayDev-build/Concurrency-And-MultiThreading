public class ExceptionOccuringInThread {
    static class MyThread extends Thread{
        @Override
        public void run(){
            try {
                throw new RuntimeException("Error In Thread");
            }catch (Exception e){
                System.out.println("Caught exception in thread: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
//        t1.start();

        t1.run();
        System.out.println("Main thread still runs");
    }
}
