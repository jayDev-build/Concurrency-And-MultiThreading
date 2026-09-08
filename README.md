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