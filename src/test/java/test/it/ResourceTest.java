package test.it;

import app.*;
import io.undertow.servlet.api.*;
import org.jboss.resteasy.plugins.server.undertow.*;
import org.junit.*;

import javax.ws.rs.client.*;

import static org.jboss.resteasy.test.TestPortProvider.*;
import static org.junit.Assert.*;

public class ResourceTest {
    private static UndertowJaxrsServer server;
    
    @BeforeClass
    public static void init() {
        server = new UndertowJaxrsServer().start();
    }
    
    @AfterClass
    public static void stop() {
        server.stop();
    }
    
    @Test
    public void testApplicationPath() {
        server.deploy(MyApp.class);
        Client client = ClientBuilder.newClient();
        String val = client.target(generateURL("/base/test")).request().get(String.class);
        assertEquals("hello world", val);
        client.close();
    }
    
    @Test
    public void testApplicationContext() {
        server.deploy(MyApp.class, "/root");
        Client client = ClientBuilder.newClient();
        String val = client.target(generateURL("/root/test")).request().get(String.class);
        assertEquals("hello world", val);
        client.close();
    }
    
    @Test
    public void testDeploymentInfo() {
        DeploymentInfo di = server.undertowDeployment(MyApp.class);
        di.setContextPath("/di");
        di.setDeploymentName("DI");
        server.deploy(di);
        Client client = ClientBuilder.newClient();
        String val = client.target(generateURL("/di/base/test")).request().get(String.class);
        assertEquals("hello world", val);
        client.close();
    }
}
