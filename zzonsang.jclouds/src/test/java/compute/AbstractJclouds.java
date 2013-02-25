package compute;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.CloudStackGlobalAsyncClient;
import org.jclouds.cloudstack.domain.AsyncJob;
import org.jclouds.cloudstack.domain.AsyncJob.Status;
import org.junit.Before;

import com.google.common.util.concurrent.ListenableFuture;

import core.CloudStackClientFactory;

public class AbstractJclouds {

    protected final String apiUrl = "http://192.168.71.2:8080/client/api";
    protected final String rootApiKey = "X2c6N94krBVGd-VEhB4ISiEUTXf3sY7EXmQY8Ynp-EK7TZ28kLd_N_9ANmV3qKRmnTZVp5KKDZUtJqNS2fCwlw";
    protected final String rootSecretKey = "eScvGJZgotDc16qJEZyiA0ibLcWcme6w2Dpamy252ECBNwRwLzoF6EUaguMPvTNUFB1jY83j2j_nbrlsOD4hSQ";
    protected final String zoneId = "71e8d8cf-153e-42da-9bb3-c3df9f9b39ea";
    protected final String serviceOfferingId = "93240daf-699d-486e-be72-dfa2004573f2";
    protected final String templateId = "9e87915d-ebc7-4e47-8de7-6afc13fc187d";
    protected final String rootDomainId = "07a01f54-45cc-4b86-9e02-457cf97831b4";
//    protected final String testDomainId = "81fe53cc-67ed-4662-b416-34ff31375ca3";
    protected final String testDomainId = "1f0a6f6f-beb0-4404-86f9-c9134370bc3d";
    
    // xenserver-server1
    protected final String hostId1 = "239cd2ce-4bbd-4766-9893-3c99071e88c6";
    
    // xenserver-desktop 1
    protected final String hostId2 = "7d41b8a2-fe47-439c-a77f-cfa3ebb093cb";
    
    // xenserver-desktop 2
    protected final String hostId3 = "fbadc544-9d32-45fc-85dd-ebc315e59597";
    
    protected CloudStackClientFactory factory;
    protected CloudStackGlobalAsyncClient client;
    
    protected CloudStackGlobalAsyncClient getAsyncClient() {
        factory = new CloudStackClientFactory();
        client = factory.getAsyncClient(apiUrl, rootApiKey, rootSecretKey);
        
        return client;
    }
    
    @Before
    public void before() {
        client = getAsyncClient();
    }
    
    protected void printResponse(String jobId) throws InterruptedException, ExecutionException, TimeoutException {
        boolean flag = true;
        do {
            ListenableFuture<AsyncJob<Object>> asyncJob = client.getAsyncJobClient().getAsyncJob(jobId);
            AsyncJob<Object> asyncJob2 = asyncJob.get(20, TimeUnit.SECONDS);
            System.out.println(asyncJob2);
            
            Status status = asyncJob2.getStatus();
            if (!status.equals(Status.IN_PROGRESS)) {
                flag = false;
                System.out.println("############ Result ##############");
                System.out.println(asyncJob2.getResult());
            }
            Thread.sleep(1000);
        } while(flag);
    }
}
