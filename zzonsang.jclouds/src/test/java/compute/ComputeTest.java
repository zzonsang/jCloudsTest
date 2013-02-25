/**
 * 
 */
package compute;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.AsyncCreateResponse;
import org.jclouds.cloudstack.domain.Host;
import org.jclouds.cloudstack.domain.Pod;
import org.jclouds.cloudstack.domain.VirtualMachine;
import org.jclouds.cloudstack.options.DeployVirtualMachineOptions;
import org.jclouds.cloudstack.options.ListHostsOptions;
import org.jclouds.cloudstack.options.ListPodsOptions;
import org.jclouds.cloudstack.options.ListVirtualMachinesOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author zzonsang2
 *
 */
public class ComputeTest extends AbstractJclouds {
    
    @Test
    public void getVM() throws InterruptedException, ExecutionException, TimeoutException {
        
        ListVirtualMachinesOptions options = ListVirtualMachinesOptions.NONE;
        
        options.id("21ca892e-1e2f-403f-9f69-280d08541bf4");
        
        ListenableFuture<Set<VirtualMachine>> listVirtualMachines = client.getVirtualMachineClient().listVirtualMachines(options);
        
        Set<VirtualMachine> virtualMachine = listVirtualMachines.get(10, TimeUnit.SECONDS);
        
        System.out.println(virtualMachine);
    }
    
    @Test
    public void deployVM() throws InterruptedException, ExecutionException, TimeoutException {
        DeployVirtualMachineOptions options = DeployVirtualMachineOptions.NONE;
        
        options.accountInDomain("accountName", testDomainId).name("hschotesthostname");
        
        ListenableFuture<AsyncCreateResponse> deployVirtualMachineInZone = client.getVirtualMachineClient().deployVirtualMachineInZone(zoneId, serviceOfferingId, templateId, options);
        
        AsyncCreateResponse asyncCreateResponse = deployVirtualMachineInZone.get(20, TimeUnit.SECONDS);
        
        printResponse(asyncCreateResponse.getJobId());
    }

    @Test
    public void listPods() throws InterruptedException, ExecutionException, TimeoutException {
        ListPodsOptions options = ListPodsOptions.NONE;
        options.id("12");
        
        ListenableFuture<Set<Pod>> listPods = client.getPodClient().listPods(options);
        
        Set<Pod> pods = listPods.get(10, TimeUnit.SECONDS);
        
        if (pods.size() > 0) {
            for(Pod pod : pods) {
                if (pod.getId().equals("12")) {
                    System.out.println("false");
                }
            }
        }
        
        System.out.println(true);
    }
}
