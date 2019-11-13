import junit.framework.TestCase;
import org.mockejb.jndi.MockContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class LearningTests extends TestCase {
    public void testBindToMockJndi() throws Exception {
        MockContextFactory.setAsInitial();
        Context rootContext = new InitialContext();

        String jndiName = "blah/blah/constant";
        rootContext.bind(jndiName, new Long(762));
        assertEquals(new Long(762), rootContext.lookup(jndiName));
    }

    public void testLookupWithNarrow() throws Exception {
        Context rootContext = new InitialContext();

        String jndiName = "blah/blah/constant";
        rootContext.bind(jndiName, new Long(762));
        Object actualLookupObject = rootContext.lookup(jndiName);

        Long actual =
                (Long) PortableRemoteObject.narrow(
                        actualLookupObject,
                        Long.class);

        assertEquals(new Long(762), actual);
    }
}
