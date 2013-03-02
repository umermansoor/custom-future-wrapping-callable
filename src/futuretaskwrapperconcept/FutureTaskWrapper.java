
package futuretaskwrapperconcept;

import java.util.concurrent.*;

/**
 * A Custom Wrapper around FutureTask for returning Task Id.
 * @author umermansoor
 */
public abstract class FutureTaskWrapper<T> extends FutureTask<T> 
{
    
    public FutureTaskWrapper(Callable<T> c) {
        super(c);
    }
    
    abstract int getTaskId();
    
}
