package futuretaskwrapperconcept;

import java.util.concurrent.*; /** Import everything. Don't do this at home */

/**
 * This code given here explains how to return a custom FutureTask when you 
 * submit a Callable to an ExecutorService. This is helpful in the following 
 * cases:
 * 
 * 1. To support Interruption-unfriendly task cancellation. java.net.Socket 
 * operations are notoriously oblivious to interruptions. Hence, tasks using 
 * Socket operations must support non-standard cancellation. (The best approach
 * to do this is to `close()` to Socket, forcing all of it's blocked methods
 * to throw SocketException.
 * 
 * 2. To return an identifier for Callable. Useful when Tasks are assigned ID's
 * and could be used to track them.
 * 
 * @author umermansoor
 */

public class FutureTaskWrapperConcept {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ExecutorService exec = new CustomFutureReturningExecutor(1,1, 
                Long.MAX_VALUE, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());
                
        // A Task with Id
        class Task implements IdentifiableCallable<Boolean> {
            
            private final int id; // Task Identifier
            volatile boolean cancelled; // Cancellation flag
            
            public Task(int id) {
                this.id = id;
            }
            
            @Override
            public synchronized int getId() {
                return id;
            }

            @Override public RunnableFuture<Boolean> newTask() {
                return new FutureTaskWrapper<Boolean>(this) {
                    @Override public boolean cancel(boolean mayInterruptIfRunning) {
                        Task.this.cancelTask();
                        return super.cancel(mayInterruptIfRunning);
                    }
                    
                    @Override
                    public int getTaskId() { 
                        return getId();
                    }
                };   
            }
            
            @Override public synchronized void cancelTask() {
                cancelled = true; // Set the cancellation flag
            }
            
            @Override public Boolean call() {
                
                while (!cancelled) {
                    // Do Samba
                }
                
                System.out.println("bye");
                
                return true;
            }
        };

        Future<?> f = exec.submit(new Task(0));
        FutureTaskWrapper ftw = null;
        if (f instanceof FutureTaskWrapper) {
            ftw = (FutureTaskWrapper)f;
            
        } else {
            throw new Exception("wtf");
        }
        
        // Give enough time for the task to run in the executor
        try { Thread.sleep(2000); } catch (InterruptedException ignored) { }
        
        System.out.println("Task Id: " + ftw.getTaskId());
        ftw.cancel(true);
        exec.shutdown();
            
    }
}
