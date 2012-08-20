package net.zzonsang.cloud.jclouds;

import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.domain.Location;
import org.junit.Before;
import org.junit.Test;

public class ComputeAPITest {

	/**
	 * 
	 */
	private ComputeService computeService;
	
	@Before
	public void before() {
		ComputeServiceContext context = ContextBuilder.newBuilder(TestConstants.PROVIDER)
				.endpoint(TestConstants.ENDPOINT)
				.credentials(TestConstants.API_KEY, TestConstants.SECRET_KEY)
				.build(ComputeServiceContext.class);
		computeService = context.getComputeService();
	}
	
	/**
	 * List of the Nodes 
	 */
//	@Test
	public void listNodes() {
		System.out.println("### listNodes ###");

		Set<? extends ComputeMetadata> listNodes = computeService.listNodes();
		for ( ComputeMetadata node : listNodes ) {
			System.out.println(node.toString());
		}
		
		System.out.println("");
	}
	
	/**
	 * List of the Locations
	 */
	@Test
	public void listLocations() {
		System.out.println("### listLocation ###");
		
		Set<? extends Location> listAssignableLocations = computeService.listAssignableLocations();
		for ( Location location : listAssignableLocations ) {
			System.out.println(location.toString());
		}
		
		System.out.println("");
	}
	
	/**
	 * List of the Hardware
	 */
//	@Test
	public void listHardware() {
		System.out.println("### listHardware ###");
		
		Set<? extends Hardware> listHardwareProfiles = computeService.listHardwareProfiles();
		for ( Hardware hardware : listHardwareProfiles ) {
			System.out.println(hardware.toString());
		}
		
		System.out.println("");
	}
	
	/**
	 * List of the Images
	 */
//	@Test
	public void listImages() {
		System.out.println("### listImages ###");
		
		Set<? extends Image> listImages = computeService.listImages();
		for( Image image : listImages ) {
			System.out.println(image.toString());
		}
		
		System.out.println("");
	}
	
	/**
	 * Create node in group
	 */
//	@Test
	public void createNodeInGroup() {
		System.out.println("### createNodeInGroup ###");
		
		Template template = computeService.templateBuilder().hardwareId(TestConstants.HARDWARE_ID)
//				.osVersionMatches("5.6")
				.imageDescriptionMatches("CentOS")
				.osFamily(OsFamily.CENTOS).os64Bit(true).build();
		
		try {
			Set<? extends NodeMetadata> createNodesInGroup = computeService.createNodesInGroup("jclouds", 1, template);
		} catch (RunNodesException e) {
			e.printStackTrace();
		}
		
		System.out.println("");
	}
}












