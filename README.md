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

<hr>

<b>In Java, threads do not own objects.</b><br>
MyThread t1 = new MyThread("Task-1");<br>
t1.start(); // Spawns a new background thread<br><br>

MyThread t2 = new MyThread("Task-2");<br>
t2.run();   // Executes like a normal method call<br><br>

Remember our golden rule from earlier: All objects live on the shared Heap.<br><br>

Here is exactly what is happening between the main thread and the t2 object:<br><br>

Where t2 lives: When you typed 
<br><i>MyThread t2 = new MyThread("Task-2");</br></i>, 
the JVM built the t2 object on the shared Heap. It just sits there, waiting to be used.<br>

What the main thread does: The main thread has a reference (a remote control) to 
t2 on its private Stack. When you call <br><i>t2.run(),</br></i> the main thread simply uses that 
remote control to execute the instructions inside the run() method.<br><br>

The Kitchen Analogy<br>
Think of the Heap as a shared kitchen, and the t2 object as a blender sitting on the counter.<br>

<br><i>t1.start():</br></i> You (the main thread) hire a new worker (Thread-0), point to blender t1, and say, 
"Go turn that on." The worker goes and does it in the background while you keep moving.<br>

<br><i>t2.run():</br></i> You (the main thread) walk up to blender t2 and turn it on yourself. 
You have to stand there and wait for it to finish blending before you can do anything else.<br>

<hr>
