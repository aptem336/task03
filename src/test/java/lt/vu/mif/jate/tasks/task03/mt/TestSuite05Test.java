package lt.vu.mif.jate.tasks.task03.mt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lt.vu.mif.jate.tasks.task03.mt.tool.ClientTest;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import lt.vu.mif.jate.tasks.task03.mt.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Multi-threaded test: responses can arrive in different (mixed) order
 * clients should be able to send and receive correlated messages in mixed sequence.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite05Test implements ClientTest {

    @Test
    public void doTests() throws Throwable {
        try (Client c = new Client(SERVER_ADDR)) {
            
            ExecutorService executor = Executors.newFixedThreadPool(2);
            
            Future<Long> fastVerySlow = executor.submit(new Callable<Long>() {
                
                @Override
                public Long call() throws Exception {
                    
                    assertEquals((Long) 111L, c.addition(10L, 101L, 5000L));
                    return System.currentTimeMillis();
                    
                };
                
            });
            
            Future<Long> slowFast = executor.submit(new Callable<Long>() {
                
                @Override
                public Long call() throws Exception {
                    
                    Thread.sleep(1000);
                    
                    assertEquals((Long) 111L, c.addition(10L, 101L, 0L));
                    return System.currentTimeMillis();
                    
                };
                
            });
            
            assertTrue("slowFast should have finished before fastVerySlow!", slowFast.get() < fastVerySlow.get());
            
            try {
                        
                executor.shutdown();
                executor.awaitTermination(2, TimeUnit.MINUTES);
            
            } catch (InterruptedException e) { }

            
        }        
     }
    
}
