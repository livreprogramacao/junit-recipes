package junit.extensions.cookbook;

/**
 * A simple wrapper that I wish were part of the Java
 * reflection API.
 *
 * @author <a href="mailto:jbr@diasparsoftware.com>J. B. Rainsberger</a>
 */
public class MethodParameter {
    private Class type;
    private Object value;

    public MethodParameter(Class type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Class getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
