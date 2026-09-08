Notes: 

<H3>Extends Threads:</H3>
Issue with extend Threads is that we can not extend to any other class Since java do not support Multiple Inheritance

<H3>Implements Runnable:</H3>
But this increases the code overhead of creating runnable to pass in threads

| Feature | The Stack (Thread-Local) | The Heap (Shared) |
| :--- | :--- | :--- |
| **Scope** | Private to one specific thread. | Shared across all threads. |
| **Contents** | Local variables, object references, method execution order. | Actual objects, instance variables, arrays. |
| **Thread Safety** | 100% inherently thread-safe. | Not thread-safe. Requires synchronization (locks). |
| **Lifespan** | Data is destroyed the moment the method finishes. | Data stays until the Garbage Collector removes it. |

Here is the improved and nicely formatted version of those notes, ready to be appended directly to your `README.md` file. I continued the numbering from your previous notes to keep it cohesive!

---

### 5. `start()` vs `run()`: Threads Do Not Own Objects

In Java, threads do not "own" objects. **All objects live on the shared Heap.**

```java
MyThread t1 = new MyThread("Task-1");
t1.start(); // Spawns a new background worker thread

MyThread t2 = new MyThread("Task-2");
t2.run();   // Executes exactly like a normal method call on the main thread

```

Here is exactly what is happening between the `main` thread and the `t2` object:

* **Where `t2` lives (The Heap):** When you type `new MyThread("Task-2")`, the JVM builds the `t2` object on the shared Heap. It just sits there, waiting to be used.
* **What the `main` thread does (The Stack):** The `main` thread holds a reference (a remote control) to `t2` on its private Stack. When you call `t2.run()`, the `main` thread simply uses that remote control to execute the instructions inside the `run()` method itself.

> **The Kitchen Analogy**
> Think of the **Heap** as a shared kitchen, and the thread objects (`t1` and `t2`) as blenders sitting on the counter.
> * **`t1.start()`:** You (the `main` thread) hire a new worker (`Thread-0`), point to blender `t1`, and say, *"Go turn that on."* The worker goes and runs it in the background while you keep moving and doing other tasks.
> * **`t2.run()`:** You (the `main` thread) walk up to blender `t2` and turn it on yourself. You have to stand there and wait for it to finish blending before you can do anything else.
>
>
> The blender (`t2`) was never "transferred" to you—it always stayed on the shared kitchen counter. You just decided to operate it yourself instead of hiring a background worker!
---

## Java Concurrency: Locks, `wait()`, and `notify()`

### 1. The `IllegalMonitorStateException`

To call `wait()`, `notify()`, or `notifyAll()` on an object, a thread **must currently own that object's lock**. If it doesn't, Java will throw an `IllegalMonitorStateException`. You acquire this lock by executing the code inside a `synchronized` method or block.

### 2. Locks Belong to Objects, Not Methods

In Java, every single object has one built-in lock (a "monitor"). Adding the `synchronized` keyword to a method is shorthand for locking the **entire object**.

* **The Bathroom Analogy:** Think of the shared object as a bathroom with one lock on the door. The synchronized methods are just activities inside (showering, brushing teeth). If Thread A is inside using a synchronized method, the door is locked. Thread B cannot enter *any* synchronized method on that exact object until Thread A comes out and unlocks the door.

### 3. How `wait()` Works

When a thread is inside a synchronized method and calls `wait()`, it does two things simultaneously:

1. It goes to sleep.
2. **It drops the lock (the key).**
   Because the lock is dropped, other threads are now free to acquire the lock and enter the object's synchronized methods.

### 4. How `notify()` Actually Works

Calling `notify()` does **not** instantly restart the waiting thread, nor does it force it to jump to the end of the method.

1. **Wake Up:** `notify()` tells the waiting thread to wake up.
2. **Wait for the Key:** The notifying thread *still holds the lock* until it finishes its own synchronized method. The newly woken thread enters a `BLOCKED` state by the door, waiting for the lock to become available.
3. **Resume:** Once the notifying thread finishes and drops the lock, the woken thread grabs the key and resumes execution on the exact line of code directly below `wait()`.

---

### Code Example

Here is a complete, thread-safe example demonstrating `wait()` and `notify()` interacting on a single shared object.

```java
class SharedResource {

    // Thread-1 locks the object, then drops the lock when wait() is called
    public synchronized void waitForAction() {
        System.out.println(Thread.currentThread().getName() + " going to wait...");
        try {
            wait(); // Drops lock, goes to sleep. Resumes on the next line!
            System.out.println(Thread.currentThread().getName() + " back in action!");
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted: " + e.getMessage());
        }
    }

    // Thread-2 acquires the lock, notifies, and releases the lock when method ends
    public synchronized void notifyAction() {
        System.out.println(Thread.currentThread().getName() + " notifying a waiting thread...");
        notify(); 
        // Thread-1 is awake now, but MUST wait for Thread-2 to finish this method 
        // before Thread-1 can actually start running again.
    }
}

public class WaitNotifyExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Pass the shared resource to Thread-1
        Thread t1 = new Thread(() -> resource.waitForAction(), "Thread-1");
        
        // Pass the same shared resource to Thread-2
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000); // Give Thread-1 time to start waiting
                resource.notifyAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}

```