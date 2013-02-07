package compute;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.Capacity;
import org.jclouds.cloudstack.options.ListCapacityOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

public class CapacityTest extends AbstractJclouds {

    @Test
    public void podStatus() throws InterruptedException, ExecutionException, TimeoutException {
        ListCapacityOptions options = ListCapacityOptions.NONE;
        
        ListenableFuture<Set<Capacity>> listCapacity = client.getCapacityClient().listCapacity(options);
        
        Set<Capacity> capacitySet = listCapacity.get(10, TimeUnit.SECONDS);
        
        System.out.println(capacitySet);
    }
}
