This code given here explains how to return a custom FutureTask when you 
submit a Callable to an ExecutorService. This is helpful in the following 
cases:
 
1. To support Interruption-unfriendly task cancellation. java.net.Socket 
operations are notoriously oblivious to interruptions. Hence, tasks using 
Socket operations must support non-standard cancellation. (The best approach
to do this is to `close()` to Socket, forcing all of it's blocked methods
to throw SocketException.
 
2. To return an identifier for Callable. Useful when Tasks are assigned ID's
 and could be used to track them.
