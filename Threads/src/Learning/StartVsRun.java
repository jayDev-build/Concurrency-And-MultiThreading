package Learning;

public class StartVsRun {
    static class MyThread extends Thread{

        private String name;
        MyThread(String name) {
            this.name = name;
        }
        @Override
        public void run(){
            String realThreadName = Thread.currentThread().getName();
            System.out.println("Actual thread running is [" + realThreadName + "] for task: " + name);
        }
    }

    public static void main(String[] args) {
        MyThread t1 = new MyThread("Thread-1");
        t1.start();

        MyThread t2 = new MyThread("Thread-2");
        t2.run();
    }
}
