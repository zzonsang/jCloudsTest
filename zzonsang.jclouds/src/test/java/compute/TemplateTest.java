package compute;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.Template;
import org.jclouds.cloudstack.options.ListTemplatesOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

public class TemplateTest extends AbstractJclouds {

    @Test
    public void listTemplate() throws InterruptedException, ExecutionException, TimeoutException {
        
        ListTemplatesOptions options = ListTemplatesOptions.NONE;
//        options.zoneId(zoneId);
        
        ListenableFuture<Set<Template>> listTemplates = client.getTemplateClient().listTemplates();
        
        Set<Template> template = listTemplates.get(10, TimeUnit.SECONDS);
        
        System.out.println(template);
    }
}
