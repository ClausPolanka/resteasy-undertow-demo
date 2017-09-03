package test.unit;

import app.resources.*;
import org.jboss.resteasy.core.*;
import org.jboss.resteasy.mock.*;
import org.jboss.resteasy.plugins.server.resourcefactory.*;
import org.junit.*;

import static javax.servlet.http.HttpServletResponse.*;
import static org.jboss.resteasy.mock.MockHttpRequest.get;
import static org.junit.Assert.assertEquals;

public class RestourceTest {
    
    @Test
    public void getReturnsHttpStatusOkAndExpectedContentAsString() throws Exception {
        Dispatcher d = createDispatcher(Resource.class);
        
        MockHttpResponse res = new MockHttpResponse();
        d.invoke(get("/test"), res);
        
        assertEquals(SC_OK, res.getStatus());
        assertEquals("hello world", res.getContentAsString());
    }
    
    private Dispatcher createDispatcher(Class<Resource> resource) {
        Dispatcher d = MockDispatcherFactory.createDispatcher();
        d.getRegistry().addResourceFactory(new POJOResourceFactory(resource));
        return d;
    }
}
