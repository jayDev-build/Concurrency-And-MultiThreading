import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.*;

class MyCallable implements Callable<String>{

    private final String name;

    MyCallable(String name) {
        this.name = name;
    }

    @Override
    public String call(){
        StringBuilder sb = new StringBuilder();
        try {
            for(int i = 0; i < 5; i++){
                sb.append("Callable ").append(name).append(" is running ").append(i).append(" \n");
                Thread.sleep(500);
            }
        }catch (Exception e){
            System.out.println("Got error: " + e.getMessage());
        }

        return sb.toString();
    }
}

public class CallableExample {

    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(2);

        /*
            since in MyCallable i only have call function
            A thread will keep a separate Stack and call
            function will be pushed to each stack so no
            Dirty read will happen

            So therefore i can also pass the same MyCallable object but there will be same name
        */
        MyCallable callable1 = new MyCallable("Task-1");
        MyCallable callable2 = new MyCallable("Task-2");


        try{
            Future<String> future1 = executors.submit(callable1);
            Future<String> future2 = executors.submit(callable2);

            System.out.println("Result from first task:");
            System.out.println(future1.get()); // Main Thread Blocks until the task completes

            System.out.println("Result from second task:");
            System.out.println(future2.get()); // Main Thread Blocks until the task completes

        }catch (Exception e){
            System.out.println("Task Execution Get Interrupted: " + e.getMessage());
        }finally {
            executors.shutdown();
        }

    }
}
