package futuretaskwrapperconcept;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * Custom Callable Interface with Id.
 * @author umermansoor
 */
public interface IdentifiableCallable<T> extends Callable<T> 
{
    int getId();
    void cancelTask(); // Method for supporting non-standard cancellation
    RunnableFuture<T> newTask();
}
