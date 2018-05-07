package mongodb;

import java.net.UnknownHostException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class App {
	public static void main(String[] args) throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		Server jettyServer = new Server(8091);
		jettyServer.setHandler(context);

		ServletHolder testServlet = context.addServlet(ServletContainer.class,"/rest/*");
		testServlet.setInitOrder(0);
		testServlet.setInitParameter("jersey.config.server.provider.classnames", EntryPoint.class.getCanonicalName());
		
		
		ServletHolder personServlet = context.addServlet(ServletContainer.class, "/api/v1/*");
		personServlet.setInitOrder(0);
		personServlet.setInitParameter("jersey.config.server.provider.packages", "mongodb.Resources");
		
		context.setAttribute("mongodbObj", initializeMongoDb());

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}

	public static DB initializeMongoDb() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		DB database = mongoClient.getDB("test-db");
		return database;
	}
}
