package core;


import java.util.List;

import org.jclouds.ContextBuilder;
import org.jclouds.cloudstack.CloudStackClient;
import org.jclouds.cloudstack.CloudStackContext;
import org.jclouds.cloudstack.CloudStackGlobalAsyncClient;
import org.jclouds.enterprise.config.EnterpriseConfigurationModule;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.inject.Module;

/**
 * @author zzonsang2
 *
 */
public final class CloudStackClientFactory {

    private static final String LOGGER_NAME = "org.jclouds.logging.slf4j.config.SLF4JLoggingModule";
    
    private static final List<Module> MODULES;
    
    static {
        Builder<Module> builder = ImmutableList.<Module> builder();
        try {
            Class<?> cls = CloudStackClient.class.getClassLoader().loadClass(LOGGER_NAME);
            Module slf4j = (Module) cls.newInstance();
            builder.add(slf4j);
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        MODULES = builder.add(new EnterpriseConfigurationModule()).build();
    }
    
    public static CloudStackContext getContext(String endpointUrl, String apiKey, String secretKey) {
        return ContextBuilder.newBuilder("cloudstack")
                .endpoint(Preconditions.checkNotNull(endpointUrl))
                .credentials(Preconditions.checkNotNull(apiKey), Preconditions.checkNotNull(secretKey))
                .modules(MODULES)
                .build(CloudStackContext.class);
    }
    
    
    public CloudStackGlobalAsyncClient getAsyncClient(String endpoint, String apikey, String secretkey) {
        CloudStackContext context = getContext(endpoint, apikey, secretkey);
        return context.getGlobalContext().getAsyncApi();
    }
}
