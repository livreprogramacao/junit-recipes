package junit.cookbook.jsp.test;

import junit.framework.TestCase;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.Options;
import org.apache.jasper.compiler.*;
import org.apache.jasper.servlet.JspCServletContext;
import org.apache.jasper.servlet.JspServletWrapper;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

public class JasperLearningTests extends TestCase {
    public void testCreateCompiler() throws Exception {
        URL resourceBaseUrl =
                new URL("file://localhost/d:/home/jbrains/workspace/wsad-5.0/cookbook/CoffeeShopWeb/Web Content/");

        // TODO  Replace with a proper NullPrintWriter
        PrintWriter logWriter = new PrintWriter(new StringWriter());

        boolean isErrorPage = false;
        Options options = makeOptions();

        options.getScratchDir().mkdirs();

        ServletContext servletContext =
                new JspCServletContext(logWriter, resourceBaseUrl);

        JspServletWrapper jspServletWrapper = null;
        JspRuntimeContext jspRuntimeContext =
                new JspRuntimeContext(servletContext, options);

        JspCompilationContext jspCompilationContext =
                new JspCompilationContext(
                        "/shopcart.jsp",
                        isErrorPage,
                        options,
                        servletContext,
                        jspServletWrapper,
                        jspRuntimeContext);

        jspCompilationContext.setServletJavaFileName(
                "data/jsp/_shopcart_jsp.java");

        StringWriter stringWriter = new StringWriter();
        jspCompilationContext.setWriter(
                new ServletWriter(new PrintWriter(stringWriter)));

        Compiler compiler = new Compiler(jspCompilationContext);
        compiler.generateJava();

        // Now how to run the JSP?

        assertEquals("", stringWriter.toString());
    }

    private Options makeOptions() {
        // TODO  Use EasyMock for this?

        return new Options() {

            public boolean getKeepGenerated() {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean getLargeFile() {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean isPoolingEnabled() {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean getMappedFile() {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean getSendErrorToClient() {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean getClassDebugInfo() {
                // TODO Auto-generated method stub
                return false;
            }

            public int getCheckInterval() {
                // TODO Auto-generated method stub
                return 0;
            }

            public boolean getDevelopment() {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean getReloading() {
                // TODO Auto-generated method stub
                return false;
            }

            public String getIeClassId() {
                // TODO Auto-generated method stub
                return null;
            }

            public File getScratchDir() {
                return new File("temp/jsp-scratch");
            }

            public String getClassPath() {
                // TODO Auto-generated method stub
                return null;
            }

            public String getCompiler() {
                // TODO Auto-generated method stub
                return null;
            }

            public TldLocationsCache getTldLocationsCache() {
                // TODO Auto-generated method stub
                return null;
            }

            public String getJavaEncoding() {
                return "UTF-8";
            }

            public boolean getFork() {
                // TODO Auto-generated method stub
                return false;
            }

        };
    }
}