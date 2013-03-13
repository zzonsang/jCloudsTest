/**
 * 
 */
package compute;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.CloudStackGlobalAsyncClient;
import org.jclouds.cloudstack.domain.AsyncCreateResponse;
import org.jclouds.cloudstack.domain.Pod;
import org.jclouds.cloudstack.domain.VirtualMachine;
import org.jclouds.cloudstack.domain.Volume;
import org.jclouds.cloudstack.options.DeployVirtualMachineOptions;
import org.jclouds.cloudstack.options.ListPodsOptions;
import org.jclouds.cloudstack.options.ListVirtualMachinesOptions;
import org.jclouds.cloudstack.options.ListVolumesOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

import core.CloudStackClientFactory;

/**
 * @author zzonsang2
 *
 */
public class ComputeTest extends AbstractJclouds {
    
    @Test
    public void listVMsForTwoAccount() throws InterruptedException, ExecutionException, TimeoutException {
        String endpoint = "http://183.98.30.86:8080/client/api";
        
        String apikey = "Ch66GTHk6E_8e9mC5nimFHOevDntkeTV43SQytIfRBsnUsM4Jx20yMe2hDh4DhGXZ6cE21H4zVDBARNSs_9s9A";
        String secretkey = "AENfC4THE1Dycx9d6uZUnPbDqU5ETqMJrB6lCVhWfqmCOJnOOtV7es6nZTAahjeQfotH_LoR8zgMhM0_jo3XYA";
        
        String apikey2 = "x5aI9qZQxmY-7HDsSjolqk0crGVmXMYSI-HNwDyVpGdaRbZ2ZtcF0QT1DlrwSbSdLCuw9D4oqkDNy3fRDrk1yw";
        String secretkey2 = "b04N7wyaw2sgK_n-OV31xNelByegyYzhuxv7fE0MhyZ0wIavvWeRKdQ3TAeLTEns8f05ik8Q2Y3GddTuCEh5aw";
        
        
        CloudStackClientFactory factory = new CloudStackClientFactory();
        
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
        listVM(factory.getAsyncClient(endpoint, apikey, secretkey));
        listVM(factory.getAsyncClient(endpoint, apikey2, secretkey2));
    }
    
    private void listVM(CloudStackGlobalAsyncClient client) throws InterruptedException, ExecutionException, TimeoutException {
        ListenableFuture<Set<VirtualMachine>> listVirtualMachines = client.getVirtualMachineClient().listVirtualMachines();
        Set<VirtualMachine> vms = listVirtualMachines.get(10, TimeUnit.SECONDS);
        
        for (VirtualMachine vm : vms) {
            System.out.println(vm);
        }
    }
    
    
    @Test
    public void listVolumes() throws InterruptedException, ExecutionException, TimeoutException {
        String volumeId = "371bc77f-8e6c-4fd8-b200-0460c001b741";
        
        ListVolumesOptions options = ListVolumesOptions.NONE;
        options.id(volumeId);
        
        ListenableFuture<Set<Volume>> listVolumes = client.getVolumeClient().listVolumes(options);
        
        Set<Volume> volumes = listVolumes.get(10, TimeUnit.SECONDS);
        
        if (volumes.size() == 1) {
            System.out.println(volumes.iterator().next().getDeviceId());
        }
//        
//        for (Volume vol : volumes) {
//            System.out.println(vol.getDeviceId());
//        }
    }
    
    @Test
    public void getVM() throws InterruptedException, ExecutionException, TimeoutException {
        
        ListVirtualMachinesOptions options = ListVirtualMachinesOptions.NONE;
        
        options.id("21ca892e-1e2f-403f-9f69-280d08541bf4");
        
        ListenableFuture<Set<VirtualMachine>> listVirtualMachines = client.getVirtualMachineClient().listVirtualMachines(options);
        
        Set<VirtualMachine> virtualMachine = listVirtualMachines.get(10, TimeUnit.SECONDS);
        
        System.out.println(virtualMachine);
    }
    
    @Test
    public void existVMS() throws InterruptedException, ExecutionException, TimeoutException {
        String account = "ipcaccount2_15";
        String domain = "c3b12fda-d0fa-4223-9c16-61b19b66e229";
        String name = "testvmname6";
        String zoneId = "239d62bf-5708-4a6b-8a41-c551975c1f23";
        String serviceOfferingId = "6926f42a-1908-451a-a2ab-ac7fffbfd76a";
        String templateId = "6099263a-24b4-4860-bbe7-a89aacd92084";
        
        DeployVirtualMachineOptions options = DeployVirtualMachineOptions.NONE;
        options.accountInDomain(account, domain);
        options.name(name);
        
        ListenableFuture<AsyncCreateResponse> deployVirtualMachineInZone = client.getVirtualMachineClient().deployVirtualMachineInZone(zoneId, serviceOfferingId, templateId, options);
        deployVirtualMachineInZone.get(10, TimeUnit.SECONDS);
//        Thread.sleep(1000);
        
//        AsyncCreateResponse asyncCreateResponse = deployVirtualMachineInZone.get(10, TimeUnit.SECONDS);
//        printResponse(asyncCreateResponse.getJobId());
        
        ListVirtualMachinesOptions options2 = ListVirtualMachinesOptions.NONE;
        options2.accountInDomain(account, domain);
        options2.name(name);
        
        ListenableFuture<Set<VirtualMachine>> listVirtualMachines = 
                client.getVirtualMachineClient().listVirtualMachines(options2);
        
        Set<VirtualMachine> vms = listVirtualMachines.get(20, TimeUnit.SECONDS);
        
        for (VirtualMachine vm : vms) {
            System.out.println(vm.getName());
        }
    }
    
    @Test
    public void createVolume() throws InterruptedException, ExecutionException, TimeoutException {
        
//        factory = new CloudStackClientFactory();
//        client = factory.getAsyncClient("http://183.98.30.86:8080/client/", 
//                "A4lQn-SV1mGEe5vb_ZbOUd8k761cX-6d45553R3wBllDzpF1xvns-jEaPlW2EMhk2SdrWbJXf2zqZv93mvSCkw", 
//                "k8LK-96G8t2Do_38_KRMPvmbfaRT_7iWSiGCLEpnn0DDB2twuQ2YcBE68rAuZBdz7YKKI3RNhiGNcyfMht9CFQ");
        
        String name = "ipcaccount_37";
        String diskOffering = "69d6c86e-fb81-419f-a914-2440b54e204c";
        String zoneId = "239d62bf-5708-4a6b-8a41-c551975c1f23";
        
        ListenableFuture<AsyncCreateResponse> createVolumeFromDiskOfferingInZone = client.getVolumeClient()
        .createVolumeFromDiskOfferingInZone(name, diskOffering, zoneId);
        
        AsyncCreateResponse asyncCreateResponse = createVolumeFromDiskOfferingInZone.get(10, TimeUnit.SECONDS);
        
        printResponse(asyncCreateResponse.getJobId());
    }
    
//    @Test
//    public void createVM() {
//        String zoneId = "239d62bf-5708-4a6b-8a41-c551975c1f23";
//        String serviceOfferingId = "";
//        String templateId = "";
//        DeployVirtualMachineOptions options = DeployVirtualMachineOptions.NONE;
//        options.accountInDomain("ipcaccount_37", )
//        client.getVirtualMachineClient().deployVirtualMachineInZone(zoneId, serviceOfferingId, templateId, options)
//    }
    
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
