package net.zzonsang.cloud.jclouds.cloudstack;

import java.util.Properties;
import java.util.Random;

import net.zzonsang.cloud.jclouds.TestConstants;

import org.jclouds.Constants;
import org.jclouds.ContextBuilder;
import org.jclouds.cloudstack.CloudStackAsyncClient;
import org.jclouds.cloudstack.CloudStackClient;
import org.jclouds.cloudstack.CloudStackContext;
import org.jclouds.cloudstack.domain.NetworkOffering;
import org.jclouds.cloudstack.domain.Zone;
import org.jclouds.rest.RestContext;

public class CloudStackClientUtil {

    public static CloudStackClient getCloudStackClient() {
        Properties prop = new Properties();
        prop.put(Constants.PROPERTY_ENDPOINT, TestConstants.ENDPOINT);
        
        ContextBuilder builder = ContextBuilder.newBuilder(TestConstants.PROVIDER)
                .credentials(TestConstants.API_KEY, TestConstants.SECRET_KEY)
//              .modules(modules)
                .overrides(prop);
        
        CloudStackContext context = builder.buildView(CloudStackContext.class);
        
        RestContext<CloudStackClient, CloudStackAsyncClient> providerContext = context.getProviderSpecificContext();
        return providerContext.getApi();
    }
    
    public static String getFirstZoneID(CloudStackClient client) throws Exception {
        Zone zone = client.getZoneClient().listZones().iterator().next();
        if ( zone != null ) return zone.getId();
        else throw new Exception("Not found zone.");
    }
    
    public static String getFirstNetworkOfferingId(CloudStackClient client) {
        NetworkOffering offering = client.getOfferingClient().listNetworkOfferings().iterator().next();
        return offering.getId();
    }
    
}
