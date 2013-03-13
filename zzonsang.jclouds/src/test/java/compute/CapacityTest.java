package compute;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.Capacity;
import org.jclouds.cloudstack.domain.Host;
import org.jclouds.cloudstack.options.ListCapacityOptions;
import org.jclouds.cloudstack.options.ListHostsOptions;
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
    
    @Test
    public void hostInfo() throws InterruptedException, ExecutionException, TimeoutException {
        ListHostsOptions options = ListHostsOptions.NONE;
        options.id(testHost);
        ListenableFuture<Set<Host>> listHosts = client.getHostClient().listHosts(options);
        Set<Host> hosts = listHosts.get(10, TimeUnit.SECONDS);
        for (Host host : hosts) {
            System.out.println(host);
        }
    }
}
