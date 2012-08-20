package net.zzonsang.cloud.jclouds;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jclouds.Constants;
import org.jclouds.ContextBuilder;
import org.jclouds.cloudstack.CloudStackAsyncClient;
import org.jclouds.cloudstack.CloudStackClient;
import org.jclouds.cloudstack.CloudStackContext;
import org.jclouds.cloudstack.domain.AsyncJob;
import org.jclouds.cloudstack.domain.PublicIPAddress;
import org.jclouds.cloudstack.domain.VirtualMachine;
import org.jclouds.cloudstack.domain.Volume;
import org.jclouds.cloudstack.domain.Zone;
import org.jclouds.rest.RestContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Using Cloud Stack API
 */
public class CloudStackComputeAPI {

    private CloudStackClient client;
    
    @Before
    public void setUp() {
        Properties prop = new Properties();
        prop.put(Constants.PROPERTY_ENDPOINT, TestConstants.ENDPOINT);
        
        ContextBuilder builder = ContextBuilder.newBuilder(TestConstants.PROVIDER)
                .credentials(TestConstants.API_KEY, TestConstants.SECRET_KEY)
//              .modules(modules)
                .overrides(prop);
        
        CloudStackContext context = builder.buildView(CloudStackContext.class);
        
        RestContext<CloudStackClient, CloudStackAsyncClient> providerContext = context.getProviderSpecificContext();
        client = providerContext.getApi();
    }
    
    /**
     * listVirtualMachines
     */
//    @Test
    public void listVirtaulMachines() {
        System.out.println("### listVirtualMachines ###");
        
//        Properties prop = new Properties();
//        prop.put(Constants.PROPERTY_ENDPOINT, TestConstants.ENDPOINT);
//        
//        ContextBuilder builder = ContextBuilder.newBuilder(TestConstants.PROVIDER)
//                .credentials(TestConstants.API_KEY, TestConstants.SECRET_KEY)
////              .modules(modules)
//                .overrides(prop);
//        
//        CloudStackContext context = builder.buildView(CloudStackContext.class);
//        
//        RestContext<CloudStackClient, CloudStackAsyncClient> providerContext = context.getProviderSpecificContext();
//        CloudStackClient client = providerContext.getApi();
        Set<VirtualMachine> listVirtualMachine = client.getVirtualMachineClient().listVirtualMachines();
        
        List<VirtualMachine> notRunningVm = new LinkedList<VirtualMachine>();
        
        System.out.println("\n@@@ Running VMs@@@");
        for (VirtualMachine vm : listVirtualMachine ) {
            if ( vm.getState() == VirtualMachine.State.RUNNING ) {
                System.out.println(vm.toString());
            } else {
                notRunningVm.add(vm);
            }
        }
        
        System.out.println("\n@@@ Not Running VMs @@@");
        for ( VirtualMachine vm : notRunningVm ) {
            System.out.println("VM state : " + vm.getState().toString() + ", Detail : " + vm);
        }
        
        System.out.println();
    }
    
    /**
     * listZones
     */
//    @Test
    public void listZones() {
        System.out.println("### listZones ###");
        Set<Zone> listZones = client.getZoneClient().listZones();
        
        for ( Zone zone : listZones ) {
            System.out.println(zone);
        }
        
        System.out.println("");
    }
    
    /**
     * listPods
     */
//    @Test
//    public void listPods() {
//        System.out.println("### listPods ###");
//        
//        cli
//        
//        System.out.println();
//    }
    
    /**
     * listVolumes
     */
//    @Test
    public void listVolumes() {
        System.out.println("### listVolumes ###");
        
        Set<Volume> listVolumes = client.getVolumeClient().listVolumes();
        for ( Volume volume : listVolumes ) {
            System.out.println(volume.toString());
        }
        
        System.out.println("");
    }
    
    /**
     * listPublicIPAddresses
     */
//    @Test
    public void listPublicIPAddresses() {
        System.out.println("### listPublicIPAddresses ###");
        
        Set<PublicIPAddress> listPublicIPAddresses = client.getAddressClient().listPublicIPAddresses();
        for ( PublicIPAddress address : listPublicIPAddresses ) {
            System.out.println(address.toString());
        }
        
        System.out.println("");
    }
    
//    @Test
    public void list() {
        Set<AsyncJob<?>> listAsyncJobs = client.getAsyncJobClient().listAsyncJobs();
        for ( AsyncJob<?> jobs : listAsyncJobs ) {
            System.out.println(jobs.toString());
        }
    }
    
}


































