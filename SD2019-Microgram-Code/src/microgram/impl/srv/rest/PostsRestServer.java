package microgram.impl.srv.rest;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import discovery.Discovery;
import utils.IP;


public class PostsRestServer {
	private static Logger Log = Logger.getLogger(PostsRestServer.class.getName());

	static {
		System.setProperty("java.net.preferIPv4Stack", "true");
		System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s");
	}
	
	public static final int PORT = 7772;
	public static final String SERVICE = "Microgram-Posts";
	public static String SERVER_BASE_URI = "http://%s:%s/rest";
	
	public static void main(String[] args) throws Exception {

		Log.setLevel( Level.FINER );

		String ip = IP.hostAddress();
		String serverURIS = String.format(SERVER_BASE_URI, ip, PORT);
		
		ResourceConfig config = new ResourceConfig();
		
		URI serverURI = new URI(serverURIS);
		
		config.register(new RestPostsResources(serverURI));
		
		JdkHttpServerFactory.createHttpServer( URI.create(serverURIS.replace(ip, "0.0.0.0")), config);

		Log.info(String.format("%s Server ready @ %s\n",  SERVICE, serverURI));
		
		Discovery.announce(SERVICE, serverURIS);
	}
}
