package compute;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.AsyncCreateResponse;
import org.jclouds.cloudstack.options.DeployVirtualMachineOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

public class AllocateVMTest extends AbstractJclouds {
    
    @Test
    public void createVM() throws InterruptedException, ExecutionException, TimeoutException {
        DeployVirtualMachineOptions options = DeployVirtualMachineOptions.NONE;
        options.accountInDomain("accountName", testDomainId);
        options.buildQueryParameters().put("hostid", hostId2);
        
        ListenableFuture<AsyncCreateResponse> deployVirtualMachineInZone = 
                client.getVirtualMachineClient().deployVirtualMachineInZone(zoneId, serviceOfferingId, templateId, options);

        AsyncCreateResponse asyncCreateResponse = deployVirtualMachineInZone.get(20, TimeUnit.SECONDS);
        printResponse(asyncCreateResponse.getJobId());
    }
}
