package junit.cookbook.junitx.example;

import junitx.framework.TestAccessException;

public class TestProxy extends junitx.framework.TestProxy {

    public Object newInstance(Object[] arguments)
            throws TestAccessException {

        try {
            return getProxiedClass()
                    .getConstructor(arguments)
                    .newInstance(arguments);
        } catch (Exception e) {
            throw new TestAccessException(
                    "could not instantiate "
                            + getTestedClassName(),
                    e);
        }
    }

    public Object newInstanceWithKey(
            String constructorKey,
            Object[] arguments)
            throws TestAccessException {

        try {
            return getProxiedClass()
                    .getConstructor(constructorKey)
                    .newInstance(arguments);
        } catch (Exception e) {
            throw new TestAccessException(
                    "could not instantiate "
                            + getTestedClassName(),
                    e);
        }
    }
}
